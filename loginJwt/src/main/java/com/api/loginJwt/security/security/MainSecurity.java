package com.api.loginJwt.security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.api.loginJwt.security.jwt.JwtEntryPoint;
import com.api.loginJwt.security.jwt.JwtTokenFilter;

//Clase de configuracion de seguridad en base a http
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MainSecurity {

	@Autowired
	private JwtEntryPoint jwtEntryPoint;

	@Bean
	public JwtTokenFilter jwtTokenFilter() {
		return new JwtTokenFilter();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*Se configura los metodos de seguridad y las autenticaciones de cada usuario
	  Restringe los accesos en funcion a la solicitud de la url, los usuarios con autenticacion tienen acceso a cualquiera, manejo de excepciones, 
	  establece punto de autenticacion(enviando jwtEntryPoint del paquete jwt)
	  configura la gestion de sesiones, una unica sesion a la vez, politica en la creacion de sesion.
 	  añadimos un filtro antes de la clase, por ultimo que el http que se construye sea creado una vez
 	*/
	//Necesita HttpSecurity
	//Retorna el http con los filtros configurados  
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
		.authorizeHttpRequests().antMatchers("/security/**")
		.permitAll().anyRequest().authenticated().and().exceptionHandling()
		.authenticationEntryPoint(jwtEntryPoint).and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

}
