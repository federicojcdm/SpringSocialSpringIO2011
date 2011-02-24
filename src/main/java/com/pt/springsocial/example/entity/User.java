package com.pt.springsocial.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/** user Entity - to store users registered in the system
 * 
 * @author fcaro
 *
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "User.findByName", query = "SELECT u FROM User u where name = ?")
})
public class User {
	/** primary key for user */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer	id;
	
	/** Unique User Name */
	@Column(nullable=false, unique=true)
	private String name;
	
	/** Password of user */
	@Column(nullable=false)
	private String password;

	// linkedin connection information
	private String linkedInAccessToken;
	private String linkedInSecret;
	
	// facebook connection information
	private String facebookAccessToken;
	private String facebookUserId;
	
	// twitter connection information
	private String twitterAccessToken;
	private String twitterSecret;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLinkedInAccessToken() {
		return linkedInAccessToken;
	}

	public void setLinkedInAccessToken(String linkedInAccessToken) {
		this.linkedInAccessToken = linkedInAccessToken;
	}

	public String getLinkedInSecret() {
		return linkedInSecret;
	}

	public void setLinkedInSecret(String linkedInSecret) {
		this.linkedInSecret = linkedInSecret;
	}

	public String getFacebookAccessToken() {
		return facebookAccessToken;
	}

	public void setFacebookAccessToken(String facebookAccessToken) {
		this.facebookAccessToken = facebookAccessToken;
	}

	public String getFacebookUserId() {
		return facebookUserId;
	}

	public void setFacebookUserId(String facebookUserId) {
		this.facebookUserId = facebookUserId;
	}

	public String getTwitterAccessToken() {
		return twitterAccessToken;
	}

	public void setTwitterAccessToken(String twitterAccessToken) {
		this.twitterAccessToken = twitterAccessToken;
	}

	public String getTwitterSecret() {
		return twitterSecret;
	}

	public void setTwitterSecret(String twitterSecret) {
		this.twitterSecret = twitterSecret;
	}
}
