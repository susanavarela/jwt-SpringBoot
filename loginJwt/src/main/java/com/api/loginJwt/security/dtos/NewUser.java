package com.api.loginJwt.security.dtos;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

//Clase tipo models, con algunas validaciones necesarias para que no se envien datos vacios o con un formato especifico, es utilizada en el controller
public class NewUser {

	@Email
	private String email;

	@NotBlank
	private String password;

	@NotBlank
	private String username;

	private Set<String> roles = new HashSet<>();

	public NewUser(@Email String email, @NotBlank String password, @NotBlank String username, Set<String> roles) {
		super();
		this.email = email;
		this.password = password;
		this.username = username;
		this.roles = roles;
	}

	public NewUser() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

}
