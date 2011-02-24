package com.pt.springsocial.example.controller;

import org.hibernate.validator.constraints.NotEmpty;

/** Form object to pass status into controller
 * 
 * @author fcaro
 *
 */
public class StatusForm {
	@NotEmpty
	String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
