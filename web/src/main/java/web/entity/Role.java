package web.entity;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;
import static web.entity.Permission.*;

public enum Role {
	ADMIN(Sets.newHashSet(CONTRACT_READ,CONTRACT_WRITE,EMPLOYEE_READ,EMPLOYEE_WRITE,ROLLINGUP_WRITE))
	, EMPLOYEE(Sets.newHashSet(Permission.CONTRACT_READ,Permission.EMPLOYEE_READ,Permission.ROLLINGUP_READ));
	private final Set<Permission> permissions;
	private Role(Set<Permission>permissions) {
		
	
		this.permissions=permissions;
	}
	public Set<SimpleGrantedAuthority> getGrantedAuthorities()
	{
		Set<SimpleGrantedAuthority> permissions=this.permissions.stream().map(permission -> new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toSet());
		permissions.add(new SimpleGrantedAuthority("ROLE_"+ this.name()));
		return permissions;
	}
}
