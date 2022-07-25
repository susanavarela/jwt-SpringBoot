package com.api.loginJwt.security.dtos;

import javax.validation.constraints.NotBlank;

//Clase tipo models, con algunas validaciones necesarias para que no se envien datos vacios, es utilizada en el controller
public class LoginUser {

	@NotBlank
	private String username;

	@NotBlank
	private String password;

	public LoginUser(@NotBlank String username, @NotBlank String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public LoginUser() {
		super();
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
