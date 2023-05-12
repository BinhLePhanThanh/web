package web.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
public class Department {
	
	
	
	
	@Id
	@GeneratedValue
	private Long id;
	@Column(unique = true)
	private String name;
	
	
	@OneToOne(mappedBy = "department", cascade = CascadeType.ALL)
	Admin admin;
	
	@OneToMany(mappedBy = "department")
	private List<Contract>contracts;
	
	
	

}
