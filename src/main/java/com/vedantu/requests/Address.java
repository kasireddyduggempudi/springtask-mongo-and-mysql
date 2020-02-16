package com.vedantu.requests;

public class Address {
	
	private String city;
	private String state;
	private String country;
	private Integer pincode;
	
	public Address() {
		super();
	}
	
	public Address(String city,String state, String country, Integer pincode) {
		this.city = city;
		this.state = state;
		this.country = country;
		this.pincode = pincode;
	}
	
	//setter and getter methods
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
