package com.java.jsp;

import java.io.IOException;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class PatientDaoImpl implements Serializable {
	SessionFactory sf;
	Session session;

	
	public String addPatient(Patient patient) {
		String uhid = generateUHID();
		System.out.println("UH Id i  " + uhid);
		patient.setUhid(uhid);
		sf = SessionHelper.getConnection();
		session = sf.openSession();
		Transaction trans = session.beginTransaction();

		// Set the status to "Pending" by default
		patient.setStatus("Pending");

		// Save the Patient entity
		session.save(patient);
		trans.commit();
		session.close();

		String otp = generateOtp(8);

		String body = "Welcome to Mr/Miss " + patient.getFirstName() + "\r\n" + "Your OTP Generated Successfully"
				+ "\r\n" + "OTP is " + otp;

		MailSend.mailOtp(patient.getEmail(), "Otp Sent Successfully...", body);

		session = sf.openSession();
		Transaction trans2 = session.beginTransaction();

		Login login = new Login();
		login.setUhid(patient.getUhid());
		login.setUsername(patient.getUserName());
		login.setOtp(otp);
		login.setEmail(patient.getEmail());

		session.save(login);

		trans2.commit();
		session.close();

		return "Welcome.jsp?faces-redirect=true";
	}

//    public static String generateOtp(int length) {
//        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
//        SecureRandom secureRandom = new SecureRandom();
//        StringBuilder otp = new StringBuilder(length);
//
//        for (int i = 0; i < length; i++) {
//            int randomIndex = secureRandom.nextInt(characters.length());
//            char randomChar = characters.charAt(randomIndex);
//            otp.append(randomChar);
//        }
//
//        return otp.toString();
//    }
	public static String generateOtp(int length) {
		String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		SecureRandom secureRandom = new SecureRandom();
		StringBuilder otp = new StringBuilder(6);

		for (int i = 0; i < 6; i++) {
			int randomIndex = secureRandom.nextInt(characters.length());
			char randomChar = characters.charAt(randomIndex);
			otp.append(randomChar);
		}

		return otp.toString();
	}

//
//    private static int uhidCounter = 1;
//
//    private static String generateUHID() {
//        // Generate UHID in the format "IN0001", "IN0002", and so on
//        String uhid = String.format("IN%04d", uhidCounter);
//        uhidCounter++;
//        return uhid;
//    }

	private static String generateUHID() {
		SessionFactory sf;
		Session session;
		sf = SessionHelper.getConnection();
		session = sf.openSession();
		Transaction trans = session.beginTransaction();

		// Fetch the last UHID from the table
		Query query = session.createQuery("SELECT MAX(p.uhid) FROM Patient p");
		String lastUHID = (String) query.uniqueResult();

		if (lastUHID == null) {
			lastUHID = "IN0000"; // Set an initial value if the table is empty
		}

		// Extract the numeric part and increment it
		int numericPart = Integer.parseInt(lastUHID.substring(2));
		numericPart++;

		// Generate the new UHID with the incremented numeric part
		String newUHID = String.format("IN%04d", numericPart);
		System.out.println(newUHID);

		trans.commit();
		session.close();

		return newUHID;
	}
	// -----------------------Search Patient Medical
	// History(Piyush)---------------------------------------

///This is for radio Button
	private String matchingType;

	// Getter and Setter for matchingType
	public String getMatchingType() {
		return matchingType;
	}

	public void setMatchingType(String matchingType) {
		this.matchingType = matchingType;
		System.out.println("Matching Type set to: " + matchingType);
	}

	// This for reset button---
	public void clear() throws IOException {
		System.out.println("Clear");
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("daoImpl", null);

		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	}

	// Method for Search Insurance By Providerid
//	public List<Patient> showPatientDetails() {
//		sf = SessionHelper.getConnection();
//		session = sf.openSession();
//		Criteria criteria = session.createCriteria(Patient.class);
//		List<Patient> patientList = criteria.list();
//		return patientList;
//	}
	
//	public String showPatientDetails(String uhid) {
//	    sf = SessionHelper.getConnection();
//	    session = sf.openSession();
//	    
//	    Criteria criteria = session.createCriteria(Patient.class);
//	    criteria.add(Restrictions.eq("uhid", uhid));
//
//	    List<Patient> patientList = criteria.list();
//	    System.out.println(patientList);
//	    return "PatientDetails.jsp?faces-redirect=true";
//	    
//	}
	
	
	public String SearchUhid(String uhid) {
	   	 System.out.println("method is hitting in search");
	   	 System.out.println(uhid);
	        Session session = SessionHelper.getConnection().openSession();
		     Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		     Criteria criteria = session.createCriteria(Patient.class);
	        criteria.add(Restrictions.eq("uhid", uhid));
	       Patient patient=(Patient) criteria.uniqueResult();
	        sessionMap.put("patient", patient);
	        return "PatientDetails.jsp?faces-redirect=true";
	                
	    }

	public Criteria searchPatientByuhid(String uhid) {
		sf = SessionHelper.getConnection();
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.put("searchuhid", uhid);
		session = sf.openSession();
		Criteria criteria = session.createCriteria(Patient.class);
		// Restrictions is used for where clause query
		// sql query= select * from insurance_claim where providerid=?.
		criteria.add(Restrictions.ilike("uhid", "%" + uhid + "%"));
		// List<insurance_Claim> claimList = criteria.list();

		// Redirect to a jsp page
		return criteria;
	}

	public Criteria searchPatientByfirstName(String firstName) {
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.put("searchfirstName", firstName);
		sf = SessionHelper.getConnection();
		session = sf.openSession();
		Criteria criteria = session.createCriteria(Patient.class);
		criteria.add(Restrictions.ilike("firstName", "%" + firstName + "%"));
		return criteria;
	}

	public Criteria searchPatientByphoneno(String phoneno) {
		sf = SessionHelper.getConnection();
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.put("searchphoneno", phoneno);
		session = sf.openSession();
		Criteria criteria = session.createCriteria(Patient.class);
		criteria.add(Restrictions.ilike("phoneno", "%" + phoneno + "%"));
		// List<insurance_Claim> claimList = criteria.list();

		return criteria;
	}

	// --------------------------------------
	// ---------------Paging---------------

	private String localCode;

	public String getLocalCode() {
		return localCode;
	}

	public void setLocalCode(String localCode) {
		this.localCode = localCode;
	}

	public List<Patient> showPatientDao(int firstRow, int rowCount) {
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		String searchuhid = "";
		if (sessionMap.get("searchuhid") != null) {
			System.out.println("Found...");
			searchuhid = (String) sessionMap.get("searchuhid");
			Criteria cr = session.createCriteria(Patient.class);
			cr.add(Restrictions.eq("uhid", searchuhid));
			cr.setFirstResult(firstRow);
			cr.setMaxResults(rowCount);
			System.out.println(firstRow);
			System.out.println(rowCount);
			System.out.println(cr.list());
			return cr.list();

		}
		String searchfirstName = "";
		if (sessionMap.get("searchfirstName") != null) {
			System.out.println("Found...");
			searchfirstName = (String) sessionMap.get("searchfirstName");
			Criteria cr = session.createCriteria(Patient.class);
			cr.add(Restrictions.eq("firstName", searchfirstName));
			cr.setFirstResult(firstRow);
			cr.setMaxResults(rowCount);
			System.out.println(firstRow);
			System.out.println(rowCount);
			System.out.println(cr.list());
			return cr.list();

		}
		String searchphoneno = "";
		if (sessionMap.get("searchphoneno") != null) {
			System.out.println("Found...");
			searchphoneno = (String) sessionMap.get("searchphoneno");
			Criteria cr = session.createCriteria(Patient.class);
			cr.add(Restrictions.eq("phoneno", searchphoneno));
			cr.setFirstResult(firstRow);
			cr.setMaxResults(rowCount);
			System.out.println(firstRow);
			System.out.println(rowCount);
			System.out.println(cr.list());
			return cr.list();

		}

		return null;

	}

	public List<Patient> getListOfPatient(int firstRow, int rowCount) {
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		System.out.println("Method hitting.....");
		if (sessionMap.get("searchuhid") != null) {
			String uhid = (String) sessionMap.get("searchuhid");
			SessionFactory sf = SessionHelper.getConnection();
			Session session = sf.openSession();
			List<Patient> cdList = null;

			session.beginTransaction();
			Criteria criteria = searchPatientByuhid(uhid);
			criteria.setFirstResult(firstRow);
			criteria.setMaxResults(rowCount);

			System.out.println("From Pagination " + firstRow);
			System.out.println("From Pagination Count   " + rowCount);
			List<Patient> patientList = criteria.list();

			System.out.println(patientList);
			sessionMap.put("patientList", patientList);
			return patientList;
		}
		if (sessionMap.get("searchfirstName") != null) {
			String firstName = (String) sessionMap.get("searchfirstName");
			SessionFactory sf = SessionHelper.getConnection();
			Session session = sf.openSession();
			List<Patient> cdList = null;

			session.beginTransaction();
			Criteria criteria = searchPatientByfirstName(firstName);
			criteria.setFirstResult(firstRow);
			criteria.setMaxResults(rowCount);

			System.out.println("From Pagination " + firstRow);
			System.out.println("From Pagination Count   " + rowCount);
			List<Patient> patientList = criteria.list();

			System.out.println(patientList);
			sessionMap.put("patientList", patientList);
			return patientList;
		}
		if (sessionMap.get("searchphoneno") != null) {
			String phoneno = (String) sessionMap.get("searchphoneno");
			SessionFactory sf = SessionHelper.getConnection();
			Session session = sf.openSession();
			List<Patient> cdList = null;

			session.beginTransaction();
			Criteria criteria = searchPatientByphoneno(phoneno);
			criteria.setFirstResult(firstRow);
			criteria.setMaxResults(rowCount);

			System.out.println("From Pagination " + firstRow);
			System.out.println("From Pagination Count   " + rowCount);
			List<Patient> patientList = criteria.list();

			System.out.println(patientList);
			sessionMap.put("patientList", patientList);
			return patientList;
		}

		return null;
	}

	public int countRows() {
	    try {
	        SessionFactory sf = SessionHelper.getConnection();
	        Session session = sf.openSession();
	        session.beginTransaction();

	        Criteria criteria = session.createCriteria(Patient.class);
	        // Add criteria based on the search parameters, if applicable

	        int count = ((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();

	        session.getTransaction().commit();
	        session.close();

	        return count;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return 0;
	}

	
	


	// ----------------------------------------------------------------------
	// All Search in One Button
	// ----------------------------------------------------------------------

	// --------------------------------------------
	
	

	public Criteria showPatientUhid(String uhid, String firstName, String phoneno, String matchingType) {
	    if ((uhid == null || uhid.isEmpty()) && (firstName == null || firstName.isEmpty())
	            && (phoneno == null || phoneno.isEmpty())) {
	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                "Please provide at least one search parameter.", null);
	        FacesContext.getCurrentInstance().addMessage(null, message);
	        return null; // Returning null to stay on the same page
	    }

	    sf = SessionHelper.getConnection();
	    Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	    session = sf.openSession();
	    Criteria criteria = session.createCriteria(Patient.class);
	    

	    // Use AND conditions to match any of the provided parameters
		/* Disjunction disjunction = Restrictions.disjunction(); */
		/* Conjunction conjunction = Restrictions.conjunction(); */
	    // Adjust the matching type based on the radio button selection
	    if (matchingType.equals("startsWith")) {

	        if (uhid != null && !uhid.isEmpty()) {
	        	if (!uhid.matches("^[a-zA-Z0-9]+$")) {
	                System.out.println("Hitting uhid");
	                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "UHID should be alphanumeric.",
	                        null);
	                FacesContext.getCurrentInstance().addMessage("searchForm:uhid", message);
	                return null; // Returning null to stay on the same page
	            }
	            sessionMap.put("searchuhid", uhid);
	            criteria.add(Restrictions.like("uhid", uhid + "%"));
	            System.out.println("Generated SQL: " + criteria.toString());
	        }

	        if (firstName != null && !firstName.isEmpty()) {
	        	if (!firstName.matches("^[a-zA-Z]+$")) {
	                System.out.println("Hitting");
	                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                        "First Name should contain only characters.", null);
	                FacesContext.getCurrentInstance().addMessage("searchForm:firstName", message);
	                return null; // Returning null to stay on the same page
	            }
	            sessionMap.put("searchfirstName", firstName);
	            criteria.add(Restrictions.like("firstName", firstName + "%"));
	        }

	        if (phoneno != null && !phoneno.isEmpty()) {
	        	if (!phoneno.matches("^91")) {
	                System.out.println("Hitting");
	                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                        "Phone No should start with (91).", null);
	                FacesContext.getCurrentInstance().addMessage("searchForm:phoneno", message);
	                return null; // Returning null to stay on the same page
	            }
	            sessionMap.put("searchphoneno", phoneno);
	            criteria.add(Restrictions.like("phoneno", phoneno + "%"));
	        }

	    } else if (matchingType.equals("contains")) {

	        if (uhid != null && !uhid.isEmpty()) {
	        	
	        	 if (!uhid.matches("^[a-zA-Z0-9]+$")) {
		                System.out.println("Hitting uhid");
		                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "UHID should be alphanumeric.",
		                        null);
		                FacesContext.getCurrentInstance().addMessage("searchForm:uhid", message);
		                return null; // Returning null to stay on the same page
		            }
	            sessionMap.put("searchuhid", uhid);
	            criteria.add(Restrictions.ilike("uhid", "%" + uhid + "%"));
	            System.out.println("Generated SQL: " + criteria.toString());
	        }

	        if (firstName != null && !firstName.isEmpty()) {
	        	if (!firstName.matches("^[a-zA-Z]+$")) {
	                System.out.println("Hitting");
	                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                        "First Name should contain only characters.", null);
	                FacesContext.getCurrentInstance().addMessage("searchForm:firstName", message);
	                return null; // Returning null to stay on the same page
	            }
	            sessionMap.put("searchfirstName", firstName);
	            criteria.add(Restrictions.ilike("firstName", "%" + firstName + "%"));
	        }

	        if (phoneno != null && !phoneno.isEmpty()) {
	        	if (!phoneno.matches("^[0-9]+$")) {
	                System.out.println("Hitting");
	                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                        "Only Digits are Allowed.", null);
	                FacesContext.getCurrentInstance().addMessage("searchForm:phoneno", message);
	                return null; // Returning null to stay on the same page
	            }
	            sessionMap.put("searchphoneno", phoneno);
	            criteria.add(Restrictions.ilike("phoneno", "%" + phoneno + "%"));
	        }

	    } else {
	        // If no matching type is selected, match exactly
	        if (uhid != null && !uhid.isEmpty()) {
	            System.out.println("uhid: " + uhid);
	            if (!uhid.matches("^[a-zA-Z0-9]+$")) {
	                System.out.println("Hitting uhid");
	                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "UHID should be alphanumeric.",
	                        null);
	                FacesContext.getCurrentInstance().addMessage("searchForm:uhid", message);
	                return null; // Returning null to stay on the same page
	            }
	            sessionMap.put("searchuhid", uhid);
	            criteria.add(Restrictions.like("uhid", "%" + uhid + "%"));
	            System.out.println("Generated SQL: " + criteria.toString());
	        }

	        if (firstName != null && !firstName.isEmpty()) {
	            if (!firstName.matches("^[a-zA-Z]+$")) {
	                System.out.println("Hitting");
	                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                        "First Name should contain only characters.", null);
	                FacesContext.getCurrentInstance().addMessage("searchForm:firstName", message);
	                return null; // Returning null to stay on the same page
	            }
	            sessionMap.put("searchfirstName", firstName);
	            criteria.add(Restrictions.like("firstName", "%" + firstName + "%"));
	        }

	        if (phoneno != null && !phoneno.isEmpty()) {
	            if (!phoneno.matches("^d{10}$")) {
	                System.out.println("Hitting");
	                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                        "Phone No should be a 10-digit number.", null);
	                FacesContext.getCurrentInstance().addMessage("searchForm:phoneno", message);
	                return null; // Returning null to stay on the same page
	            }
	            sessionMap.put("searchphoneno", phoneno);
	            criteria.add(Restrictions.like("phoneno", "%" + phoneno + "%"));
	        }
	        
	        List<Patient> patientList = criteria.list();

	        if (patientList.isEmpty()) {
	            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
	                    "No matching records found.", null);
	            FacesContext.getCurrentInstance().addMessage(null, message);
	            return null; // Returning null to stay on the same page
	        }
	    }

//	    criteria.add(criteria);
	    return criteria;
	}

	
	
	/*
	 * public Criteria showPatientUhid(String uhid, String firstName, String
	 * phoneno) { if ((uhid == null || uhid.isEmpty()) && (firstName == null ||
	 * firstName.isEmpty()) && (phoneno == null || phoneno.isEmpty())) {
	 * FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
	 * "Please provide at least one search parameter.", null);
	 * FacesContext.getCurrentInstance().addMessage(null, message); return null; //
	 * Returning null to stay on the same page }
	 * 
	 * sf = SessionHelper.getConnection(); Map<String, Object> sessionMap =
	 * FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	 * session = sf.openSession(); Criteria criteria =
	 * session.createCriteria(Patient.class);
	 * 
	 * // Use OR conditions to match any of the provided parameters // Disjunction
	 * disjunction = Restrictions.disjunction();
	 * 
	 * // Adjust the matching type based on the radio button selection if
	 * (matchingType.equals("startsWith")) {
	 * 
	 * if (uhid != null && !uhid.isEmpty()) {
	 * System.out.println("RadioButton Hitting"); sessionMap.put("searchuhid",
	 * uhid); criteria.add(Restrictions.like("uhid", uhid + "%"));
	 * System.out.println("Generated SQL: " + criteria.toString()); }
	 * 
	 * if (firstName != null && !firstName.isEmpty()) {
	 * sessionMap.put("searchfirstName", firstName);
	 * criteria.add(Restrictions.like("firstName", firstName + "%")); }
	 * 
	 * if (phoneno != null && !phoneno.isEmpty()) { sessionMap.put("searchphoneno",
	 * phoneno); criteria.add(Restrictions.like("phoneno", phoneno + "%")); }
	 * 
	 * } else if (matchingType.equals("contains")) {
	 * 
	 * if (uhid != null && !uhid.isEmpty()) { sessionMap.put("searchuhid", uhid);
	 * criteria.add(Restrictions.like("uhid", "%" + uhid + "%")); }
	 * 
	 * if (firstName != null && !firstName.isEmpty()) {
	 * sessionMap.put("searchfirstName", firstName);
	 * criteria.add(Restrictions.like("firstName", "%" + firstName + "%")); }
	 * 
	 * if (phoneno != null && !phoneno.isEmpty()) { sessionMap.put("searchphoneno",
	 * phoneno); criteria.add(Restrictions.like("phoneno", "%" + phoneno + "%")); }
	 * 
	 * } else { // If no matching type is selected, match exactly if (uhid != null
	 * && !uhid.isEmpty()) { System.out.println("uhid: " + uhid); if
	 * (!uhid.matches("^[a-zA-Z0-9]+$")) { System.out.println("Hitting uhid");
	 * FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
	 * "UHID should be alphanumeric.", null);
	 * FacesContext.getCurrentInstance().addMessage("searchForm:uhid", message);
	 * return null; // Returning null to stay on the same page }
	 * sessionMap.put("searchuhid", uhid); criteria.add(Restrictions.eq("uhid",
	 * uhid)); }
	 * 
	 * if (firstName != null && !firstName.isEmpty()) { if
	 * (!firstName.matches("^[a-zA-Z]+$")) { System.out.println("Hitting");
	 * FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
	 * "First Name should contain only characters.", null);
	 * FacesContext.getCurrentInstance().addMessage("searchForm:firstName",
	 * message); return null; // Returning null to stay on the same page }
	 * sessionMap.put("searchfirstName", firstName);
	 * criteria.add(Restrictions.eq("firstName", firstName)); }
	 * 
	 * if (phoneno != null && !phoneno.isEmpty()) { if
	 * (!phoneno.matches("^\\d{10}$")) { System.out.println("Hitting"); FacesMessage
	 * message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
	 * "Phone No should be a 10-digit number.", null);
	 * FacesContext.getCurrentInstance().addMessage("searchForm:phoneno", message);
	 * return null; // Returning null to stay on the same page }
	 * sessionMap.put("searchphoneno", phoneno);
	 * criteria.add(Restrictions.eq("phoneno", phoneno)); } }
	 * 
	 * return criteria;
	 * 
	 * }
	 */
// 	public Criteria showPatientUhid(String uhid, String firstName ,String phoneno) {
// 		if ((uhid == null || uhid.isEmpty()) && (firstName == null || firstName.isEmpty()) && (phoneno == null || phoneno.isEmpty())) {
//          FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please provide at least one search parameter.", null);
//          FacesContext.getCurrentInstance().addMessage(null, message);
//          return null; // Returning null to stay on the same page
//      }
//	        
//	        sf = SessionHelper.getConnection();
//	        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
//	        session = sf.openSession();
//	        Criteria criteria = session.createCriteria(Patient.class);
//	 
//	        // Use OR conditions to match any of the provided parameters
//	      //  Disjunction disjunction = Restrictions.disjunction();
////	        FacesContext context = FacesContext.getCurrentInstance();
//	        
//	        if (uhid != null && !uhid.isEmpty()) {
//	        	sessionMap.put("searchuhid",uhid);
//	        	criteria.add(Restrictions.like("uhid", "%" + uhid + "%"));
//				
////				  if (!uhid.matches("^P[0-9]*$")) {
////					  System.out.println("invalid");
////				  context.addMessage("searchForm:providerid", new
////				  FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Provider ID.", null));
////				  return null; 
////				  }
//				 
//	           // disjunction.add(Restrictions.like("providerid", "%" + providerid + "%"));
//	        	
//	        }
//
//	        
//	        if (firstName != null && !firstName.isEmpty()) {
//	        	sessionMap.put("searchfirstName",firstName);
////	            if (!uHID.matches("^H[0-9]*$")) {
////	                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "UHID No should be alphanumeric.", null);
////	                FacesContext.getCurrentInstance().addMessage("searchForm:UHIDinput", message);
////	                return null; // Returning null to stay on the same page
////	            }
//	            criteria.add(Restrictions.like("firstName", "%" + firstName + "%"));
//	        }
//	        if (phoneno != null && !phoneno.isEmpty()) {
//	        	sessionMap.put("searchphoneno",phoneno);
////	            if (!disease.matches("^[a-zA-Z]*$")) {
////	                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Disease No should be words.", null);
////	                FacesContext.getCurrentInstance().addMessage("searchForm:Diseaseinput", message);
////	                return null; // Returning null to stay on the same page
////	            }
//	            criteria.add(Restrictions.like("phoneno", "%" + phoneno + "%"));
//	        }
//	        
//	       // criteria.add(disjunction);
//	       // System.out.println(disjunction);
//	 
//	        
//	        return criteria;
//	    }
//   
	// -----------------------------------

	public String showPatient(String uhid, String firstName, String phoneno) {
		sf = SessionHelper.getConnection();
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		session = sf.openSession();
		Criteria criteria = session.createCriteria(Patient.class);
		Disjunction disjunction = Restrictions.disjunction();

		if ((uhid == null || uhid.isEmpty()) && (firstName == null || firstName.isEmpty())
				&& (phoneno == null || phoneno.isEmpty())) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Please provide at least one search parameter.", null);
			FacesContext.getCurrentInstance().addMessage(null, message);
			return null; // Returning null to stay on the same page
		}

		// Adjust the matching type based on the radio button selection
		if ("startsWith".equals(matchingType)) {
			if (uhid != null && !uhid.isEmpty()) {
				disjunction.add(Restrictions.like("uhid", uhid + "%"));
			}

			if (firstName != null && !firstName.isEmpty()) {
				disjunction.add(Restrictions.like("firstName", firstName + "%"));
			}

			if (phoneno != null && !phoneno.isEmpty()) {
				disjunction.add(Restrictions.like("phoneno", phoneno + "%"));
			}

		} else if ("contains".equals(matchingType)) {
			if (uhid != null && !uhid.isEmpty()) {
				disjunction.add(Restrictions.like("uhid", "%" + uhid + "%"));
			}

			if (firstName != null && !firstName.isEmpty()) {
				disjunction.add(Restrictions.like("firstName", "%" + firstName + "%"));
			}

			if (phoneno != null && !phoneno.isEmpty()) {
				disjunction.add(Restrictions.like("phoneno", "%" + phoneno + "%"));
			}

		} else {
			// If no matching type is selected, match exactly
			if (uhid != null && !uhid.isEmpty()) {
				if (!uhid.matches("^[a-zA-Z0-9]+$")) {
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "UHID should be alphanumeric.",
							null);
					FacesContext.getCurrentInstance().addMessage("searchForm:uhid", message);
					return null; // Returning null to stay on the same page
				}

				disjunction.add(Restrictions.eq("uhid", uhid));
			}

			if (firstName != null && !firstName.isEmpty()) {
				if (!firstName.matches("^[a-zA-Z]+$")) {
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"First Name should contain only characters.", null);
					FacesContext.getCurrentInstance().addMessage("searchForm:firstName", message);
					return null; // Returning null to stay on the same page
				}

				disjunction.add(Restrictions.eq("firstName", firstName));
			}

			if (phoneno != null && !phoneno.isEmpty()) {
				if (!phoneno.matches("^\\d{10}$")) {
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Phone No should be a 10-digit number.", null);
					FacesContext.getCurrentInstance().addMessage("searchForm:phoneno", message);
					return null; // Returning null to stay on the same page
				}

				disjunction.add(Restrictions.eq("phoneno", phoneno));
			}
		}

		criteria.add(disjunction);

		List<Patient> patientList = criteria.list();
		sessionMap.put("patientList", patientList);
		System.out.println(patientList);
		return "SmartSearchPagNot.jsp?faces-redirect=true";
	}
	
	/*private String parameterType;
	
	public String getParameterType() {
		return parameterType;
	}

	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}

	public Criteria showPatientNew(String uhid, String firstName, String phoneno, String parameterType) {
		if ((uhid == null || uhid.isEmpty()) && (firstName == null || firstName.isEmpty())
	            && (phoneno == null || phoneno.isEmpty())) {
	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                "Please provide at least one search parameter.", null);
	        FacesContext.getCurrentInstance().addMessage(null, message);
	        return null; // Returning null to stay on the same page
	    }
		
		sf = SessionHelper.getConnection();
	    Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	    session = sf.openSession();
	    Criteria criteria = session.createCriteria(Patient.class);

	    
	    if("selectAll".equals(parameterType)) {
	    	if ("selectAll".equals(matchingType)) {
	            Conjunction conjunction = Restrictions.conjunction();

	            if (uhid != null && !uhid.isEmpty()) {
	                sessionMap.put("searchuhid", uhid);
	                conjunction.add(Restrictions.like("uhid", uhid + "%"));
	            }

	            if (firstName != null && !firstName.isEmpty()) {
	                sessionMap.put("searchfirstName", firstName);
	                conjunction.add(Restrictions.like("firstName", firstName + "%"));
	            }

	            if (phoneno != null && !phoneno.isEmpty()) {
	                sessionMap.put("searchphoneno", phoneno);
	                conjunction.add(Restrictions.like("phoneno", phoneno + "%"));
	            }

	            criteria.add(conjunction);
		
	}
	    	if("uhid".equals(parameterType)) {
	    		if (uhid != null && !uhid.isEmpty()) {
	    			 System.out.println("uhid: " + uhid);
	 	            if (!uhid.matches("^[a-zA-Z0-9]+$")) {
	 	                System.out.println("Hitting uhid");
	 	                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "UHID should be alphanumeric.",
	 	                        null);
	 	                FacesContext.getCurrentInstance().addMessage("searchForm:uhid", message);
	 	                return null; // Returning null to stay on the same page
	 	            }
	 	            sessionMap.put("searchuhid", uhid);
	 	            criteria.add(Restrictions.eq("uhid", uhid));
	    		}
	    			
	    		}
	    	
	    	if("firstName".equals(parameterType)) {
	    		if (firstName != null && !firstName.isEmpty()) {
	    			if (!firstName.matches("^[a-zA-Z]+$")) {
		                System.out.println("Hitting");
		                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
		                        "First Name should contain only characters.", null);
		                FacesContext.getCurrentInstance().addMessage("searchForm:firstName", message);
		                return null; // Returning null to stay on the same page
		            }
		            sessionMap.put("searchfirstName", firstName);
		            criteria.add(Restrictions.eq("firstName", firstName));
	    			
	    		}
	    		
	    	}
	    	
	    	if("phoneno".equals(parameterType)) {
	    		if (phoneno != null && !phoneno.isEmpty()) {
		            if (!phoneno.matches("^\\d{10}$")) {
		                System.out.println("Hitting");
		                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
		                        "Phone No should be a 10-digit number.", null);
		                FacesContext.getCurrentInstance().addMessage("searchForm:phoneno", message);
		                return null; // Returning null to stay on the same page
		            }
		            sessionMap.put("searchphoneno", phoneno);
		            criteria.add(Restrictions.eq("phoneno", phoneno));
	    		
	    	}
	    		
	    }
	    			
   }
	    return criteria;
	*/

}
	

//------------------------------------------------------------------------------------------------------------------------
