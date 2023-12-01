<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
 
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
 
<f:view>
	<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Details Page</title>
 
<style type="text/css">
body {

   filter: drop-shadow(2px 4px 6px black);
background-image: url("HospitalTheme1.jpg"); 
    background-color: #add8e6; /* Light blue background for the body */
    font-family: 'Arial', sans-serif;
    color: #333;
}
 
.container {
    opacity: 0.8;
    background-color: #f0f0f0; /* Light grey background for the container */
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* Optional: Add a subtle box shadow */
    width: 40%;
    margin: 0 auto; /* Center the container */
    text-decoration: none;
  display: inline-block;
  padding: 8px 16px;
}
/* .circle {
    width: 60px;
    height: 60px;
    background-color: #add8e6; /* Circle color */
    border-radius: 50%;
    top: -20px;
    left: 50%;
    margin-left: -20px; /* Center the circle horizontally */
} */

container:hover {
  background-color: #ddd;
  color: black;
}

.circle{
width: 100%; /* Adjust the width as needed */
    height: auto; /* Maintain aspect ratio */
    border-radius: 50%; /* Make it a circle */
}
.container h:outputLabel {
    color: black;
    font-weight: bold;
}
.name {
    text-align: center;
    margin-top: 10px;
}
.container h:outputText {
    font-weight: bold;
}
 
/* Add more styles as needed */
 
 
/* Add more styles as needed */
  
</style>
</head>
<body>
	
	 <h:form>
        <center>
          <h2>
		<h:outputText value="Patient Detail" />
	</h2>
          <div class="container">
          <!-- <div class="circle">
               <img src="profile.jpg" alt="Image Description">
          </div> -->
          
        <div class="name">
           <b> <h:outputLabel >Name:- </h:outputLabel></b>
          <h:outputText value="#{patient.getFirstName()}"/>&nbsp;<h:outputText value="#{patient.getLastName()}"/></br></br>
        </div>
     <b>   <h:outputLabel>UHID:- </h:outputLabel></b>
          <h:outputText value="#{patient.getUhid()}" style="background-color: yellow;"/><br></br>
        
                   <b> <h:outputLabel>DOB:- </h:outputLabel> </b>
          
          <h:outputText value="#{patient.getDob()}">
          <f:convertDateTime pattern="yyyy-MM-dd" /> </h:outputText>   </br></br>
                   <b>  <h:outputLabel>Gender:- </h:outputLabel></b>
           
          <h:outputText value="#{patient.getGender()}"/>    </br></br>
                 <b>  <h:outputLabel>UserName:- </h:outputLabel></b>
         
          <h:outputText value="#{patient.getUserName()}"/>    </br></br>
                  <b>   <h:outputLabel>PhoneNo:- </h:outputLabel></b>
          
          <h:outputText value="#{patient.getPhoneno()}"/>    </br></br>
                  <b>   <h:outputLabel>Email:- </h:outputLabel> </b>
          
          <h:outputText value="#{patient.getEmail()}"/>    </br></br>
          
           <b> <h:outputLabel>Status:- </h:outputLabel></b>
          
          <h:outputText value="#{patient.getStatus()}"/>    </br></br>
          
          <b> <h:outputLabel>Medical History:- </h:outputLabel></b>
          
          <h:outputText value="#{patient.getMedHistory()}"/>    </br></br>
          
         <b>  <h:outputLabel>Address:- </h:outputLabel> </b>
          
          <h:outputText value="#{patient.getAddress()}"/>    </br></br>
          
          
       
          </div>
          </center>
        </h:form>
</body>
	</html>
</f:view>