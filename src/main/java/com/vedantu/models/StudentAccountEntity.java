package com.vedantu.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "accounts")
public class StudentAccountEntity {
	
	/*
	 * avax.persistence.Id should be used instead of org.springframework.data.annotation.Id
	 */
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	public Long id;
	private String objectId;
	private int closingAmount;
	private Timestamp createdTime;
	private Timestamp lastUpdatedTime;

	public StudentAccountEntity() {
		super();
	}
	
	// constructor with parameters
	// useful in conveting into this model while inserting into db
	// without id (will be set in save() mysql default
	public StudentAccountEntity(String objectId, int closingAmount, Timestamp createdTime, Timestamp lastUpdatedTime) {
		this.objectId = objectId;
		this.closingAmount = closingAmount;
		this.createdTime = createdTime;
		this.lastUpdatedTime = lastUpdatedTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public int getClosingAmount() {
		return closingAmount;
	}

	public void setClosingAmount(int closingAmount) {
		this.closingAmount = closingAmount;
	}

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public Timestamp getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(Timestamp lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	

}

