package web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import web.entity.Admin;

public interface AdminRepo extends JpaRepository<Admin, Long> {

	 Admin findByUsername(String username);

	 List<Admin> findByName(String name);
	 List<Admin> findByNameContainingIgnoringCase(String name);
}
