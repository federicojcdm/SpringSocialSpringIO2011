package com.pt.springsocial.example.dao;

import java.util.List;

import javax.persistence.EntityManagerFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

import com.pt.springsocial.example.entity.User;

/** DAO for working with users
 * 
 * @author fcaro
 *
 */
@Repository
public class UserDao extends JpaDaoSupport {
	@Autowired
	public UserDao(EntityManagerFactory entityManagerFactory) {
		setEntityManagerFactory(entityManagerFactory);
	}
	
	@SuppressWarnings("unchecked")
	public User findByName(String userName) {
		List<User> users = getJpaTemplate().findByNamedQuery("User.findByName", userName);
		
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

	/** Persist user object
	 * 
	 * @param user
	 */
	public void saveOrUpate(User user) {
		getJpaTemplate().persist(user);
	}

}
