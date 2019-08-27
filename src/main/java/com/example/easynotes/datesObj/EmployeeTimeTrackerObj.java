package com.example.easynotes.datesObj;

import java.io.Serializable;
import java.sql.Date;

import com.example.easynotes.model.HumanResources;
import com.example.easynotes.model.Purchases;
import com.example.easynotes.model.Sales;

public class EmployeeTimeTrackerObj implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HumanResources hr;
	private Date startDate;
	private Date endDate;

 
 
 
 

	public HumanResources getHr() {
		return hr;
	}

	public void setHr(HumanResources hr) {
		this.hr = hr;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
