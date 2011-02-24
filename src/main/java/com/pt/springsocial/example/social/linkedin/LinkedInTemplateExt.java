package com.pt.springsocial.example.social.linkedin;

import org.springframework.http.client.CommonsClientHttpRequestFactory;
import org.springframework.social.linkedin.LinkedInTemplate;
import org.springframework.social.oauth.OAuthSigningClientHttpRequestFactory;
import org.springframework.social.oauth1.OAuth1RequestSignerFactory;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

/** Our own version of LinkedInTemplate added  updateStatus method
 * 
 * @author fcaro
 *
 */
public class LinkedInTemplateExt extends LinkedInTemplate {
	RestOperations restOperationsExt;
	
	public LinkedInTemplateExt(String apiKey, String apiSecret, String accessToken, String accessTokenSecret) {
		super(apiKey, apiSecret, accessToken, accessTokenSecret);
		
		// base restOperations is not available for inherited class
		// so, I've define my own
		
		RestTemplate restTemplate = new RestTemplate(new OAuthSigningClientHttpRequestFactory(
				new CommonsClientHttpRequestFactory(),
				OAuth1RequestSignerFactory.getRequestSigner(apiKey, apiSecret, accessToken, accessTokenSecret)));
		this.restOperationsExt = restTemplate;		
	}
	
	/** Post message with using person-activities API method
	 * 
	 * @param message
	 */
	public void updateStatus(String message) {
		LinkedInPersonActivity personActivity = new LinkedInPersonActivity(message);
		
		restOperationsExt.postForLocation("http://api.linkedin.com/v1/people/~/person-activities", personActivity);
	}

}
