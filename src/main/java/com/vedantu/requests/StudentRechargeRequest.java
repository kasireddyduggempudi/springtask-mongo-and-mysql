package com.vedantu.requests;

public class StudentRechargeRequest{
	// this is what we get from students (it is for mysql)
	// both for recharge and deduction
	// based on recharge or deduction, we code logic in managers
	
	private String objectId;
	private int amountPaid;
	
	
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public int getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(int amountPaid) {
		this.amountPaid = amountPaid;
	}
}