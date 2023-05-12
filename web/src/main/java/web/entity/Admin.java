package web.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@JsonIgnoreProperties(value = {"department"})
public class Admin  {
	
	@Id
	@GeneratedValue
	private Long id;
	@Column(unique = true)
	private String username;
	private String password;
	private String name, address, phoneNumber,status, createdDate;
	
	@OneToOne(cascade = CascadeType.ALL)
	
	private Department department;

}
