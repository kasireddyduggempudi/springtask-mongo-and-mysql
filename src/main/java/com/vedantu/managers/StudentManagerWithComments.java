package com.vedantu.managers;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.vedantu.daos.StudentDao;
import com.vedantu.daos.StudentSqlDao;
import com.vedantu.enums.TransactionType;
import com.vedantu.models.StudentModel;
import com.vedantu.models.StudentTransactionEntity;
import com.vedantu.models.StudentAccountEntity;
import com.vedantu.models.StudentAccountEntity;
import com.vedantu.requests.Address;
import com.vedantu.requests.StudentRechargeRequest;
import com.vedantu.requests.StudentReq;

@Service
public class StudentManagerWithComments {
	
	/* StudentManager plays a crucial role.
	 * It takes request data from controllers, converts them into model objects 
	 * and gives them to StudentDao or StudentSqlDao,
	 * where these model objects directly inserted or updated into database
	 */
	
	@Autowired
	private StudentDao studentDao;
	
	@Autowired 
	private StudentSqlDao studentSqlDao;
	
	/* adding to database after validation and checking for duplicates */
	public boolean add(StudentReq stu) throws Exception {
		//validation
		if(stu.getFirstName() != null && stu.getLastName() != null && stu.getContactNumber() != null &&
				stu.getEmail() != null && stu.getCity() != null  && stu.getState() != null && stu.getCountry() != null 
				&& stu.getPincode() != null) {
			
			// everything is fine with data
			//check for email or phone duplicates			
			if(studentDao.getByEmail(stu.getEmail()) != null ||  studentDao.getByContactNumber(stu.getContactNumber()) != null) {
				// means trying to add duplicate email or contactNumber
				throw new Exception("Invalid details. Duplicate Data!!!!");
			}else {
				Address address = new Address(stu.getCity(), stu.getState(), stu.getCountry(), stu.getPincode());
				String fullName = stu.getFirstName() + " " + stu.getLastName();
				StudentModel stuModel = new StudentModel(stu.getFirstName(), stu.getLastName(), fullName, stu.getContactNumber(),stu.getEmail() ,address);
				return studentDao.add(stuModel);
			}
		}else {
			// invalid data
			return false;	
		}
	}
	
	
	/* getById */
	public StudentModel getById(String id) {
		return studentDao.getById(id);
	}

	/* getByEmail */
	public StudentModel getByEmail(String email) {
		return studentDao.getByEmail(email);
	}

	/* getList */
	public List<StudentModel> getList(){
		return studentDao.getList();
	}
	
	/* updateByEmail */
	public StudentModel updateByEmail(String email, StudentReq stu) throws Exception {
		if(email != null && (!email.isEmpty())) {
			if(studentDao.getByEmail(email) != null) {
				//validation
				if(stu.getFirstName() != null && stu.getLastName() != null && stu.getContactNumber() != null &&
						stu.getEmail() != null && stu.getCity() != null  && stu.getState() != null && stu.getCountry() != null 
						&& stu.getPincode() != null) {
					
					// creating new StudentModel document from the StudentReq data
					Address address = new Address(stu.getCity(), stu.getState(), stu.getCountry(), stu.getPincode());
					String fullName = stu.getFirstName() + " " + stu.getLastName();
					StudentModel newDoc = new StudentModel(stu.getFirstName(), stu.getLastName(), fullName, stu.getContactNumber(),stu.getEmail() ,address);
					return studentDao.updateByEmail(email, newDoc);
				}else {
					throw new Exception("not valid data");
				}
			}else {
				throw new Exception("No email found!!!");
			}
		}else {
			throw new Exception("empty email");
		}
	}
	
	/* deleteById */
	public StudentModel deleteById(String id) {
		return studentDao.deleteById(id);
	}
	
	// recharge methods 
	
	/* recharge */
	public boolean recharge(StudentRechargeRequest stuRechargeReq) throws Exception {
		if(studentDao.isIdExists(stuRechargeReq.getObjectId())) {
			
			// to check  if already objectId existed in accounts table or null
			StudentAccountEntity accountDoc = studentSqlDao.getAccountDetailsByObjectId(stuRechargeReq.getObjectId());
			StudentTransactionEntity transactionDoc = null;
			
			boolean newAccount = true; // to know newAccount or existed
			
			// for current time
			Date date = new Date();
			long time  = date.getTime();
			Timestamp ts = new Timestamp(time);
			
			if(accountDoc == null) {
				// not existed  previously
				
				accountDoc = new StudentAccountEntity(stuRechargeReq.getObjectId(), stuRechargeReq.getAmountPaid(), ts, ts);
				/*
				 * accountDoc.setObjectId(stuRechargeReq.getObjectId());
				 * accountDoc.setClosingAmount(stuRechargeReq.getAmountPaid());
				 * accountDoc.setCreatedTime(ts); 
				 * accountDoc.setLastUpdatedTime(ts);
				 */
				// createdTime, lastUpdateTime will set default in table
				// id will be created by save method while inserting
				
				//creating StudentTransactionEntity entry
				// accountId will be set in dao class after getting by inserting accountDoc
				transactionDoc = new StudentTransactionEntity();
				transactionDoc.setAmount(stuRechargeReq.getAmountPaid());
				transactionDoc.setTransactionType(TransactionType.CREDIT);
				transactionDoc.setClosingAmount(stuRechargeReq.getAmountPaid());
				transactionDoc.setCreatedTime(ts);
				//createdTime will set default in mysql table or else we can set
				
				newAccount = true; //   newAccount  it is. actually no need. already set in global
				
			}else {
				// already existed
				/*updating accountDetails */
				accountDoc.setClosingAmount(accountDoc.getClosingAmount() + stuRechargeReq.getAmountPaid());
				accountDoc.setLastUpdatedTime(ts);
				// on update not working.
				
				/*creating new StudentTransactionEntity entry */
				transactionDoc = new StudentTransactionEntity(accountDoc.getId(), stuRechargeReq.getAmountPaid(), TransactionType.CREDIT, accountDoc.getClosingAmount(), ts);
				// setting objectId from the accountDetails
				/* transactionDoc.setAccountId(accountDoc.getId());
				transactionDoc.setAmount(stuRechargeReq.getAmountPaid());
				transactionDoc.setTransactionType(TransactionType.CREDIT);
				transactionDoc.setClosingAmount(accountDoc.getClosingAmount()); // already set in accountDoc
				transactionDoc.setCreatedTime(ts);
				*/
			
				System.out.println("objectid existed in accounts table");
				
				newAccount = false;	/* means existed account */ 
			}
			return studentSqlDao.rechargeOrDeductionDataSaving(accountDoc, transactionDoc, newAccount );
		}else {
			throw new Exception("object id not found in students collection");
		}
	}

	/* deduction */
	public boolean deduction(StudentRechargeRequest stuRechargeReq) throws Exception {
		if(studentDao.isIdExists(stuRechargeReq.getObjectId())) {
			
			// to check  if already objectId existed in accounts table or null
			StudentAccountEntity accountDoc = studentSqlDao.getAccountDetailsByObjectId(stuRechargeReq.getObjectId());
			StudentTransactionEntity transactionDoc = null;
			
			/* boolean newAccount = false;  // no need, only existed will be given to stuSqlDao*/
			
			// for current time
			Date date = new Date();
			long time  = date.getTime();
			Timestamp ts = new Timestamp(time);
			
			if(accountDoc == null) {
				// not existed  previously
				// so not possible for deduction. throw error
				throw new Exception("Zero money in your account. Not possible to deduct now. Please recharge.");
				
			}else {
				// already existed
				// checking whether user closingAmount is greater than or equal deduction request amount
				if(stuRechargeReq.getAmountPaid() <= accountDoc.getClosingAmount()) {
					// sufficient amount in user account. so remove the request amount and update
					/*updating accountDetails */
					accountDoc.setClosingAmount(accountDoc.getClosingAmount() - stuRechargeReq.getAmountPaid());
					accountDoc.setLastUpdatedTime(ts);
					
					/*creating new StudentTransactionEntity entry */
					transactionDoc = new StudentTransactionEntity(accountDoc.getId(), stuRechargeReq.getAmountPaid(), TransactionType.DEBIT, accountDoc.getClosingAmount(), ts);
					
					/*
					// setting objectId from the accountDetails
					transactionDoc.setAccountId(accountDoc.getId());
					transactionDoc.setAmount(stuRechargeReq.getAmountPaid());
					transactionDoc.setTransactionType(TransactionType.DEBIT);
					transactionDoc.setClosingAmount(accountDoc.getClosingAmount()); // already set in accountDoc
					transactionDoc.setCreatedTime(ts);
					*/
					return studentSqlDao.rechargeOrDeductionDataSaving(accountDoc, transactionDoc, false );
				}else {
					throw new Exception("Not sufficent balance. Please recharge !!!");
				}				
			}
		}else {
			throw new Exception("object id not found in students collection");
		}
	}

	public int getAvailableAmountById(String id) throws Exception {
		if(studentDao.isIdExists(id)) {
			return studentSqlDao.getAvailableAmountById(id);
		}else {
			throw new Exception("object id not found in students collection");
		}
	}	
	
	public List<StudentTransactionEntity> getRechargeHistoryById(String id) throws Exception {
		if(studentDao.isIdExists(id)) {
			return studentSqlDao.getRechargeHistoryById(id);
		}else {
			throw new Exception("object id not found in students collection");
		}
	}


}
