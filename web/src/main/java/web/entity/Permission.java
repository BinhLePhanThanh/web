package web.entity;

public enum Permission {
	ROLLINGUP_WRITE("writeRollingUp"),
	ROLLINGUP_READ("readRollingUp"),
	CONTRACT_WRITE("writeContract"),
	CONTRACT_READ("readContract"),
	EMPLOYEE_WRITE("writeEmployee"),
	EMPLOYEE_READ("readEmployee");
	private final String permission;
	private Permission(String permission) {
		// TODO Auto-generated constructor stub
		this.permission=permission;
	}
	public String getPermission() {
		return permission;
	}
	
	
	
	
}
