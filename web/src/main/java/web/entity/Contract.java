package web.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Contract {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String createdDay, expiredTime,position;
	private String description, note;
	private String status;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="employee_id", nullable = false)
	
	private Employee employee;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "department_id", nullable = false)
	private Department department;
	
}
