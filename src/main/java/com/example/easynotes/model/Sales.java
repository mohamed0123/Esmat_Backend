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
 * Created by Hamada المبيعات
 */
@Entity
@Table(name = "sales")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdAt", "updatedAt" }, allowGetters = true)
public class Sales {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "human_resource_id")
	@NotNull
	private HumanResources humanResources;

	@NotNull
	private Double quantityInKillo;

	@ManyToOne
	@JoinColumn(name = "category_id")
	@NotNull
	private Categories category;

	@NotNull
	private Double priceInKillo;
	
	// السعر الكلى للمنتج فى هذه العمليه
	//@NotNull
	private Double priceAllKillios;

	// الحساب الكلى
	//@NotNull
	private Double totalPrice;

	// وارد الفلوس اللى دفعها
	@NotNull
	private Double incomingMoney;

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

	public Double getPriceInKillo() {
		return priceInKillo;
	}

	public void setPriceInKillo(Double priceInKillo) {
		this.priceInKillo = priceInKillo;
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

	 

	public Double getIncomingMoney() {
		return incomingMoney;
	}

	public void setIncomingMoney(Double incomingMoney) {
		this.incomingMoney = incomingMoney;
	}

 

	public HumanResources getHumanResources() {
		return humanResources;
	}

	public void setHumanResources(HumanResources humanResources) {
		this.humanResources = humanResources;
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

}
