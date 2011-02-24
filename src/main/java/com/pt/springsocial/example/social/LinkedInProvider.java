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
import org.springframework.stereotype.Repository;

import com.pt.springsocial.example.entity.User;
import com.pt.springsocial.example.social.linkedin.LinkedInTemplateExt;

@Repository
public class LinkedInProvider {
	@Value("${linkedin.apiKey}")
	private String apiKey;
	@Value("${linkedin.apiSecret}")
	private String apiSecret;
	@Value("${linkedin.callbackUrl}")
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
	    config.setRequestTokenEndpoint("https://api.linkedin.com/uas/oauth/requestToken");
	    config.setAccessTokenEndpoint("https://api.linkedin.com/uas/oauth/accessToken");
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
		return "https://www.linkedin.com/uas/oauth/authorize";
	}
	
	/** Factory method to create LinkedIn template (request-scoped) for current user
	 * 
	 * @return
	 */
	public LinkedInTemplateExt createTemplate(User user) {
	    if (user.getLinkedInAccessToken() != null) {
	    	return new LinkedInTemplateExt(apiKey, apiSecret, user.getLinkedInAccessToken(), user.getLinkedInSecret());
	    } else {
	    	return null;
	    }
	}
}
