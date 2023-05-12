package web.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(value = {"contracts"})

public class Employee {
	@Id
	@GeneratedValue
	private Long id;
	@Column(unique = true)
	private String username;
	private String password, address,phoneNumber,createdAt;
	private String name;
	private Long salary;
	
	@OneToMany(mappedBy = "employee",cascade = CascadeType.ALL)
	private List<Contract> contracts;
}
