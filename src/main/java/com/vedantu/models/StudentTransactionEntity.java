package com.vedantu.models;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.vedantu.enums.TransactionType;

@Entity
@Table(name = "transactions")
public class StudentTransactionEntity {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;	// transaction id - > primary key
	
	private Long accountId;	// foreign key to id in accounts table
	private int amount;
	
	
	@Enumerated(EnumType.STRING)
	// to store as string otherwise ordinal value will be stored
	private TransactionType transactionType;
	private int closingAmount;
	private Timestamp createdTime;
	
	// default constructor
	public StudentTransactionEntity() {
		super();
	}
	
	// constructor with parameters
	// useful in converting to this object while inserting into db
	// without id -> it will set default while inserting into mysql db by save()
	public StudentTransactionEntity(Long accountId, int amount, TransactionType transactionType, int closingAmount, Timestamp createdTime) {
		this.accountId = accountId;
		this.amount = amount;
		this.transactionType  = transactionType;
		this.closingAmount = closingAmount;
		this.createdTime = createdTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public TransactionType getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(TransactionType transactionTYpe) {
		this.transactionType = transactionTYpe;
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
	
}
