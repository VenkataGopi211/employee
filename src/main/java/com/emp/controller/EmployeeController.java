package com.emp.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emp.Service.EmpService;
import com.emp.bean.Employee;
import com.emp.repository.EmployeeRepository;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class EmployeeController {
	@Autowired
	private EmployeeRepository empRepository;
	@Autowired
	private EmpService empService;

	@PostMapping("/save")
	public ResponseEntity<Employee> saveEmployeeDetails(@RequestBody Employee emp, HttpServletRequest request)
			throws IOException {

		// call the business Logic
		emp = empRepository.save(emp);
		if (emp.getEmployeeId() > 0) {
			return new ResponseEntity<Employee>(emp, HttpStatus.OK);
		} else {
			return new ResponseEntity<Employee>(emp, HttpStatus.PRECONDITION_FAILED);
		}
	}

	@GetMapping("/employees/tax")
	public ResponseEntity<?> getTaxDeduction() {
		List<Employee> employees = empRepository.findAll();
		List<Map<String, Object>> response = new ArrayList<>();
		LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), 4, 1);
		LocalDate endDate = LocalDate.of(LocalDate.now().getYear() + 1, 3, 31);

		for (Employee employee : employees) {
			// check if the employee joined after April 1st
			LocalDate doj = employee.getDOJ();
			if (doj.isAfter(startDate) || doj.isEqual(startDate)) {
				int numMonths = Period.between(doj.withDayOfMonth(1), endDate.withDayOfMonth(1)).getMonths();
				double totalSalary = employee.getSalary() * numMonths;
				double tax = calculateTax(employee.getSalary()) * numMonths;
				double cess = (totalSalary > 2500000) ? 0.02 * (totalSalary - 2500000) : 0;
				double totalTaxDeduction = tax + cess;
				Map<String, Object> employeeTax = new HashMap<>();
				employeeTax.put("employee_code", employee.getEmployeeId());
				employeeTax.put("first_name", employee.getFirstName());
				employeeTax.put("last_name", employee.getLastName());
				employeeTax.put("yearly_salary", totalSalary);
				employeeTax.put("tax_amount", tax);
				employeeTax.put("cess_amount", cess);
				response.add(employeeTax);
			}
		}
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		

	

	@SuppressWarnings("unused")
	private double calculateTax(double Salary) {
		double tax = 0;

		if (Salary > 1000000) {
			tax += 0.2 * (Salary - 1000000);
			Salary = 1000000;
		}
		if (Salary > 500000) {
			tax += 0.1 * (Salary - 500000);
			Salary = 500000;
		}
		if (Salary > 250000) {
			tax += 0.05 * (Salary - 250000);
		}

		return tax;
	}
}
