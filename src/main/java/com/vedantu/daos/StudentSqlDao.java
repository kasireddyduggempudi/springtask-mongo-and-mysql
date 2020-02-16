package com.vedantu.daos;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vedantu.models.StudentAccountEntity;
import com.vedantu.models.StudentTransactionEntity;

@Service
public class StudentSqlDao implements AbstractStudentRechargeDaoMysql{

	@Autowired
	private MysqlConnection mysqlConnection;
	
	// to get the SessionFactory from mysqlConnection
	// this will be used to create Session object through which database queries done
	public SessionFactory getSessionFactory() {
		return mysqlConnection.getSessionFactory();
	}
	
	/* rechargeById */
	public boolean rechargeOrDeductionDataSaving(StudentAccountEntity accountDoc, StudentTransactionEntity transactionDoc, boolean newAccount){
		/* it is just to save the accounts record and transactions record
		 * in the tables given by the StudentManager class 
		 * all the logic regarding credit, debit done and it just gives the
		 * respective objects to store.
		 * this will store those objects
		 * 
		 * newAccount tells if the accountDoc is new object that is objectId is not in the accounts tables
		 */
			Session session = getSessionFactory().openSession();
			Transaction transaction = session.getTransaction();
			try {
				transaction.begin();
				
				if(newAccount) {
					// this will be useful for only recharging. for deductiong it wont useful
					Long accountId =(Long) session.save(accountDoc);	// saving in accounts table
					transactionDoc.setAccountId(accountId);
					session.save(transactionDoc);	// saving in transactions table
					System.out.println("new account created");
				}else {
					// means already account created in accounts table
					session.update(accountDoc); // updating account details
					session.save(transactionDoc); // saving in transactions table;
					System.out.println("account updated");
				}
				
				transaction.commit();
				return true;
			}catch(Exception e) {
				System.out.println(e + " in sqlDao");
				return false;
			}finally {
				session.close();
			}
	}
	
	/* getTotalAmountPaid */
	public int getAvailableAmountById(String objectId) throws Exception {
			Session session = getSessionFactory().openSession();
			Transaction transaction = session.getTransaction();
			try {
				StudentAccountEntity accountDoc = getAccountDetailsByObjectId(objectId);
				if(accountDoc == null) {
					// no object with given objectId
					System.out.println(" no objecct found");
					return 0;
				}else {
					return accountDoc.getClosingAmount();
				}
			}catch(Exception e) {
				System.out.println(e);
				return 0;
			}finally {
				session.close();
			}
		
	}
	
	/* getRechargeHistoryById */
	public List<StudentTransactionEntity> getRechargeHistoryById(String objectId) throws Exception {
			Session session = getSessionFactory().openSession();
			Transaction transaction = session.getTransaction();
			try {
				transaction.begin();
				String sql = "SELECT  t.* FROM  transactions t, accounts a  where a.objectId=:objectId and a.id = t.accountId";
				NativeQuery query = session.createNativeQuery(sql);
				query.addEntity(StudentTransactionEntity.class);
				query.setParameter("objectId", objectId);
				List<StudentTransactionEntity> list = query.list();
				transaction.commit();
				return list;
			}catch(Exception e) {
				System.out.println(e);
				return null;
			}finally {
				session.close();
			}
	}
	
	/* getAccountDetailsByObjectId */
	/* useful in knowing whether account already existed in accounts table */
	
	public StudentAccountEntity getAccountDetailsByObjectId(String objectId){
		StudentAccountEntity accountDetails = null;
		Session session = getSessionFactory().openSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			String sql = "SELECT * FROM accounts where objectId=:objectId";
			NativeQuery<Object> query = session.createNativeQuery(sql);
			query.addEntity(StudentAccountEntity.class);
			query.setParameter("objectId", objectId);
			accountDetails = (StudentAccountEntity) query.uniqueResult();
			transaction.commit();
		}catch(Exception e) {
			System.out.println(e + " in sqlDao getAcccountDetails");
		}finally {
			session.close();
		}
		return accountDetails;
	}
	
}
