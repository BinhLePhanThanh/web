package web.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(value = {"employees"})
public class RollingUp {	
	@Id
	@GeneratedValue
	private Long id;
	
	
	private String date;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Employee> employees;
	
	public LocalDate retLocalDate()
	{
		return LocalDate.parse(date);
	}
	

}
