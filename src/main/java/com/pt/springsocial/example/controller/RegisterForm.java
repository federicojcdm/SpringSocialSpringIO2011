package com.pt.springsocial.example.controller;

import javax.validation.constraints.NotNull;

/** Form used for Registration
 * 
 * @author fcaro
 *
 */
public class RegisterForm {
	@NotNull
	public String userName;
	@NotNull
	public String password;
	@NotNull
	public String confirmPassword;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}