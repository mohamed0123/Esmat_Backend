package com.example.easynotes.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Hamada سلف الموظف
 */
@Entity
@Table(name = "employee_ancestor")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdAt", "updatedAt" }, allowGetters = true)
public class EmployeeAncestor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
 
	@ManyToOne
	@JoinColumn(name = "employee_work_hour_and_salary_id")
	@NotNull
	private EmployeeWorkHourAndSalary employeeWorkHourSalary;

	// السلفه بالجنيه
	@NotNull
	private Double ancestor;

	// هل سدد السلفه
	@NotBlank
	private String isStillNotPaid;
	
	
	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updatedAt;
	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dayDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

 

	 

	public EmployeeWorkHourAndSalary getEmployeeWorkHourSalary() {
		return employeeWorkHourSalary;
	}

	public void setEmployeeWorkHourSalary(EmployeeWorkHourAndSalary employeeWorkHourSalary) {
		this.employeeWorkHourSalary = employeeWorkHourSalary;
	}

	public String getIsStillNotPaid() {
		return isStillNotPaid;
	}

	public void setIsStillNotPaid(String isStillNotPaid) {
		this.isStillNotPaid = isStillNotPaid;
	}

	public Double getAncestor() {
		return ancestor;
	}

	public void setAncestor(Double ancestor) {
		this.ancestor = ancestor;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Date getDayDate() {
		return dayDate;
	}

	public void setDayDate(Date dayDate) {
		this.dayDate = dayDate;
	}

}
