package app.model;

public enum Roles {
	
	ADMIN("admin"),
	GUEST("guest");
	
	private String role;
	
	Roles(String role) {
		this.role = role;
	}

	public String getRole() {
		return this.role;
	}
}
