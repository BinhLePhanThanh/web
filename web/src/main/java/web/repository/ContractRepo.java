package web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import web.entity.Contract;
import web.entity.Department;

public interface ContractRepo extends JpaRepository<Contract, Long> {

	
	List<Contract> findByDepartment(Department department);
}
