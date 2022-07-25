package com.api.loginJwt.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.api.loginJwt.security.service.UserDetailsServiceImpl;

public class JwtTokenFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;


	//Metodo para interceptar las peticiones http y separa el token del bearer
	//Necesita HttpServletRequest
	//Retorna un string (token)
	private String getToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if (header != null && header.startsWith("Bearer")) {
			return header.replace("Bearer", "");
		}
		return null;
	}

	//Filtro utilizado en la clase MainSecurity
	//Necesita HttpServletRequest, HttpServletResponse, FilterChain
	//Genera un token de acceso que Spring usará internamente, este luego se guarda en SecurityContext
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String token = getToken(request);
			//Si el token no es nulo y es un token valido
			if (token != null && jwtProvider.validateToken(token)) {
				//Obtiene el nombre del usuario de ese token para luego tener el UserDetails
				String username = jwtProvider.getUserNameFromToken(token);
				UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
				//Creamos el objeto de autenticación
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		filterChain.doFilter(request, response);
	}

}
