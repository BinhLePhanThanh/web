package web.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import web.entity.Admin;
import web.entity.Department;
import web.entity.Employee;
import web.repository.AdminRepo;
import web.repository.DepartmentRepo;
import web.repository.EmployeeRepo;

@Service
public class AdminService {
	@Autowired
	AdminRepo adminRepo;
	@Autowired
	DepartmentRepo departmentRepo;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	EmployeeRepo employeeRepo;
	
	
	
	public boolean changeAminInf(Admin admin)
	{
		if(admin.getId()==null)return false;
		Admin temp =adminRepo.findById(admin.getId()).get();
		if(temp==null)return false;
		
				temp.setAddress(admin.getAddress());
		temp.setPhoneNumber(admin.getPhoneNumber());
		temp.setName(admin.getName());
		temp.setStatus(temp.getStatus());
		adminRepo.save(temp);
		return true;
	
	}
	
	
	public boolean save(Admin admin, String department)
	{
		if(adminRepo.findByUsername(admin.getUsername())!=null|| departmentRepo.findByName(department)!=null|| employeeRepo.findByUsername(admin.getUsername())!=null)
		{
			return false;
		}
		else 
		{
			Department	tempDepartment=new Department();
			LocalDate currentDate = LocalDate.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        
	        String formattedDate = currentDate.format(formatter);
			
			
			
			tempDepartment.setName(department);
			
			
			admin.setDepartment(tempDepartment);
			admin.setPassword(passwordEncoder.encode(admin.getPassword()));
			
			admin.setCreatedDate(formattedDate);			
			
			adminRepo.save(admin);
			
			return true;			
			
		}
		
	}
	
	public boolean adminResetPassword(Long id)
	{
		Admin admin=adminRepo.findById(id).get();
		if(admin==null)
		{
			return false;
		}
		
		admin.setPassword(passwordEncoder.encode("123"));		
		adminRepo.save(admin);
		return true;
		
	}
	public boolean employeeResetPassword(Long id)
	{
		Employee employee=employeeRepo.findById(id).get();
		if(employee==null)return false;
		
		employee.setPassword(passwordEncoder.encode("123"));
		employeeRepo.save(employee);
		return true;
		
	}

}
