package com.pt.springsocial.example.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.validation.Valid;

import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.facebook.FacebookAccessToken;
import org.springframework.social.facebook.FacebookProfile;
import org.springframework.social.facebook.FacebookTemplate;
import org.springframework.social.facebook.FacebookUserId;
import org.springframework.social.linkedin.LinkedInProfile;
import org.springframework.social.linkedin.LinkedInTemplate;
import org.springframework.social.twitter.SearchResults;
import org.springframework.social.twitter.TwitterTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import com.pt.springsocial.example.entity.User;
import com.pt.springsocial.example.service.UserService;
import com.pt.springsocial.example.social.FacebookProvider;
import com.pt.springsocial.example.social.LinkedInProvider;
import com.pt.springsocial.example.social.TwitterProvider;
import com.pt.springsocial.example.social.linkedin.LinkedInTemplateExt;

/** This controller responsible for all social-operations: connect to some service, update status
 * 
 * @author fcaro
 *
 */
@Controller
public class SocialController {
	@Autowired
	LinkedInProvider linkedInProvider;
	@Autowired
	FacebookProvider facebookProvider;
	@Autowired
	TwitterProvider  twitterProvider;
	
	@Autowired
	UserService		userService;

	/** Send redirect to linkedIN for connection
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/connect/linkedin", method = RequestMethod.GET)
	public String requestConnectionToLinkedIn(WebRequest request) {
	    // get request token
		Token requestToken = linkedInProvider.getOAuthService().getRequestToken();
		// store request token in session
	    request.setAttribute("linkedin_request_token", requestToken, WebRequest.SCOPE_SESSION);
	    
	    return "redirect:" + linkedInProvider.getAuthorizeUrl() + "?oauth_token=" + requestToken.getToken();
	}
	
	/** Process callback on success login into LinkedIn
	 * 
	 * @param verifier
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/callback/linkedin", method = RequestMethod.GET, params = "oauth_token")
	public String authorizeLinkedInCallback(@RequestParam(value = "oauth_verifier", defaultValue = "verifier") String verifier,
	                                		WebRequest request) {
		// get request token from session
	    Token requestToken = (Token)request.getAttribute("linkedin_request_token", WebRequest.SCOPE_SESSION);
	    
	    // get access token
	    Token accessToken = linkedInProvider.getOAuthService().getAccessToken(requestToken, new Verifier(verifier));
	    String userName = getCurrentUser().getName();
	    userService.updateLinkedInAuthentication(userName, accessToken.getToken(), accessToken.getSecret());
	    
	    return "redirect:/status";
	 
	}
	
	/** Send redirect to url in twitter for login
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/connect/twitter", method = RequestMethod.GET)
	public String requestConnectionToTwitter(WebRequest request) {
	    // get request token
		Token requestToken = twitterProvider.getOAuthService().getRequestToken();
		// store request token in session
	    request.setAttribute("twitter_request_token", requestToken, WebRequest.SCOPE_SESSION);
	    
	    return "redirect:" + twitterProvider.getAuthorizeUrl() + "?oauth_token=" + requestToken.getToken();
	}
	
	/** Callback from twitter on success login
	 * 
	 * @param verifier
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/callback/twitter", method = RequestMethod.GET, params = "oauth_token")
	public String authorizeTwitterCallback(@RequestParam(value = "oauth_verifier", defaultValue = "verifier") String verifier,
	                                WebRequest request) {
		// get request token from session
	    Token requestToken = (Token)request.getAttribute("twitter_request_token", WebRequest.SCOPE_SESSION);
	    
	    // get access token
	    Token accessToken = twitterProvider.getOAuthService().getAccessToken(requestToken, new Verifier(verifier));
	    String userName = getCurrentUser().getName();
	    userService.updateTwitterAuthentication(userName, accessToken.getToken(), accessToken.getSecret());
	    
	    return "redirect:/status";
	 
	}
	
	/** Callback from Faceook on success login
	 * 
	 * @param accessToken
	 * @param facebookUserId
	 * @return
	 */
	@RequestMapping(value="/connect/facebook", method=RequestMethod.POST) 
	public String connectAccountToFacebook(@FacebookAccessToken String accessToken, 
										   @FacebookUserId String facebookUserId) {
		if (facebookUserId != null && accessToken != null) {
			// store facebook information
		    String userName = getCurrentUser().getName();
		    userService.updateFacebookAuthentication(userName, accessToken, facebookUserId);			
		}
		return "redirect:/status";
	}
	
	/** Display user status page
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/status")
	public String showStatus(Model model) {
		User user = getCurrentUser();
		
		LinkedInTemplate linkedInTemplate = linkedInProvider.createTemplate(user);
		FacebookTemplate facebookTemplate = facebookProvider.createTemplate(user);
		TwitterTemplate  twitterTemplate = twitterProvider.createTemplate(user);
		
		// put linkedin info into page model
		if (linkedInTemplate != null) {
			model.addAttribute("connectedToLinkedIn", true);
			model.addAttribute("linkedInProfile", linkedInTemplate.getUserProfile());
		} else {
			model.addAttribute("connectedToLinkedIn", false);
		}
		
		// pub facebook info into page model
		if (facebookTemplate != null) {
			model.addAttribute("connectedToFacebook", true);
			model.addAttribute("facebookProfile", facebookTemplate.getUserProfile());
		} else {
			model.addAttribute("connectedToFacebook", false);
		}

		// put twitter info into page model
		if (twitterTemplate != null) {
			model.addAttribute("connectedToTwitter", true);
			model.addAttribute("twitterProfile", twitterTemplate.getProfileId());
		} else {
			model.addAttribute("connectedToTwitter", false);
		}
		
		// put empty status form
		model.addAttribute("statusForm", new StatusForm());
		
		return "status";
	}	
	
	/** Send status to supported services
	 * 
	 * @param statusForm
	 * @param result
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/status", method = RequestMethod.POST)
	public String sendStatus(@Valid StatusForm statusForm, BindingResult result, ModelMap modelMap) {
		User user = getCurrentUser();
		
		LinkedInTemplateExt linkedInTemplate = linkedInProvider.createTemplate(user);
		FacebookTemplate facebookTemplate = facebookProvider.createTemplate(user);
		TwitterTemplate  twitterTemplate = twitterProvider.createTemplate(user);
		
		// send message to LinkedIn
		if (linkedInTemplate != null) {
			linkedInTemplate.updateStatus(statusForm.getStatus());
		}
		
		// send message to Facebook
		if (facebookTemplate != null) {
			facebookTemplate.updateStatus(statusForm.getStatus());
		}
		
		// send message to Twitter
		if (twitterTemplate != null) {
			twitterTemplate.updateStatus(statusForm.getStatus());
		}
		return "redirect:/status";
	}
	
	
	/** Conexiones de LinkedIn
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/detail/linkedin", method = RequestMethod.GET)
	public String linkedInProfile(Model model) {
		User user = getCurrentUser();
		
		LinkedInTemplate linkedInTemplate = linkedInProvider.createTemplate(user);
		if (linkedInTemplate != null) {
			List<LinkedInProfile> profiles = linkedInTemplate.getConnections();
			model.addAttribute("connections", profiles);
		}
		
		return "linkedInProfile";
	}
	
	/** Tweets 
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/detail/twitter", method = RequestMethod.GET)
	public String searchTweets(@RequestParam(value="query", defaultValue="#springsocial") String query, WebRequest request) {
		User user = getCurrentUser();
		
		TwitterTemplate twitterTemplate = twitterProvider.createTemplate(user);
		if (twitterTemplate != null) {
			SearchResults results =  twitterTemplate.search(query);
			request.setAttribute("tweets", results.getTweets(), WebRequest.SCOPE_SESSION);
		}
		
		return "twitterSearch";
	}
	
	
	/** Facebook template
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/detail/facebook", method = RequestMethod.GET)
	public String searchTweets(Model model,  WebRequest request) {
		User user = getCurrentUser();
		FacebookTemplate template = facebookProvider.createTemplate(user);
		List<String> friendsIds = template.getFriendIds();
		FacebookProfile profile = template.getUserProfile();
		model.addAttribute("profile", profile);
		model.addAttribute("friendsIds", friendsIds);
		//template.getProfilePicture(friendsIds.get(0)));
		return "facebookProfile";
	}
	
	@RequestMapping(value = "/detail/facebook/photoProfile", method = RequestMethod.GET)
	public ResponseEntity<byte[]> handle(HttpEntity<byte[]> requestEntity) throws UnsupportedEncodingException {
		User user = getCurrentUser();
		FacebookTemplate template = facebookProvider.createTemplate(user);
		HttpHeaders responseH = new HttpHeaders();
		//responseH.setContentType(MediaType.ALL);
		return new ResponseEntity<byte[]>(template.getProfilePicture(), responseH, HttpStatus.OK);
	}
	
	
	/** Returns current user
	 * 
	 * @return
	 */
	protected User getCurrentUser() {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    String userName = userDetails.getUsername();
	    User user = userService.findByName(userName);
		
	    return user;
	}
	
	
	
	
}
