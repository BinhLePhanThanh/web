package web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import web.entity.Department;

public interface DepartmentRepo extends JpaRepository<Department, Long>{

	Department findByName(String name);
	List<Department>  findByNameContainingIgnoreCase(String name);
}
