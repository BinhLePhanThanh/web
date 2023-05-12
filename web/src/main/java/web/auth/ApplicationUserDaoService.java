package web.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;

import web.entity.Admin;
import web.entity.Employee;
import web.entity.Role;
import web.repository.AdminRepo;
import web.repository.EmployeeRepo;

@Repository("test")
public class ApplicationUserDaoService implements ApplicationUserDao {

	@Autowired
	private EmployeeRepo employeeRepo;
	@Autowired
	private AdminRepo adminRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Override
	public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
		// TODO Auto-generated method stub
		
		
		
		return getApplicationUsers().stream()
				.filter(applicationUser -> username.equals(applicationUser.getUsername()))
				.findFirst();
	}
	private List<ApplicationUser> getApplicationUsers()
	{
		List<ApplicationUser> applicationUsers=Lists.newArrayList();
		List<Employee> employees=employeeRepo.findAll();
		List<Admin>admins=adminRepo.findAll();
				
		for(Employee employee:employees)
		{
			applicationUsers.add(new ApplicationUser(Role.EMPLOYEE.getGrantedAuthorities(), employee.getPassword(), 
					employee.getUsername(), true, true, true, true));
		}
		for(Admin admin: admins)
		{ 	
			applicationUsers.add(new ApplicationUser(Role.ADMIN.getGrantedAuthorities(), admin.getPassword(), 
					admin.getUsername(), true, true, true, true));
		}
		
		return applicationUsers;
	}

}
