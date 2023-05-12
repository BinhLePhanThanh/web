package web.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.persistence.Temporal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import web.entity.Contract;
import web.entity.Department;
import web.entity.Employee;
import web.repository.AdminRepo;
import web.repository.ContractRepo;
import web.repository.DepartmentRepo;
import web.repository.EmployeeRepo;
import org.apache.naming.java.javaURLContextFactory;
@Service
public class EmployeeService {

	@Autowired
	EmployeeRepo employeeRepo;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	DepartmentRepo departmentRepo;
	@Autowired
	ContractRepo contractRepo;
	@Autowired
	AdminRepo adminRepo;
	
	public Set<Employee> employeesBelongToDepartment(Department department)
	{
		List<Contract> contracts=contractRepo.findAll();
		
		Set<Employee>employees=new HashSet<Employee>();
		for(Contract contract:contracts) 
		{
			
			if(contract.getDepartment().getName().equals(department.getName()))
			{
				employees.add(contract.getEmployee());				
				
			}
		}
		
		return employees;
	}
	
	
	
	public boolean changeEmpInfo(Employee employee)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		
		
		Employee temp=employeeRepo.findByUsername(username);
		
		
		
		temp.setAddress(employee.getAddress());
		temp.setName(employee.getName());
		temp.setPhoneNumber(employee.getPhoneNumber());			
		employeeRepo.save(temp);
		return true;
		
		
	}
	
	
	
	public boolean save(Employee employee)
	{
		if(employeeRepo.findByUsername(employee.getName())!=null||  adminRepo.findByUsername(employee.getUsername())!=null)
		{
			return false;
		}
		else 
		{
			LocalDate currentDate = LocalDate.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        String formattedDate = currentDate.format(formatter);
	        
	        employee.setCreatedAt(formattedDate);
	        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
			employeeRepo.save(employee);
			return true;
			
			
		}
	}
	
}
