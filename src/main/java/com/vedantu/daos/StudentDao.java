package com.vedantu.daos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.vedantu.models.StudentModel;


/* It is stored in the IOC bean container. Initialized only once and will be there forever until
 * termination 
 */

@Service
public class StudentDao implements AbstractStudentDao{
	
	// this will connect to database to perform queries on student collection
	// this will use studentModel either to insert, query or to get 
	// and this will be accessed by the controller which will send the result to client
	
	
	// this class will depend on MongoConnection
	//MongoConnection will create connection and return the MongoOperations instance
	/*
	 * @Autowired private MongoClient mongoClient;
	 */
	
	@Autowired
	private MongoConnection mongoConnection;
	
	
	// gives the MongoTemplate object on which all db operations done!!!!
	public MongoTemplate getMongoTemplate(){
		// mongoClient object contains getMongoTemplate method which returns the MongoTemplate instance
		return mongoConnection.getMongoTemplate();
	}
	
	
	
	private final String COLLECTION = "students";
	
	// add student document to collectio. returns true on success otherwise false
	public boolean add(StudentModel stuDoc){
		System.out.println(stuDoc.getClass().getSimpleName());
		try {
			getMongoTemplate().insert(stuDoc, COLLECTION);
		}catch(Exception e) {
			System.out.println(e);
			return false; // something went wrong
		}
		return true;
	}
	
	// fetch the student document based on id. if not found returns null
	public StudentModel getById(String id) {
		StudentModel data = null;
		try {
			Query q = new Query(Criteria.where("_id").is(id));
			data = getMongoTemplate().findOne(q, StudentModel.class, COLLECTION);
		}catch(Exception e) {
			System.out.println(e);
		}
		return data;
	}
	
	//  fetch the student document based on email. if not found returns null
	public StudentModel getByEmail(String email) {
		StudentModel data = null;
		try {
			Query q = new Query(Criteria.where("email").is(email));
			data = getMongoTemplate().findOne(q, StudentModel.class, COLLECTION);
		}catch(Exception e) {
			System.out.println(e);
		}
		return data;
	}
	
	// fetch the data based on contactNumber. if not found returns null
	public StudentModel getByContactNumber(Long contactNumber) {
		StudentModel data = null;
		try {
			Query q = new Query(Criteria.where("contactNumber").is(contactNumber));
			data = getMongoTemplate().findOne(q, StudentModel.class, COLLECTION);
		}catch(Exception e) {
			System.out.println(e);
		}
		return data;
	}
	
	//fetch all the studnet documents
	public List<StudentModel> getList(){
		List<StudentModel> data = null;
		try {
			data = getMongoTemplate().findAll(StudentModel.class, COLLECTION);
		}catch(Exception e) {
			System.out.println(e);
		}
		return data;
	}
	
	// updates based on email and new data given
	public StudentModel updateByEmail(String email, StudentModel newDoc) {
		StudentModel updatedDoc = null;
		try {
			Query q = new Query(Criteria.where("email").is(email));
			
			Update update = new Update();
			// updating all the fields except email and contactNumber
			update.set("firstName", newDoc.getFirstName());
			update.set("lastName", newDoc.getLastName());
			update.set("fullName", newDoc.getFullName());
			update.set("address", newDoc.getAddress());
			
		    /** Set option about returning updated document otherwise old one will be returned*/
		    FindAndModifyOptions options = new FindAndModifyOptions();
		    options.returnNew(true);
		    
			// updating document  based on the query matched
		    // if not matched returns null
			updatedDoc = getMongoTemplate().findAndModify(q, update, options, StudentModel.class, COLLECTION);
		}catch(Exception e){
			System.out.println(e);
		}
		return updatedDoc;
	}
	
	public StudentModel deleteById(String id) {
		StudentModel deletedDoc = null;
		try {
			Query q = new Query(Criteria.where("id").is(id));
			deletedDoc = getMongoTemplate().findAndRemove(q, StudentModel.class, COLLECTION);
		}catch(Exception e){
			System.out.println(e);
		}
		return deletedDoc;
	}
	
	/* isIdExists */
	public boolean isIdExists(String id) {
		try {
			Query q = new Query(Criteria.where("id").is(id));
			StudentModel data = getMongoTemplate().findOne(q, StudentModel.class, COLLECTION);
			if(data != null) {
				return true;
			}else {
				return false;
			}
		}catch(Exception e) {
			System.out.println(e);
			return false;
		}
	}


	
//	public StudentModel getByName() {
//		Query q = new Query(Criteria.where("weight").exists(true));
//		return getMongoTemplate().findOne(q, StudentModel.class, COLLECTION);
//	}
	
}
