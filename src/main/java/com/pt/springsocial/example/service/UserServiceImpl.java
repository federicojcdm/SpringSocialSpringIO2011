package com.pt.springsocial.example.service;

import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pt.springsocial.example.dao.UserDao;
import com.pt.springsocial.example.entity.User;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {
	@Autowired
	UserDao userDao;
	
	/* (non-Javadoc)
	 * @see com.pt.springsocial.example.service.UserService#findByName(java.lang.String)
	 */
	@Override
	public User findByName(String userName) {
		return userDao.findByName(userName);
	}

	/** method to support UserDetailsService interface
	 * 
	 */
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
		User user = userDao.findByName(userName);
		
		if (user == null) {
			throw new UsernameNotFoundException(userName);
		}
		
		org.springframework.security.core.userdetails.User userDetails = 
			new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), 
																true, true, true, true, 
																new ArrayList<GrantedAuthority>());

		return userDetails;
		
	}

	/* (non-Javadoc)
	 * @see com.pt.springsocial.example.service.UserService#createNewUser(java.lang.String, java.lang.String)
	 */
	@Override
	public void createNewUser(String userName, String password) {
		User user = new User();
		user.setName(userName);
		user.setPassword(password);

		userDao.saveOrUpate(user);
	}
	
	/** Update LinkedIn Authentication Information for user
	 * 
	 * @param userName
	 * @param token
	 * @param secret
	 */
	public void updateLinkedInAuthentication(String userName, String token, String secret) {
		User user = userDao.findByName(userName);
		user.setLinkedInAccessToken(token);
		user.setLinkedInSecret(secret);
		
		userDao.saveOrUpate(user);
	}
	
	/** Update Facebook connection information
	 * 
	 * @param userName
	 * @param accessToken
	 * @param facebookUserId
	 */
	public void updateFacebookAuthentication(String userName, String accessToken, String facebookUserId) {
		User user = userDao.findByName(userName);
		user.setFacebookAccessToken(accessToken);
		user.setFacebookUserId(facebookUserId);
		
		userDao.saveOrUpate(user);
	}

	/** Update Twitter Authentication Information for user
	 * 
	 * @param userName
	 * @param token
	 * @param secret
	 */
	public void updateTwitterAuthentication(String userName, String token, String secret) {
		User user = userDao.findByName(userName);
		user.setTwitterAccessToken(token);
		user.setTwitterSecret(secret);
		
		userDao.saveOrUpate(user);
	}	
}
