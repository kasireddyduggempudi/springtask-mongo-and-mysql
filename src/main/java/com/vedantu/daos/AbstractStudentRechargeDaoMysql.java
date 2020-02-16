package com.vedantu.daos;

import java.util.List;

import com.vedantu.models.StudentAccountEntity;
import com.vedantu.models.StudentTransactionEntity;

public interface AbstractStudentRechargeDaoMysql {
	
	// recharge methods
	
	public boolean rechargeOrDeductionDataSaving(StudentAccountEntity accountDoc, StudentTransactionEntity transactionDoc, boolean newAccount);
	
	public int getAvailableAmountById(String objectId) throws Exception;

	public List<StudentTransactionEntity> getRechargeHistoryById(String objectId) throws Exception;
	
	public StudentAccountEntity getAccountDetailsByObjectId(String objectId);

	/*
	 * public int getTotalAmountPaidById(String id) throws Exception;
	 * 
	 * public List<StudentRechargeEntityMysql> getRechargeHistoryById(String id)
	 * throws Exception;
	 */
}
