package com.bitsnbytes.product.dto;

public class UserDTO {

	private String username;
	private String password;
	private String role;
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	private UserDTO(String username, String password, String role) {
		this.username = username;
		this.password = password;
		this.role=role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
