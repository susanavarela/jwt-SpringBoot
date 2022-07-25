package com.api.loginJwt.security.dtos;

//Clase tipo models, es utilizada en el controller
public class JwtDTO {

	private String token;

	public JwtDTO(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
