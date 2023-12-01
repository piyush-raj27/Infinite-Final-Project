<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%> 
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%> 

<f:view>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h:form>
   
        		<center>
                <h2><h:outputText value="Patient Records"/></h2>
            </center>
		<h:dataTable value="#{paginationDao.getPatientList()}" var="i" border="3">
			<h:column>
				<f:facet name="header">
					<h:outputLabel value="UHID" />
				</f:facet>
				<h:outputText value="#{i.uhid}" />
			</h:column>
			<h:column>
				<f:facet name="header">
					<h:outputLabel value="First Name" />
				</f:facet>
				<h:outputText value="#{i.firstName}" />
			</h:column>
			<h:column>
				<f:facet name="header">
					<h:outputLabel value="last Name" />
				</f:facet>
				<h:outputText value="#{i.lastName}" />
			</h:column>

			<h:column>
				<f:facet name="header">
					<h:outputLabel value="DateOfBirth" />
				</f:facet>
				<h:outputText value="#{i.dob}" />
			</h:column>
			
			<h:column>
				<f:facet name="header">
					<h:outputLabel value="Gender" />
				</f:facet>
				<h:outputText value="#{i.gender}" />
			</h:column>
			
			<h:column>
				<f:facet name="header">
					<h:outputLabel value="Username" />
				</f:facet>
				<h:outputText value="#{i.userName}" />
			</h:column>
			<h:column>
				<f:facet name="header">
					<h:outputLabel value="Phone No" />
				</f:facet>
				<h:outputText value="#{i.phoneno}" />
			</h:column>
			<h:column>
				<f:facet name="header">
					<h:outputLabel value="Email" />
				</f:facet>
				<h:outputText value="#{i.email}" />
			</h:column>
			<h:column>
				<f:facet name="header">
					<h:outputLabel value="Status" />
				</f:facet>
				<h:outputText value="#{i.status}" />
			</h:column>
			<h:column>
				<f:facet name="header">
					<h:outputLabel value="Medical History" />
				</f:facet>
				<h:outputText value="#{i.medHistory}" />
			</h:column>
			<h:column>
				<f:facet name="header">
					<h:outputLabel value="Address" />
				</f:facet>
				<h:outputText value="#{i.address}" />
			</h:column>
			
			
		</h:dataTable>
		<!--The paging buttons-->
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
	</h:form>
    </body>
</html>
</f:view>
