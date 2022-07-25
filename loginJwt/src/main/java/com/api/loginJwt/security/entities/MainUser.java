package com.api.loginJwt.security.entities;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//Esta clase la va a usar spring security y el JWT para identificar la autoridad a travez del rol. 
//Es una clase de seguridad para separar los datos que solo necesita de los usuarios, discriminando aquellos datos que guardan, necesarios pero para el manejo de negocio. ej domicilio, legajo, edad, etc.
public class MainUser implements UserDetails {

	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;

	public MainUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}

	//En base a un usuario crea un mainUser con el username, password y roles que tenga el usuario
	//Necesita un User
	//Retorna un MainUser
	public static MainUser build(User user) {
		List<GrantedAuthority> autorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getRoleName().name())).collect(Collectors.toList());
		return new MainUser(user.getUsername(), user.getPassword(), autorities);
	}

	//Metodos de UserDetails de get
	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	//indica si el usuario esta habilitado
	@Override
	public boolean isEnabled() {
		return true;
	}

	//indica si la cuenta del usuario ha caducado
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	//indica si el usuario esta bloqueado
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	//indica si las credenciales del usuario han caducado
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

}
