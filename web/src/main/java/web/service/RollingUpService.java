package web.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.entity.Employee;
import web.entity.RollingUp;
import web.repository.EmployeeRepo;
import web.repository.RollingUpRepo;

@Service
public class RollingUpService {

	@Autowired
	RollingUpRepo rollingUpRepo;
	@Autowired
	EmployeeRepo employeeRepo;
	public List<RollingUp> getRollingUpDaysOfEmpInSpecificMonth(Long id,int month, int year)
	{
		Employee employee=employeeRepo.findById(id).get();
		if(employee==null)return null;
		
		List<RollingUp> rollingUps=rollingUpRepo.findAll();// rlup chua nhieu emp
		
		ArrayList<RollingUp> canUse=new ArrayList<>();
		
		for(RollingUp temp: rollingUps)
		{
			if(temp.retLocalDate().getMonthValue()==month && temp.retLocalDate().getYear()== year) 			{
				
				canUse.add(temp);				
			}
		
		}
		ArrayList<RollingUp> retVal=new ArrayList<>();
		for(RollingUp temp: canUse)
		{
			
			for(Employee employeeTemp:temp.getEmployees()) 
			{
				if(employeeTemp.getId()==id) 
				{
					retVal.add(temp);
					break;
				}
				
			}
			
			
		}
		return retVal;

		
		
		
		
		
		
		
		
		
	}
	
	
}
