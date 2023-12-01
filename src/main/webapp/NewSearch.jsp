<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>JSP Page</title>
            <script type="text/javascript">
                function toggleInputText() {
                    var selectedValue = document.getElementById("searchForm:searchParameter").value;
                    var uhidInput = document.getElementById("searchForm:uhidInput");
                    var firstNameInput = document.getElementById("searchForm:firstNameInput");
                    var phonenoInput = document.getElementById("searchForm:phonenoInput");

                    // Hide all input fields
                    uhidInput.style.display = "none";
                    firstNameInput.style.display = "none";
                    phonenoInput.style.display = "none";

                    // Show the selected input field
                    if (selectedValue === "selectAll") {
                        uhidInput.style.display = "block";
                        firstNameInput.style.display = "block";
                        phonenoInput.style.display = "block";
                    } else if (selectedValue === "uhid") {
                        uhidInput.style.display = "block";
                    } else if (selectedValue === "firstName") {
                        firstNameInput.style.display = "block";
                    } else if (selectedValue === "phoneno") {
                        phonenoInput.style.display = "block";
                    }
                }

                // Initialize the input field based on the default value
                toggleInputText();
            </script>
        </head>
        <body>
            <center>
                <h2>Search Patients</h2>
                <h:form id="searchForm">
                    <h:panelGroup id="inputFields">
                        <h:selectOneMenu id="searchParameter" value="#{daoImpl.parameterType}" onchange="toggleInputText()">
                            <f:selectItem itemLabel="Select Any Parameter"/>
                            <f:selectItem itemValue="selectAll" itemLabel="Select All"/>
                            <f:selectItem itemValue="uhid" itemLabel="UHID"/>
                            <f:selectItem itemValue="firstName" itemLabel="First Name"/>
                            <f:selectItem itemValue="phoneno" itemLabel="Phone No"/>
                        </h:selectOneMenu></br></br>

                        <!-- UHID input field -->
                        <h:panelGroup id="uhidInput" style="display: none;">
                            <h:outputLabel for="uhid">UHID</h:outputLabel></br>
                            <h:inputText id="uhid" value="#{patient.uhid}" /></br>
                            <h:message for="uhid" style="color: red;" />
                        </h:panelGroup>

                        <!-- First Name input field -->
                        <h:panelGroup id="firstNameInput" style="display: none;">
                            <h:outputLabel  for="firstName">First Name</h:outputLabel></br>
                            <h:inputText id="firstName" value="#{patient.firstName}" /></br>
                            <h:message for="firstName" style="color: red;" />
                        </h:panelGroup>

                        <!-- Phone Number input field -->
                        <h:panelGroup id="phonenoInput" style="display: none;">
                            <h:outputLabel for="phoneno">Phone No</h:outputLabel></br>
                            <h:inputText id="phoneno" value="#{patient.phoneno}" /></br>
                            <h:message for="phoneno" style="color: red;" />
                        </h:panelGroup>
                    </h:panelGroup> </br>

                    <!-- Search button -->
                    <h:commandButton action="#{daoImpl.showPatientNew(patient.uhid,patient.firstName,patient.phoneno,daoImpl.parameterType)}" value="Search" />
                    &nbsp;&nbsp;&nbsp;
                    <!-- Reset Button -->
                    <h:commandButton action="#{daoImpl.clear()}" value="Reset" />
                </h:form>
                <h:messages id="messages" globalOnly="true" style="color: red;" />
            </center>

            <h:form id="patientListForm">
                <center>
                    <h2><h:outputText value="Patient Records"/></h2>
                </center>
                <center>
                    <h:dataTable value="#{patientList}" var="e" border="3">
                         <h:column>
				<f:facet name="header">
					<h:outputLabel value="UHID" />
				</f:facet>
				<h:outputText value="#{e.uhid}" />
			</h:column>
			<h:column>
				<f:facet name="header">
					<h:outputLabel value="First Name" />
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
					<h:outputLabel value="Phone No" />
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
                    <h:commandButton value="first" action="#{paginationDao.pageFirst}"
                             disabled="#{paginationDao.firstRow == 0}" />
            <h:commandButton value="prev" action="#{paginationDao.pagePrevious}"
                             disabled="#{paginationDao.firstRow == 0}" />
            <h:outputText value="&nbsp;" escape="false"/>
            <h:commandButton value="next" action="#{paginationDao.pageNext}"
                             disabled="#{paginationDao.firstRow + paginationDao.rowsPerPage >= paginationDao.totalRows}" />
            <h:outputText value="&nbsp;" escape="false"/>
            <h:commandButton value="last" action="#{paginationDao.pageLast}"
                             disabled="#{paginationDao.firstRow + paginationDao.rowsPerPage >= paginationDao.totalRows}" />
            <h:outputText value="&nbsp;" escape="false"/>
            <h:outputText value="Page #{paginationDao.currentPage} / #{paginationDao.totalPages}" />
            <br />
            <!-- Set rows per page -->
            <h:outputLabel for="rowsPerPage" value="Rows per page" />
            <h:inputText id="rowsPerPage" value="#{paginationDao.rowsPerPage}" size="3" maxlength="3" />
            <h:commandButton value="Set" action="#{paginationDao.pageFirst}" />
            <h:message for="rowsPerPage" errorStyle="color: red;" />
                </center>
            </h:form>
        </body>
    </html>
</f:view>
