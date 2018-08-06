package com.camas.chorusdigital.security;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

//@PasswordMatches
public class RegistrationRequest {

	@NotNull(message="User name is required")
	@NotEmpty(message="User name is required")
	private String userName;

	@NotNull(message="First name is required")
	@NotEmpty(message="First name is required")
	private String firstName;

	@NotNull(message="Last name is required")
	@NotEmpty(message="Last name is required")
	private String lastName;

	@NotNull(message="Password is required")
	@NotEmpty(message="Password is required")
	private String password;

	@NotNull(message="Password match is required")
	@NotEmpty(message="Password match is required")
	private String password2;

	@NotNull(message="Email is required")
	@NotEmpty(message="Email is required")
	@ValidEmail
	private String email;

	@NotNull(message="Phone is required")
	@NotEmpty(message="Phone is required")
	private String phone;

	private boolean termsAgreed;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isTermsAgreed() {
		return termsAgreed;
	}

	public void setTermsAgreed(boolean termsAgreed) {
		this.termsAgreed = termsAgreed;
	}
}
