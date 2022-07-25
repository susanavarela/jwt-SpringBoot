package com.api.loginJwt.security.jwt;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {
	
	/*Es un metodo de la clase que implementa
	  controla el error que devuelve cuando un usuario sin permisos intenta realizar una accion
	*/
	//Necesita HttpServletRequest, HttpServletResponse y AuthenticationException
	//Retorna una respuesta de error al cliente utilizando el código de estado especificado,
    // por defecto crea la respuesta para que parezca una página de error del servidor con formato HTML que contiene el mensaje especificado 
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)throws IOException{
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No autorizado");

	}
}
