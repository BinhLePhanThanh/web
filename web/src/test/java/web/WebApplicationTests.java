package web;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.google.common.collect.Lists;

import web.auth.ApplicationUser;
import web.entity.Admin;
import web.entity.Contract;
import web.entity.Department;
import web.entity.Employee;
import web.entity.Role;
import web.repository.AdminRepo;
import web.repository.DepartmentRepo;
import web.repository.EmployeeRepo;
import web.service.AdminService;

@SpringBootTest
class WebApplicationTests {

	
	@Autowired
	AdminRepo adminRepo;
	@Autowired
	DepartmentRepo departmentRepo;
	@Autowired
	EmployeeRepo employeeRepo;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	AdminService adminService;
	
	
	
	//@Test
	void dm()
	{
		
		Admin admin= Admin.builder()
				.name("binh")
				.address("VN")
				.phoneNumber("01233214523").status("active").id((long)1)
				.build();
		adminService.changeAminInf(admin);
		
	}
	
	

	
	//@Test
	void insertDepartment()
	{
		Department d1=Department.builder().name("CS").build();
		Department d2=Department.builder().name("CS2").build();
		Department d3=Department.builder().name("CS3").build();
		Department d4=Department.builder().name("IT1").build();
		Department d5=Department.builder().name("IT2").build();
		Department d6=Department.builder().name("IT3").build();
		
				
		departmentRepo.saveAll( List.of(d1,d2,d3,d4,d5,d6));

	}
	
	@Test
	void contextLoads() {
		
		Department department=new Department();
		department.setName("CSSSSS");
		
		Admin admin= Admin.builder()
				.address("HN").name("admin2")
				.phoneNumber("01233214523").status("active")
				.username("admin").password(passwordEncoder.encode("123")).build();
		
		admin.setDepartment(department);
		adminRepo.save(admin);
		
	}
	//@Test
		void insertEmployees()
		{
			Employee e1=Employee.builder().name("nv1").phoneNumber("0123124").address("HN").createdAt(LocalDate.now().toString()).username("1").password(passwordEncoder.encode("123")).build();
			Employee e2=Employee.builder().name("nv2").phoneNumber("0123124312").address("HN").createdAt(LocalDate.now().toString()).username("2").password(passwordEncoder.encode("123")).build();
			Employee e3=Employee.builder().name("nv3").phoneNumber("012123124").address("HN").createdAt(LocalDate.now().toString()).username("3").password(passwordEncoder.encode("123")).build();
			Employee e4=Employee.builder().name("nv4").phoneNumber("0123112124").address("HN").createdAt(LocalDate.now().toString()).username("4").password(passwordEncoder.encode("123")).build();
			Employee e5=Employee.builder().name("nv5").phoneNumber("0143223124").address("HN").createdAt(LocalDate.now().toString()).username("5").password(passwordEncoder.encode("123")).build();
			//employeeRepo.save(e5);		
			employeeRepo.saveAll(List.of(e1));
		}
		//@Test
		void testt()
		{
			List<ApplicationUser> applicationUsers=Lists.newArrayList();
			List<Employee> employees=employeeRepo.findAll();
			List<Admin>admins=adminRepo.findAll();
			for(Employee employee:employees)
			{
				applicationUsers.add(new ApplicationUser(Role.EMPLOYEE.getGrantedAuthorities(), employee.getPassword(), 
						employee.getUsername(), true, true, true, true));
			}
			System.out.println(applicationUsers.get(0).getUsername());
		}
		//@Test
		void addContract()
		{
			Contract contract = new Contract();
			contract.setEmployee(employeeRepo.findById((long) 1).get());
			contract.setDepartment(departmentRepo.getById((long)8));
			
		}

}
