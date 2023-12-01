<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>

<f:view>
	<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP Page</title>
<link rel="stylesheet" type="text/css" href="SearchStyle.css">
<script>
	function toggleInputText() {
		var selectedValue = document
				.getElementById("searchForm:searchParameter").value;
		var uhidInput = document.getElementById("searchForm:uhidInput");
		var firstNameInput = document
				.getElementById("searchForm:firstNameInput");
		var phonenoInput = document.getElementById("searchForm:phonenoInput");
		 var radioInput = document.getElementById("searchForm:radioInput"); 
		/* var searchLabel = document.getElementById("searchForm:searchLabel"); */
		/* var searchInput = document.getElementById("searchForm:searchInput");
		var resetInput = document.getElementById("searchForm:resetInput"); */

		// Hide all input fields
		uhidInput.style.display = "none";
		firstNameInput.style.display = "none";
		phonenoInput.style.display = "none";
		 radioInput.style.display = "none"; 
		/* searchInput.style.display="none";
		resetInput.style.display="none"; */

		// Show the selected input field
		if (selectedValue === "selectAll") {
			uhidInput.style.display = "block";
			firstNameInput.style.display = "block";
			phonenoInput.style.display = "block";
			 radioInput.style.display = "block"; 
			/* searchInput.style.display="block";
			resetInput.style.display="block"; */

		} else if (selectedValue === "uhid") {
			uhidInput.style.display = "block";
			 radioInput.style.display = "block";
			/* searchLabel.style.display = "block"; */
			/* searchInput.style.display="block";
			resetInput.style.display="block"; */
		} else if (selectedValue === "firstName") {
			firstNameInput.style.display = "block";
			 radioInput.style.display = "block";
			/* searchInput.style.display="block";
			resetInput.style.display="block"; */
		} else if (selectedValue === "phoneno") {
			phonenoInput.style.display = "block";
			 radioInput.style.display = "block"; 
			/* searchInput.style.display="block";
			resetInput.style.display="block"; */
		}
	}

	// Initialize the input field based on the default value
	toggleInputText();
</script>

<script>
        // JavaScript to hide the loader after the page is fully loaded
        document.addEventListener("DOMContentLoaded", function() {
            var loader = document.querySelector(".loader");
            loader.style.display = "none";
        });
    </script>


</head>
<body>

  <div class="loader"></div>
	<center>

		<h2>SEARCH PATIENTS</h2>

		<h:form id="searchForm" style="margin-top: 20px;">
			<!-- Search parameter dropdown -->
			
			<h:panelGroup id="inputFields">
				<h:selectOneMenu id="searchParameter" styleClass="para" onchange="toggleInputText()">
					<f:selectItem itemValue="selectedValue"
						itemLabel="-----Select Parameter-----" />
					<f:selectItem id="paraType" itemValue="selectAll" itemLabel="Select All" />
					<f:selectItem itemValue="uhid" itemLabel="UHID" />
					<f:selectItem itemValue="firstName" itemLabel="First Name" />
					<f:selectItem itemValue="phoneno" itemLabel="Phone No" />
				</h:selectOneMenu>
				<i class="fas fa-caret-down"></i>
				</br>
				</br>
				



                <!-- UHID input field -->
				<h:panelGroup id="uhidInput" styleClass="css"
					style="display: #{not empty patient.uhid ? 'block' : 'none'};">
					<h:outputLabel for="uhid">UHID</h:outputLabel>
					<br />
					<h:inputText id="uhid" value="#{patient.uhid}" />
					<br />
					<b><h:message for="uhid" style="color: red;" /></b>
				</h:panelGroup>


				<!-- First Name input field -->
				<h:panelGroup id="firstNameInput" styleClass="css"
					style="display: #{not empty patient.firstName ? 'block' : 'none'};">
					<h:outputLabel for="firstName">First Name</h:outputLabel>
					</br>
					<h:inputText id="firstName" value="#{patient.firstName}" />
					</br>
					<b><h:message for="firstName" style="color: red;" /></b>
				</h:panelGroup>


				<!-- Phone Number input field -->
				<h:panelGroup id="phonenoInput" styleClass="css"
					style="display: #{not empty patient.phoneno ? 'block' : 'none'};">
					<h:outputLabel for="phoneno">Phone No</h:outputLabel>
					</br>
					<h:inputText id="phoneno" value="#{patient.phoneno}" />
					</br>
					<b><h:message for="phoneno" style="color: red;" /></b>
				</h:panelGroup>
			</h:panelGroup>
			</br>

			 <!-- Radio buttons for matching type -->
			 <h:panelGroup id="radioInput" styleClass="radio" style="display: #{not empty daoImpl.matchingType ? 'block' : 'none'};">
				<h:selectOneRadio id="matchingType" value="#{daoImpl.matchingType}">
				<b> <f:selectItem itemValue="startsWith" itemLabel="Starts With" /></b>	
				<b>	<f:selectItem itemValue="contains" itemLabel="Contains" /></b>
				</h:selectOneRadio>
			 </h:panelGroup>
			</br> 

			<!-- Search button -->
			
			<h:commandButton styleClass="button"
				action="#{daoImpl.showPatientUhid(patient.uhid, patient.firstName, patient.phoneno, daoImpl.matchingType)}"
				value="Search" onclick="toggleBlur()" />
                &nbsp;&nbsp;&nbsp;
                <!-- Reset Button -->
			<h:commandButton styleClass="button" action="#{daoImpl.clear()}"
				value="Reset" />
				
		</h:form>
		<b><h:messages id="messages" globalOnly="true" style="color: red" /></b>
	</center>

	<h:form id="patientListForm">
		<center>

			 <h:panelGroup rendered="#{not empty paginationDao.getPatientList()}">
				<h2>
					<h:outputText value="PATIENT RECORDS" />
				</h2>
		</center>
		<center>
			<h:dataTable value="#{paginationDao.getPatientList()}" var="e"
				border="3" styleClass="funkyDataTable">
				<h:column>
					<f:facet name="header">
						<h:commandLink action="#{paginationDao.sortByUhid()}" value="UHID" styleClass="commandLink"/>
					</f:facet>
					 <h:commandButton value="#{e.uhid}"
                     action="#{daoImpl.SearchUhid(e.uhid)}" styleClass="commandButtonStyle" />
				</h:column>
				<h:column>
					<f:facet name="header">
						<h:commandLink action="#{paginationDao.sortByFirstName()}" value="First Name" styleClass="commandLink"/>
					</f:facet>
					<h:outputText value="#{e.firstName}" />

				</h:column>
				<h:column>
					<f:facet name="header">
						<h:outputLabel value="Last Name" />
					</f:facet>
					<h:outputText value="#{e.lastName}" />
				</h:column>
				<h:column>
					<f:facet name="header">
						<h:outputLabel value="Date Of Birth" />
					</f:facet>
					<h:outputText value="#{e.dob}">
						<f:convertDateTime pattern="yyyy-MM-dd" />
					</h:outputText>
				</h:column>

				<h:column>
					<f:facet name="header">
						<h:outputLabel value="Gender" />
					</f:facet>
					<h:outputText value="#{e.gender}" />
				</h:column>
				<h:column>
					<f:facet name="header">
						<h:outputLabel value="UserName" />
					</f:facet>
					<h:outputText value="#{e.userName}" />
				</h:column>
				<h:column>
					<f:facet name="header">
						<h:commandLink action="#{paginationDao.sortByPhoneno()}" value="Phone No" styleClass="commandLink"/>
					</f:facet>
					<h:outputText value="#{e.phoneno}" />
				</h:column>
				<h:column>
					<f:facet name="header">
						<h:outputLabel value="Email" />
					</f:facet>
					<h:outputText value="#{e.email}" />
				</h:column>
				<h:column>
					<f:facet name="header">
						<h:outputLabel value="Status" />
					</f:facet>
					<h:outputText value="#{e.status}" />
				</h:column>
				<h:column>
					<f:facet name="header">
						<h:outputLabel value="Medical History" />
					</f:facet>
					<h:outputText value="#{e.medHistory}" />
				</h:column>
				<h:column>
					<f:facet name="header">
						<h:outputLabel value="Address" />
					</f:facet>
					<h:outputText value="#{e.address}" />
				</h:column>
			</h:dataTable>

			<h:commandButton value="first" action="#{paginationDao.pageFirst}" styleClass="funkyPagination"
				disabled="#{paginationDao.firstRow == 0}" />
			<h:commandButton value="prev" action="#{paginationDao.pagePrevious}" styleClass="funkyPagination"
				disabled="#{paginationDao.firstRow == 0}" />
			<h:outputText value="&nbsp;" escape="false" />
			<h:commandButton value="next" action="#{paginationDao.pageNext}" styleClass="funkyPagination"
				disabled="#{paginationDao.firstRow + paginationDao.rowsPerPage >= paginationDao.totalRows}" />
			<h:outputText value="&nbsp;" escape="false" />
			<h:commandButton value="last" action="#{paginationDao.pageLast}" styleClass="funkyPagination"
				disabled="#{paginationDao.firstRow + paginationDao.rowsPerPage >= paginationDao.totalRows}" />
			<h:outputText value="&nbsp;" escape="false" />
			<h:outputText
				value="Page #{paginationDao.currentPage} / #{paginationDao.totalPages}" styleClass="funkyPagination" />
			<br />
			<!-- Set rows per page -->
			<h:outputLabel for="rowsPerPage" value="Rows per page" />
			<h:inputText id="rowsPerPage" value="#{paginationDao.rowsPerPage}"
				size="3" maxlength="3" />
			<h:commandButton value="Set" action="#{paginationDao.pageFirst}" styleClass="funkyPagination" />
			<h:message for="rowsPerPage" errorStyle="color: red;" />
		 </h:panelGroup> 

		</center>


	</h:form>
</body>
	</html>
</f:view>
