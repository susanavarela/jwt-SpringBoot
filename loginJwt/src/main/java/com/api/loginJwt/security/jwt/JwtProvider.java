package com.api.loginJwt.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.api.loginJwt.security.entities.MainUser;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtProvider {
	//Logger para mostrar los errores
	private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

	
	//Clave para verificar el token
	@Value("${jwt.secret}")
	private String secret;
	
	//Tiempo base de expiracion
	@Value("${jwt.expiration}")
	private int expiration;

	
	/*Genera el token 
	  la configuracion se hace en base al tiempo actual, y se le agrega el tiempo de expiracion,
	  se establece el tipo de algoritmo y el tipo de token. Por ultimo, crea el JWT y lo serializa en una cadena compacta segura para URL 
	*/
	//Necesita Authentication 
	//Retorna un tipo string (token)
	public String generateToken(Authentication authentication) {
	MainUser mainUser = (MainUser) authentication.getPrincipal();
	return Jwts.builder().setSubject(mainUser.getUsername())
			.setIssuedAt(new Date())
			.setExpiration(new Date(new Date().getTime() + expiration * 1000))
			.signWith(SignatureAlgorithm.HS512, secret)
			.compact();
	}
	
	
	//Permite obtener el nombre de usuario con el token
	//Necesita un string (token)
	//Retorna un string (username)
	public String getUserNameFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	}
	
	
	/*valida el token con la firma secreta
	  evalua diferentes errores que se pueden producir, utiliza logger
	*/
	//Necesita un string (token)
	//Retorna un boolean (true) en caso de que la respuesta sea correcta, en caso de que suceda una excepcion envia diferentes mensejes especificando el error,
	//caso contrario boolean (false). 
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		}catch(MalformedJwtException e) {
			logger.error("token mal formado");
		}catch(UnsupportedJwtException e) {
			logger.error("token no soportado");
		}catch(ExpiredJwtException e) {
			logger.error("token expirado");
		}catch(IllegalArgumentException e) {
			logger.error("token vacio");
		}catch(SignatureException e) {
			logger.error("fail en la firma");
		}
		return false;
	}
	
}
