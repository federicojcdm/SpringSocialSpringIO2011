package com.pt.springsocial.example.social.linkedin;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/** LinkedIn Person Activity object to be send into person-activities method
 * 
 * @author fcaro
 *
 */
@XmlRootElement(name = "activity")
public class LinkedInPersonActivity {
	public LinkedInPersonActivity() {
	}
	public LinkedInPersonActivity(String body) {
		this.body = body;
	}
	
	@XmlElement(name = "content-type")
	String contentType = "linkedin-html";
	
	@XmlElement(name = "body")
	String body;
}
