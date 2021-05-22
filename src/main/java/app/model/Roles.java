package app.model;

import java.util.HashSet;
import java.util.Set;

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
	
	public static Set<String> getAllRolesAsString() {
		Set<String> rolesAsString = new HashSet<>();
		for(Roles role : Roles.values()) {
			rolesAsString.add(role.getRole());
		}
		return rolesAsString;
	}
}
