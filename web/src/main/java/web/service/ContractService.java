package web.service;

import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.entity.Contract;
import web.entity.Department;
import web.entity.Employee;
import web.repository.ContractRepo;
import web.repository.DepartmentRepo;
import web.repository.EmployeeRepo;
@Service
public class ContractService {

	@Autowired
	EmployeeRepo employeeRepo;
	@Autowired
	ContractRepo contractRepo;
	@Autowired
	DepartmentRepo departmentRepo;
	
	public boolean save(Employee employee, String department, Long time,Contract contract )
	{
		Employee inDbEmployee=employeeRepo.findById(employee.getId()).get();
		
		Department indbDepartment=departmentRepo.findByName(department);
		
		
		
		if(inDbEmployee==null)return false; 
		if(isAvailableForWork(inDbEmployee) && indbDepartment != null)
		{
			LocalDate currentDate = LocalDate.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        
	        String formattedDate = currentDate.format(formatter);
	        
			contract.setCreatedDay(formattedDate);
			
			LocalDate expiredDate =currentDate.plusMonths(time);
			DateTimeFormatter expiredFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        
	        String expiredFormattedDate = expiredDate.format(formatter);
			
			contract.setExpiredTime(expiredFormattedDate);       
			contract.setDepartment(indbDepartment);
			contract.setEmployee(inDbEmployee);
			contractRepo.save(contract);		
		}
		
		
		
		
		return true;
	}
	
	public boolean isAvailableForWork(Employee employee)
	{
		LocalDate currentDate = LocalDate.now();
		Employee inDbEmployee=employeeRepo.findById(employee.getId()).get();
		if(inDbEmployee==null)return false; 
		List<Contract> contracts=inDbEmployee.getContracts();
		for(Contract temp:contracts)
		{
			LocalDate date = LocalDate.parse(temp.getExpiredTime());
			if(currentDate.isBefore(date)||currentDate.isEqual(date))
			{
				return false;
			}
		}
		return true;
	}
	
	
	public List<Employee> getAllEmployeesBelongingToDepartment(String departmentName)
	{
		Department department=departmentRepo.findByName(departmentName);
		if(department==null)
		{
			return null;
		}
		
		
		List<Contract> contracts = contractRepo.findByDepartment(department);
		List<Employee> employees=new ArrayList<>();
		
		for(Contract contract: contracts)
		{
			LocalDate currentDate = LocalDate.now();
	        LocalDate expiredDate=LocalDate.parse(contract.getExpiredTime());
	        if(currentDate.isBefore(expiredDate)||currentDate.isEqual(expiredDate))
	        {
	        		employees.add(contract.getEmployee());
	        		
	        }
	        
	        
		}
		return employees;		
			
	}
	
	public List<Contract> allContractsBelongingToEmployeeNameIgnoreCase(String name)
	{
		List<Employee> employees= employeeRepo.findByNameContainingIgnoreCase(name);
		List<Contract> contracts=new ArrayList<>();
		for(Employee employee:employees) 
		{
			for(Contract contract:employee.getContracts())
			{
				contracts.add(contract);
			}
		}
		return contracts;		
		
	}
	
}
