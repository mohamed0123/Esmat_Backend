package com.example.easynotes.datesObj;

import java.io.Serializable;
import java.sql.Date;

import com.example.easynotes.model.EmployeeWorkHourAndSalary;
import com.example.easynotes.model.HumanResources;

public class EmployeeAncestorObj implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EmployeeWorkHourAndSalary employeeWorkHourAndSalary;
	private Date startDate;
	private Date endDate;

 
  
	public EmployeeWorkHourAndSalary getEmployeeWorkHourAndSalary() {
		return employeeWorkHourAndSalary;
	}

	public void setEmployeeWorkHourAndSalary(EmployeeWorkHourAndSalary employeeWorkHourAndSalary) {
		this.employeeWorkHourAndSalary = employeeWorkHourAndSalary;
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
