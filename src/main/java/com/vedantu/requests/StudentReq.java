package com.vedantu.requests;


public class StudentReq {
	
	// default constructor must be there to get the object from json @RequestBody
	// this is for mongo collection students
	
	public StudentReq() {
		super();
	}
	
	// or the above can be omitted since no constructor with arguments is there. default will be there
	
	
	// this is to store data from user
	// it contains the properties realted to fields which we take from the user
	
	private String firstName;
	private String lastName;
	private Long contactNumber;	//unique
	private String email;	//unique
	// Address objects fields
	private String city;
	private String state;
	private String country;
	private Integer pincode;
	
	// setter and getter methods
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
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Integer getPincode() {
		return pincode;
	}
	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}
	
	
}
