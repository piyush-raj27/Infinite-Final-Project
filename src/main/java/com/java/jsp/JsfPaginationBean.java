package com.java.jsp;



import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

public class JsfPaginationBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Patient> cdList;
	private PatientDaoImpl queryHelper;
//	private PatientDaoImpl patientdaoimpl;
	
	/**
	 * pagination stuff
	 */
	
	static private int firstRow;
	private int rowsPerPage;
	private int totalPages;
	private int pageRange;
	private Integer[] pages;
	private int currentPage;
	/**
	 * Creates a new instance of JsfPaginationBean
	 */
	public JsfPaginationBean() {
		queryHelper = new PatientDaoImpl();
		/**
		 * the below function should not be called in real world application
		 */
		// Set default values somehow (properties files?).
//		patientdaoimpl = new PatientDaoImpl();
		rowsPerPage = 5; // Default rows per page (max amount of rows to be displayed at once).
		pageRange = 3; // Default page range (max amount of page links to be displayed at once).
	}
	public List<Patient> getPatientList() {
		Map<String, Object> sessionMap =
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		if (cdList == null) {
			loadPatient();
		}
		else if(cdList.isEmpty() || cdList.size()==0) {
			sessionMap.put("NotFound", "No Records Found");
		}
		patientList=cdList;
		return cdList;
	}
	private int totalRows;
	public List<Patient> getCdList() {
		return cdList;
	}
	public void setCdList(List<Patient> cdList) {
		this.cdList = cdList;
	}
	public PatientDaoImpl getQueryHelper() {
		return queryHelper;
	}
	public void setQueryHelper(PatientDaoImpl queryHelper) {
		this.queryHelper = queryHelper;
	}
	
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	public int getFirstRow() {
		return firstRow;
	}
	public void setFirstRow(int firstRow) {
		this.firstRow = firstRow;
	}
	public int getRowsPerPage() {
		return rowsPerPage;
	}
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getPageRange() {
		return pageRange;
	}
	public void setPageRange(int pageRange) {
		this.pageRange = pageRange;
	}
	public Integer[] getPages() {
		return pages;
	}
	public void setPages(Integer[] pages) {
		this.pages = pages;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	
	
	private void loadPatient() {
		System.out.println("First Row  " +firstRow);
		System.out.println("Count  " +rowsPerPage);
		cdList = queryHelper.getListOfPatient(firstRow, rowsPerPage);
		System.out.println("Patient Count is  " + cdList);
		totalRows = queryHelper.countRows();
		System.out.println("Total Rows  " +totalRows);
		// Set currentPage, totalPages and pages.
		
		currentPage = (totalRows / rowsPerPage) - ((totalRows - firstRow) / rowsPerPage) + 1;
		totalPages = (totalRows / rowsPerPage) + ((totalRows % rowsPerPage != 0) ? 1 : 0);
		int pagesLength = Math.min(pageRange, totalPages);
		pages = new Integer[pagesLength];
		// firstPage must be greater than 0 and lesser than totalPages-pageLength.
		int firstPage = Math.min(Math.max(0, currentPage - (pageRange / 2)), totalPages - pagesLength);
		// Create pages (page numbers for page links).
		for (int i = 0; i < pagesLength; i++) {
			pages[i] = ++firstPage;
		}
	}
	// Paging actions
	// -----------------------------------------------------------------------------
	public void pageFirst() {
		page(0);
	}
	public void pageNext() {
		page(firstRow + rowsPerPage);
	}
	public void pagePrevious() {
		page(firstRow - rowsPerPage);
	}
	public void pageLast() {
		int lastPageFirstRow = totalRows - ((totalRows % rowsPerPage != 0) ? totalRows % rowsPerPage : rowsPerPage);
	    page(lastPageFirstRow);
	}
	public void page(ActionEvent event) {
		page(((Integer) ((UICommand) event.getComponent()).getValue() - 1) * rowsPerPage);
	}
	private void page(int firstRow) {
		this.firstRow = firstRow;
		loadPatient();
	}
	
	
//	public PatientDaoImpl getPatientdaoimpl() {
//		return patientdaoimpl;
//	}
//	public void setPatientdaoimpl(PatientDaoImpl patientdaoimpl) {
//		this.patientdaoimpl = patientdaoimpl;
//	

	
private boolean sortAscending = true;
	
	private List<Patient> patientList;
	
	public List<Patient> getPatientList1() {
		return patientList;
	}
	
	
	public void setPatientList(List<Patient> patientList) {
		this.patientList = patientList;
	}
	//------- Sort By Phoneno
	public List<Patient> sortByPhoneno() {
 
			System.out.println();
		   if(sortAscending){
				
			//ascending order
			Collections.sort(patientList,new Comparator<Patient>() {

				public int compare(Patient o1, Patient o2) {
					// TODO Auto-generated method stub
					return o1.getPhoneno().compareTo(o2.getPhoneno());
				}
			});
 
			System.out.println("Inside  " +patientList);
			sortAscending = false;
				
		   }
 
		   System.out.println("Sorted One  " +patientList);
		   return patientList;
		}
	
	//---------Sort By FirstName------------------
	public List<Patient> sortByFirstName(){
		System.out.println();
		if(sortAscending) {
			Collections.sort(patientList,new Comparator<Patient>() {
				public int compare(Patient o3, Patient o4) {
					return o3.getFirstName().compareTo(o4.getFirstName());
				}
			});
			System.out.println("Inside " + patientList);
			sortAscending = false;
		}
		
		System.out.println("Sorted One " + patientList);
		return patientList;
	}
	//-------------Sort By Uhid----------------------
	public List<Patient> sortByUhid(){
		System.out.println();
		if(sortAscending) {
			Collections.sort(patientList, new Comparator<Patient>() {
				public int compare(Patient o5, Patient o6) {
					return o5.getUhid().compareTo(o6.getUhid());
				}
			});
			System.out.println("Inside " + patientList);
			sortAscending = false;
		}
		
		System.out.println("Sorted One " + patientList);
		return patientList;
	}
}
