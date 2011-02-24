package com.pt.springsocial.example.social;


import org.scribe.extractors.BaseStringExtractorImpl;
import org.scribe.extractors.HeaderExtractorImpl;
import org.scribe.extractors.TokenExtractorImpl;
import org.scribe.model.OAuthConfig;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuth10aServiceImpl;
import org.scribe.oauth.OAuthService;
import org.scribe.services.HMACSha1SignatureService;
import org.scribe.services.TimestampServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.twitter.TwitterTemplate;
import org.springframework.stereotype.Repository;

import com.pt.springsocial.example.entity.User;

@Repository
public class TwitterProvider {
	@Value("${twitter.apiKey}")
	private String apiKey;
	@Value("${twitter.apiSecret}")
	private String apiSecret;
	@Value("${twitter.callbackUrl}")
	private String callbackUrl;
	
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}


	public void setApiSecret(String apiSecret) {
		this.apiSecret = apiSecret;
	}


	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}


	public OAuthService getOAuthService() {
	    OAuthConfig config = new OAuthConfig();
	    config.setRequestTokenEndpoint("https://api.twitter.com/oauth/request_token");
	    config.setAccessTokenEndpoint("https://api.twitter.com/oauth/access_token");
	    config.setAccessTokenVerb(Verb.POST);
	    config.setRequestTokenVerb(Verb.POST);
	    config.setApiKey(apiKey);
	    config.setApiSecret(apiSecret);
	    config.setCallback(callbackUrl);
	 
	    return new OAuth10aServiceImpl(
	            new HMACSha1SignatureService(),
	            new TimestampServiceImpl(),
	            new BaseStringExtractorImpl(),
	            new HeaderExtractorImpl(),
	            new TokenExtractorImpl(),
	            new TokenExtractorImpl(),
	            config);
	}
	
	public String getAuthorizeUrl() {
		return "https://api.twitter.com/oauth/authorize";
	}
	
	/** Factory method to create LinkedIn template (request-scoped) for current user
	 * 
	 * @return
	 */
	public TwitterTemplate createTemplate(User user) {
	    if (user.getTwitterAccessToken() != null) {
	    	return new TwitterTemplate(apiKey, apiSecret, user.getTwitterAccessToken(), user.getTwitterSecret());
	    } else {
	    	return null;
	    }
	}
}
