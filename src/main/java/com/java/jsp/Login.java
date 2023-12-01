package com.java.jsp;

import java.io.Serializable;

public class Login implements Serializable {
    private int authId;
    private String uhid;
    private String username;
    private String passcode; 
    private String otp;
    private String email;
	public int getAuthId() {
		return authId;
	}
	public void setAuthId(int authId) {
		this.authId = authId;
	}
	public String getUhid() {
		return uhid;
	}
	public void setUhid(String uhid) {
		this.uhid = uhid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasscode() {
		return passcode;
	}
	public void setPasscode(String passcode) {
		this.passcode = passcode;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "Login [authId=" + authId + ", uhid=" + uhid + ", username=" + username + ", passcode=" + passcode
				+ ", otp=" + otp + ", email=" + email + "]";
	}
	public Login(int authId, String uhid, String username, String passcode, String otp, String email) {
		super();
		this.authId = authId;
		this.uhid = uhid;
		this.username = username;
		this.passcode = passcode;
		this.otp = otp;
		this.email = email;
	}
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
    
    

}