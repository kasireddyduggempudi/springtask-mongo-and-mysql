package com.vedantu.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.vedantu.requests.Address;

@Document(collection = "students")
// if this not given, we should give collection name in the methods
// otherwise getClass().getSimpleName() will be the collection name assumes
public class StudentModel {
	
	public StudentModel() {
		super();
	}
	
	//constructor with parameters
	//useful in converting StudentReq to StudentModel
	public StudentModel(String firstName, String lastName, String fullName, Long contactNumber, String email, Address address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.fullName = fullName;
		this.contactNumber = contactNumber;
		this.email = email;
		this.address = address;
	}

	// this contains the properties which the database collection contains as fields
	/*	basically, it gets values from studentReq  (frontend to studentReq and then, 
		studentReq to studentModel using setter methods)
		and then sending to database using studentDao
	*/
	@Id
	private String id;
	// _id in collection will be assigned to this while fetching
	private String firstName;
	private String lastName;
	private String fullName;
	private Long contactNumber;	//unique
	private String email;	//unique
	private Address address;
	//private List<Long> contactNumberHistory;
	
	//setter and getter methods
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Long getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(Long contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
//	public List<Long> getContactNumberHistory() {
//		return contactNumberHistory;
//	}
//	public void setContactNumberHistory(List<Long> contactNumberHistory) {
//		this.contactNumberHistory = contactNumberHistory;
//	}
}
