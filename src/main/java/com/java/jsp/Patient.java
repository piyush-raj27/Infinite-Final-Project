package com.java.jsp;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



public class Patient implements Serializable {
	private String uhid;
	private String firstName;
	private String lastName;
    private Date dob;
    private String gender;
    private String userName;
    private String phoneno;
    private String email;
    private String status;
    private String medHistory;
    private String address;
    
	public String getUhid() {
		return uhid;
	}
	public void setUhid(String uhid) {
		this.uhid = uhid;
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
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhoneno() {
		return phoneno;
	}
	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getMedHistory() {
		return medHistory;
	}
	public void setMedHistory(String medHistory) {
		this.medHistory = medHistory;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "Patient [uhid=" + uhid + ", firstName=" + firstName + ", lastName=" + lastName + ", dob=" + dob
				+ ", gender=" + gender + ", userName=" + userName + ", phoneno=" + phoneno + ", email=" + email
				+ ", status=" + status + ",  medHistory=" + medHistory + ", address=" + address
				+ "]";
	}
	public Patient(String uhid, String firstName, String lastName, Date dob, String gender, String userName,
			String phoneno, String email, String status,  String medHistory, String address) {
		super();
		this.uhid = uhid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.gender = gender;
		this.userName = userName;
		this.phoneno = phoneno;
		this.email = email;
		this.status = status;
		
		this.medHistory = medHistory;
		this.address = address;
	}
	public Patient() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
    
    
}