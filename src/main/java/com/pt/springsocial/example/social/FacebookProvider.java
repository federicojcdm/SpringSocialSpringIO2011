package com.pt.springsocial.example.social;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.facebook.FacebookTemplate;
import org.springframework.stereotype.Repository;

import com.pt.springsocial.example.entity.User;

/** Facebook provider bean
 * It is required to use <facebook:init/> tag to create form for login into facebook
 * 
 * @author fcaro
 *
 */
@Repository("facebookProvider")
public class FacebookProvider {
	@Value("${facebook.appId}")
	private String apiKey;
	@Value("${facebook.appSecret}")
	private String appSecret;

	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	
	/** Create Facebook template for currently logged-in user
	 * 
	 * @return
	 */
	public FacebookTemplate createTemplate(User user) {
	    if (user.getFacebookAccessToken() != null ) {
	    	return new FacebookTemplate(user.getFacebookAccessToken());
	    } else {
	    	return null;
	    }
	}
}
