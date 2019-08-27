package com.example.easynotes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.Date;

/**
 * Created by Hamada
 * المشتريات
 */
@Entity
@Table(name = "purchases" ,indexes = @Index(columnList = ("dayDate"),name="day_date_idx",unique=false))
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdAt", "updatedAt" }, allowGetters = true)
public class Purchases {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Double quantityInKillo;
 
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Categories category;

	@ManyToOne
	@JoinColumn(name = "human_resource_id")
	@NotNull
	private HumanResources humanResources;
	
	
	@NotNull
	private Double priceInKillo;
	
	
	// مدفوعات
	@NotNull
	private Double payments;
	
	// له
	// @NotNull
	private Double priceAllKillios;
	
	// الاجمالى
	// @NotNull
	private Double totalPrice;
	
	
	
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
//	 @DateTimeFormat(pattern = "YYYY-MM-DD")
//	 // @LastModifiedDate
	 private Date dayDate;
	 
	 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
  

 

	public Double getQuantityInKillo() {
		return quantityInKillo;
	}

	public void setQuantityInKillo(Double quantityInKillo) {
		this.quantityInKillo = quantityInKillo;
	}

	public Categories getcategory() {
		return category;
	}

	public void setcategory(Categories category) {
		this.category = category;
	}

	 

	public Double getPriceInKillo() {
		return priceInKillo;
	}

	public void setPriceInKillo(Double priceInKillo) {
		this.priceInKillo = priceInKillo;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public HumanResources getHumanResources() {
		return humanResources;
	}

	public void setHumanResources(HumanResources humanResources) {
		this.humanResources = humanResources;
	}

	public Double getPayments() {
		return payments;
	}

	public void setPayments(Double payments) {
		this.payments = payments;
	}

	public Double getPriceAllKillios() {
		return priceAllKillios;
	}

	public void setPriceAllKillios(Double priceAllKillios) {
		this.priceAllKillios = priceAllKillios;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getDayDate() {
		return dayDate;
	}

	public void setDayDate(Date dayDate) {
		this.dayDate = dayDate;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}
