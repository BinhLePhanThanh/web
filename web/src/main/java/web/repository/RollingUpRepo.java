package web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import web.entity.RollingUp;

public interface RollingUpRepo extends JpaRepository<RollingUp, Long> {
	
	RollingUp findByDate(String date);

}
