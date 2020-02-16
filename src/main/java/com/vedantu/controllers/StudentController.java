package com.vedantu.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vedantu.managers.StudentManager;
import com.vedantu.models.StudentModel;
import com.vedantu.models.StudentTransactionEntity;
import com.vedantu.requests.StudentRechargeRequest;
import com.vedantu.requests.StudentReq;

@Controller
@RequestMapping("/student")
public class StudentController {

	/*
	 * @Autowired private StudentDao studentDao;
	 */

	@Autowired
	private StudentManager stuManager;

	/* addStudent */
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public boolean addStudent(@RequestBody StudentReq stu) throws Exception {
		// stuManager checks validation and duplicates and inserts
		// it throws error if duplicates. thats why try-catch
		try {
			return stuManager.add(stu);
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	/* getStudentById */
	@RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public StudentModel getStudentById(@PathVariable("id") String id) throws Exception {
		// studentDao is instance of StudentDao bean
		return stuManager.getById(id);
	}

	/* getStudentByEmail */
	@RequestMapping(value = "/getByEmail/{email}", method = RequestMethod.GET)
	@ResponseBody
	public StudentModel getStudentByEmail(@PathVariable("email") String email) {
		return stuManager.getByEmail(email);
	}

	/* getStudentList */
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	@ResponseBody
	public List<StudentModel> getList() {
		return stuManager.getList();
	}

	/* updateStudentByEmail */
	 @RequestMapping(value = "/updateByEmail/{email}", method =
	 RequestMethod.POST)
	 
	 @ResponseBody 
	 public StudentModel updateByEmail(@PathVariable("email")  String email, @RequestBody StudentReq stu) throws Exception { // getting StudentModel from
		 try {
			 System.out.println(stu);
			 return stuManager.updateByEmail(email, stu);
		 }catch(Exception e) {
			 System.out.println(e);
			 return null;
		 }
		 
	 }

	/* deleteById */
	@RequestMapping(value = "/deleteById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public StudentModel deleteStudentById(@PathVariable("id") String id) {
		return stuManager.deleteById(id);
	}
	
	// recharge Controllers
	
	/* recharge */
	@RequestMapping(value="/recharge", method=RequestMethod.POST)
	@ResponseBody
	public boolean recharge(@RequestBody StudentRechargeRequest stuRechargeReq){
		try {
			return stuManager.recharge(stuRechargeReq);
		}catch(Exception e) {
			System.out.println(e);
			return false;
		}
		
	}
	
	/* deduction */
	@RequestMapping(value = "/deduction",  method=RequestMethod.POST)
	@ResponseBody
	public boolean deduction(@RequestBody StudentRechargeRequest stuRechargeReq) {
		try {
			return stuManager.deduction(stuRechargeReq);
		}catch(Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	/* getTotalAmountPaidById */
	@RequestMapping(value = "/getAvailableAmountById/{id}", method=RequestMethod.GET )
	@ResponseBody
	public int getAvailableAmountById(@PathVariable("id") String id) {
		try {
			return stuManager.getAvailableAmountById(id);
		}catch(Exception e) {
			System.out.println(e);
			return 0;
		}
		
	}
	
	/* rechargeHistoryById */
	@RequestMapping(value = "/getRechargeHistory/{id}", method=RequestMethod.GET)
	@ResponseBody
	public List<StudentTransactionEntity> getRechargeHistoryById(@PathVariable("id") String id) {
		try {
			return stuManager.getRechargeHistoryById(id);
		}catch(Exception e) {
			System.out.println(e);
			return null;
		}
	}
}
