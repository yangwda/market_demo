package cn.yj.market.frame.constants;

public enum SysRoleEnum {
	SALER("saler","销售员"),
	ADMIN("admin","管理员"),
	GUEST("guest","访客"),
	;
	
	private String role ;
	private String roleName ;
	private SysRoleEnum(String role ,String roleName){
		this.role = role ;
		this.roleName = roleName ;
	}
	public final String getRole() {
		return role;
	}
	public final String getRoleName() {
		return roleName;
	}
	
}
