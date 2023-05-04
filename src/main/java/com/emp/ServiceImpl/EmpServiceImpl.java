package com.emp.ServiceImpl;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp.Service.EmpService;
import com.emp.bean.Employee;
import com.emp.repository.EmployeeRepository;

@Service
public class EmpServiceImpl implements EmpService {

	@Autowired
	private EmployeeRepository empRepository;

	@Override
	public Employee saveEmployee(Employee emp) {

		return empRepository.save(emp);
	}

}
