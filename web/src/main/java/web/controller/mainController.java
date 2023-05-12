package web.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import web.entity.Admin;
import web.entity.Contract;
import web.entity.Department;
import web.entity.Employee;
import web.entity.RollingUp;
import web.repository.AdminRepo;
import web.repository.ContractRepo;
import web.repository.DepartmentRepo;
import web.repository.EmployeeRepo;
import web.repository.RollingUpRepo;
import web.service.AdminService;
import web.service.ContractService;
import web.service.EmployeeService;
import web.service.RollingUpService;

@RestController
@CrossOrigin(origins = "*")
public class mainController {
	@Autowired
	RollingUpRepo rollingUpRepo;
	
	
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	DepartmentRepo departmentRepo;
	@Autowired
	EmployeeRepo employeeRepo;
	@Autowired 
	AdminRepo adminRepo;
	@Autowired
	ContractRepo contractRepo;
	
	@Autowired
	EmployeeService employeeService;
	
	
	@Autowired
	ContractService contractService;
	
	@Autowired
	AdminService adminService ;
	
	@Autowired
	RollingUpService rollingUpService;
	
	
	@CrossOrigin
	@GetMapping("/getrole")
	public ResponseEntity<Object> temp()
	{
		HashMap<String, Object> response = new HashMap<>();		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<String> roles = authentication.getAuthorities().stream()
		        .map(GrantedAuthority::getAuthority)
		        .collect(Collectors.toList());
		String role= null;
		for(String temp:roles)
		{
			if(temp.startsWith("ROLE_"))
			{
				role=temp;
				break;
			}
		}
		response.put("role",role);		
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@CrossOrigin
	@PostMapping("/employee/newemployee")
	public ResponseEntity<Object> changeEmployee(@RequestBody Employee employee)
	{
		HashMap<String, Object> response = new HashMap<>();		

		if(employeeService.changeEmpInfo(employee))
		{
			response.put("status","ok");
			return new ResponseEntity<>(response, HttpStatus.OK);

		}
		response.put("status","failed");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping("/employee/allemployees") // 123:8080/employee
	public List<Employee> getAllEmployees()
	{
		
		return (List<Employee> )employeeRepo.findAll();
		
	}
	
	@CrossOrigin
	@GetMapping("/employee/allemployees/department/{department}") // 123:8080/employee
	public Set<Employee> getAllEmployeesBelongToDepartment(@PathVariable("department") String department)
	{
		Department department2= departmentRepo.findByName(department);
		if(department2==null)return null;
		return employeeService.employeesBelongToDepartment(department2);
		
	}
	
	
	
	
	
	@CrossOrigin
	@GetMapping("/employee/{id}") // 123:8080/employee/103
	public Employee getEmployeeById(@PathVariable("id") Long id)
	{
		
		return employeeRepo.findById(id).get();	
	}
	@CrossOrigin
	@DeleteMapping("/employee/{id}") 
	public void deleteEmployeeById(@PathVariable("id") Long id)
	{
		employeeRepo.deleteById(id);
	}
	@CrossOrigin
	@GetMapping("/employee/allemployees/{name}")
	public List<Employee> getEmployeesByName(@PathVariable("name") String name)
	{
		
		return employeeRepo.findByNameContainingIgnoreCase(name);
	}
	@CrossOrigin
	@GetMapping("/employee/getrollingupinf/{month}/{year}")
	public ResponseEntity<Object> getRollingUpInfEmp(@PathVariable("month") int month, @PathVariable("year") int year)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		HashMap<String, Object> response = new HashMap<>();		
		Employee employee=employeeRepo.findByUsername(authentication.getName());
		
		
		response.put("alldates", rollingUpService.getRollingUpDaysOfEmpInSpecificMonth(employee.getId(),month, year));
		response.put("name", employee.getName());
		response.put("address", employee.getAddress());
		response.put("phonenumber", employee.getPhoneNumber());
		return new ResponseEntity<>(response, HttpStatus.OK);	
		
	}
	
	@CrossOrigin
	@GetMapping("/admin/getrollingupinf/{id}/{month}/{year}")
	public ResponseEntity<Object> getRollingUpInf(@PathVariable("id") Long id, @PathVariable("month") int month, @PathVariable("year") int year)
	{
		HashMap<String, Object> response = new HashMap<>();		
		Employee employee=employeeRepo.findById(id).get();
		if(employee==null)
		{
			response.put("status","id not existed");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		
		response.put("alldates", rollingUpService.getRollingUpDaysOfEmpInSpecificMonth(id, month, year));
		response.put("name", employee.getName());
		return new ResponseEntity<>(response, HttpStatus.OK);	
		
	}
	
	@CrossOrigin
	@GetMapping("/getid")
	public ResponseEntity<Object> getId()
	{
		HashMap<String, Object> response = new HashMap<>();		

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<String> roles = authentication.getAuthorities().stream()
		        .map(GrantedAuthority::getAuthority)
		        .collect(Collectors.toList());
		String role= null;
		for(String temp:roles)
		{
			if(temp.startsWith("ROLE_"))
			{
				role=temp;
				break;
			}
		}
		if(role.equals("ROLE_ADMIN"))
		{
			Admin admin= adminRepo.findByUsername(authentication.getName());
			response.put("id", admin.getId());		
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		else
		{
			Employee employee=employeeRepo.findByUsername(authentication.getName());
			response.put("id", employee.getId());		
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		
	}
	
	
	@CrossOrigin
	@GetMapping("/admin/alladmins/{name}")
	public List<Admin> getAdminsByName(@PathVariable("name") String name)
	{
		List<Admin>admins=adminRepo.findByNameContainingIgnoringCase(name);
		return admins;	
		
	}
	@CrossOrigin
	@PostMapping("/admin/newadmin/{departmentname}")
	public ResponseEntity<Object> changeAdmin(@RequestBody Admin admin)
	{
		HashMap<String, Object> response = new HashMap<>();		

		if(adminService.changeAminInf(admin))
		{
			response.put("status","ok");
			return new ResponseEntity<>(response, HttpStatus.OK);

		}
		response.put("status","failed");
		return new ResponseEntity<>(response, HttpStatus.OK);

	}
	
	@CrossOrigin
	@PutMapping("/admin/newemployee")
	public ResponseEntity<Object> addEmployee(@RequestBody Employee employee)
	{
		HashMap<String, Object> response = new HashMap<>();		

		if(employeeService.save(employee))
		{
			response.put("status", "ok");		
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		else {
			response.put("status", "failed");		
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
	}

	@CrossOrigin
	@GetMapping("/admin/alladmins")
	public List<Admin> getAdmins()
	{
		return  adminRepo.findAll();	
	}
	@CrossOrigin
	@GetMapping("/admin/{id}")
	public Admin getAdminById(@PathVariable("id") Long id )
	{
		return adminRepo.findById(id).get();
	}
	
	@CrossOrigin
	@PutMapping("/admin/newadmin/{departmentname}")
	ResponseEntity<Object> addAdmin(@RequestBody Admin admin, @PathVariable("departmentname") String departmentName)
	{
		HashMap<String, Object> response = new HashMap<>();	
		if(adminService.save(admin, departmentName))
		{
			response.put("status", "ok");		
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		else 
		{
			response.put("status", "failed");	
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		
		
		
	}
	
	@CrossOrigin
	@DeleteMapping("/admin/{id}")
	public void deleteAdminById(@PathVariable("id") Long id )
	{
		adminRepo.deleteById(id);		
	}
	
	@CrossOrigin
	@PostMapping("/employee/rollingup")
	public ResponseEntity<Object> rollingUp()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String name=authentication.getName();
		LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        
		Employee employee=employeeRepo.findByUsername(name);	
		RollingUp rollingUp=rollingUpRepo.findByDate(formattedDate);
		
		HashMap<String, Object> response = new HashMap<>();	
		
		if(rollingUp==null)
		{
			rollingUp= new RollingUp();
			rollingUp.setDate(formattedDate);
			rollingUp.setEmployees(new ArrayList<Employee>());		
			
			rollingUp.getEmployees().add(employee);	
			
			response.put("status", "ok");
			rollingUpRepo.save(rollingUp);
			return new ResponseEntity<>(response,HttpStatus.OK);	
		}
		else 
		{
			for(Employee temp:rollingUp.getEmployees())
			{
				if(temp.getUsername().equals(name))
					{
						response.put("status", "rolledUp");
						return new ResponseEntity<>(response,HttpStatus.OK);						
					}
			}
			rollingUp.getEmployees().add(employee);
			
			rollingUpRepo.save(rollingUp);
			
			response.put("status", "ok");
			return new ResponseEntity<>(response,HttpStatus.OK);	
			
		}
	}
		
		@CrossOrigin
		@PutMapping("/admin/newcontract/{departmentname}/{time}/{idemp}")
		public ResponseEntity<Object> addContract(@RequestBody Contract contract, @PathVariable("departmentname") String departmentname,@PathVariable("time") Long time,@PathVariable("idemp")Long id)
		{

			HashMap<String, Object> response = new HashMap<>();	
			Employee employee=employeeRepo.findById(id).get();			
			if(employee==null)
			{
				response.put("status", "failed");	
				return new ResponseEntity<>(response,HttpStatus.OK);
			}
			
			if(contractService.save(employee,departmentname, time, contract))
			{
				
				response.put("status", "ok");		
				return new ResponseEntity<>(response,HttpStatus.OK);	
			
			}
			
			else
			{
				
				response.put("status", "failed");	
				return new ResponseEntity<>(response,HttpStatus.OK);	
			}
		
		}
		
		@CrossOrigin
		@PutMapping("/admin/resetpass/employee/{id}")
		public ResponseEntity<Object> resetEmployeePass(@PathVariable("id") Long id)
		{
			HashMap<String, Object> response = new HashMap<>();	
			if(adminService.employeeResetPassword(id))
				{
					response.put("status", "ok");	
					return new ResponseEntity<>(response,HttpStatus.OK);
				}
			
			
			response.put("status", "failed");	
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		@CrossOrigin
		@PutMapping("/admin/resetpass/admin/{id}")
		public ResponseEntity<Object> resetAdminPass(@PathVariable("id") Long id)
		{
			HashMap<String, Object> response = new HashMap<>();	
			if(adminService.adminResetPassword(id))
				{
					response.put("status", "ok");	
					return new ResponseEntity<>(response,HttpStatus.OK);
				}
			
			response.put("status", "failed");	
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		
		
		
		
		
		@CrossOrigin
		@GetMapping("/department/alldepartments")
		List<Department> getDepartments()
		{
			return departmentRepo.findAll();
		}
		
		@CrossOrigin
		@GetMapping("/employee/employeesbelongingdepartment/{departmentname}")
		List<Employee> allEmployeesBelongingToDepartment(@PathVariable("departmentname") String departmentName)
		{
			return contractService.getAllEmployeesBelongingToDepartment(departmentName);		
					
		}
		
		
		@CrossOrigin
		@GetMapping("/contract/allcontracts")
		List<Contract> getContracts()
		{
			return contractRepo.findAll();
		}
		@CrossOrigin
		@GetMapping("/contract/allcontracts/{name}")
		List<Contract> findContractsByEmployeeNameIgnoreCase(@PathVariable("name") String name)
		{
			
			return contractService.allContractsBelongingToEmployeeNameIgnoreCase(name);
			
			
		}
		@CrossOrigin
		@GetMapping("/department/alldepartments/{name}")
		
		List<Department> findDepartmentsByName(@PathVariable("name")String name)
		{
			return departmentRepo.findByNameContainingIgnoreCase(name);
		}
			
		@CrossOrigin
		@GetMapping("/countall")
		public ResponseEntity<Object> countAll()
		{
			List<Employee>employees=employeeRepo.findAll();
			List<Department>department=departmentRepo.findAll();
			List<Contract>contracts=contractRepo.findAll();
			List<Admin>admins=adminRepo.findAll();
			HashMap<String, Object> response = new HashMap<>();
			response.put("employees", employees.size());
			response.put("admins", admins.size());
			response.put("contracts", contracts.size());
			response.put("departments", department.size());	
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		

}

