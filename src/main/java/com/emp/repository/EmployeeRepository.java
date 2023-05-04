package com.emp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emp.bean.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	
	

}
