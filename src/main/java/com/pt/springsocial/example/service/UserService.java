package com.pt.springsocial.example.service;

import com.pt.springsocial.example.entity.User;

public interface UserService {

	public abstract User findByName(String userName);

	/** Create new user
	 * 
	 * @param userName
	 * @param password
	 */
	public abstract void createNewUser(String userName, String password);

	/** Update LinkedIn Authentication Information for user
	 * 
	 * @param userName
	 * @param token
	 * @param secret
	 */
	public abstract void updateLinkedInAuthentication(String userName, String token, String secret);

	/** Update Facebook connection information
	 * 
	 * @param userName
	 * @param accessToken
	 * @param facebookUserId
	 */
	public abstract void updateFacebookAuthentication(String userName, String accessToken, String facebookUserId);

	/** Update Twitter Authentication Information for user
	 * 
	 * @param userName
	 * @param token
	 * @param secret
	 */
	public abstract void updateTwitterAuthentication(String userName, String token, String secret);
	
}