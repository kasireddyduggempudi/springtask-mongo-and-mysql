package com.vedantu.daos;

import java.util.List;

import com.vedantu.models.StudentModel;
import com.vedantu.models.StudentAccountEntity;

public interface AbstractStudentDao {	
	
	public boolean add(StudentModel stu);
	
	public StudentModel getById(String id);
	
	public StudentModel getByContactNumber(Long contactNumber); // useful in finding duplicate phn
	
	public StudentModel getByEmail(String email);// useful in finding duplicate before new user adding

	public List<StudentModel> getList();
	
	/* public boolean update(StudentModel newDoc); */
	
	public StudentModel updateByEmail(String email, StudentModel newDoc);
	
	public StudentModel deleteById(String id);
	
	public boolean isIdExists(String id);	// useful in recharging with id
	


	

	
}
