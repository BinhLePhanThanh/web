package web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import web.entity.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Long>{

	Employee findByUsername(String username);
	List<Employee> findByName(String name);
	List<Employee> findByNameContainingIgnoreCase(String name);
}
