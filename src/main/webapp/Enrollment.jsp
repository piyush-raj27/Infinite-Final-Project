<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>


<f:view>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
  
    <body>
    <center>
       <h:form id="form">
            <h2>Add Patient</h2>
            <hr/>
            
    <%--     <label>UHID</label>
         <h:inputText  id="uhid" value="#{patient.uhid}"/><br/><br/>   --%>
            <label>First Name</label>
            <h:inputText id="firstName" value="#{patient.firstName}"/><br/><br/>
             <h:message for="form:firstName"></h:message>
	          

            <label>Last Name</label>
            <h:inputText id="lastName" value="#{patient.lastName}"/><br/><br/>
             <h:message for="form:lastName"></h:message>
	        
            <label>Gender:</label>
            <h:selectOneMenu id="gender" value="#{patient.gender}">
                <f:selectItem itemLabel="Select Gender" itemValue="" />
                <f:selectItem itemLabel="MALE" itemValue="MALE" />
                <f:selectItem itemLabel="FEMALE" itemValue="FEMALE" />
                <f:selectItem itemLabel="OTHER" itemValue="OTHER" />
            </h:selectOneMenu>

            <label>Date of Birth:</label>
            <h:inputText id="dob" value="#{patient.dob}">
                <f:convertDateTime pattern="dd/MM/yyyy" />
            </h:inputText><br/><br/>

            <label>UserName</label>
            <h:inputText id="userName" value="#{patient.userName}"/><br/><br/>
	       <h:message for="form:userName" /><br/>
            <br/><br/>
            <label>Email</label>
            <h:inputText id="email" value="#{patient.email}"/><br/><br/>
            <h:message for="form:email" /><br/>
            <br/><br/>
            <label>Phone Number</label>
            <h:inputText id="phoneno" value="#{patient.phoneno}"/><br/><br/>
            <h:message for="form:phoneno"></h:message>
	         <br/><br/>  
            <label>Address</label>
            <h:inputText id="address" value="#{patient.address}"/><br/><br/>
            
             <br/><br/>
              <label>Medical History</label>
            <h:inputText id="medHistory" value="#{patient.medHistory}"/><br/><br/>
            <br/><br/>
           <h:commandButton action="#{patientController.addPatientValid(patient)}" value="Sign Up" />
	       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	       <input type="reset" value="Reset">
        </h:form>
      </center>
    </body>
   
</html>
</f:view>