package com.boeing.zuul.sample.security;

public class OAuthAdditionalInfo {
	
	private String firstName;
	private String lastName;
	private String bemsId;
	private String email;

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
	public String getBemsId() {
		return bemsId;
	}
	public void setBemsId(String bemsId) {
		this.bemsId = bemsId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "OAuthAdditionalInfo [firstName=" + firstName + ", lastName=" + lastName + ", bemsId=" + bemsId
				+ ", email=" + email + "]";
	}
}
