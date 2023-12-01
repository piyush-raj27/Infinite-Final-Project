package com.java.jsp;

import java.util.Map;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class PatientController {
	
	private PatientDaoImpl daoImpl;
	
	
	public PatientDaoImpl getDaoImpl() {
		return daoImpl;
	}
	public void setDaoImpl(PatientDaoImpl daoImpl) {
		this.daoImpl = daoImpl;
	}
	
	public String addPatientValid(Patient patient) {
		System.out.println(patient);
//		System.out.println(daoImpl);
		System.out.println("addPatientValid is Hittingggggggggg");
		System.out.println(addValid(patient));
		if(addValid(patient)) {
			return daoImpl.addPatient(patient);
		}
		return "";
	
	}
	
	
public boolean addValid(Patient patient) {
	FacesContext context = FacesContext.getCurrentInstance();
	String firstName = "^[A-Za-z\\s]+$";
	String lastName =  "^[A-Za-z\\s]+$";
	String userName =  "^[^\\s]{8,10}$";
	String phoneno = "^(\\+91|91|0)?[6789]\\d{9}$";
	String email = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

	
	 if(!Pattern.matches(userName, patient.getUserName())) {
			context.addMessage("form:userName", new FacesMessage("Patient Username Contains min 8 characters"));
		 System.out.println("UserName Failed...");
		 return false;
	 }
	 if(!Pattern.matches(firstName, patient.getFirstName())) {
		 context.addMessage("form:firstName", new FacesMessage("Invalid FirstName. Only letters are allowed."));
		 System.out.println("FirstName Failed...");
		 return false;
	 }
	 if(!Pattern.matches(lastName, patient.getLastName())) {
		 context.addMessage("form:lastName", new FacesMessage("Invalid LastName. Only letters are allowed."));
		 System.out.println("LastName Failed...");
		 return false;
	 }
	 if(!Pattern.matches(phoneno, patient.getPhoneno())) {
		 context.addMessage("form:phoneno", new FacesMessage("Invalid Phone Number."));
		 System.out.println("PhoneNo Failed...");
		 return false;
	 }
	 if(!Pattern.matches(email, patient.getEmail())) {
		 context.addMessage("form:email", new FacesMessage("Invalid Email."));
		 System.out.println("Email Failed...");
		 return false;
	 }
	 return true;
}
}
