package com.api.loginJwt.security.controllers;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.loginJwt.entities.Message;
import com.api.loginJwt.security.dtos.JwtDTO;
import com.api.loginJwt.security.dtos.LoginUser;
import com.api.loginJwt.security.dtos.NewUser;
import com.api.loginJwt.security.entities.Role;
import com.api.loginJwt.security.entities.User;
import com.api.loginJwt.security.enums.RoleList;
import com.api.loginJwt.security.jwt.JwtProvider;
import com.api.loginJwt.security.service.RoleService;
import com.api.loginJwt.security.service.UserService;

@RestController
@RequestMapping("/security")
public class securityController {
	//Se inyectan AuthenticationManagerBuilder(se utiliza para crear un administrador de autenticación), passwordEncoder(encriptar)
	
	@Autowired
	AuthenticationManagerBuilder authenticationManagerBuilder;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	JwtProvider jwtProvider;
	
	
	//Verifica la autenticacion en la bd y genera un token
	//Necesita @Valid @RequestBody LoginUser y BindingResult
	//Retorna el token y respuesta ok(solicitud exitosa), si los datos no coinciden retorna un objeto mensaje con una frase de que los datos no son validos y BAD_REQUEST(solicitud incorrecta)
	@PostMapping("/login")
	public ResponseEntity<Object> login(@Valid @RequestBody LoginUser loginUser, BindingResult bindingResult){
		
		if (bindingResult.hasErrors()) {
			
			return new ResponseEntity<>(new Message("Los datos ingresados no son validos"), HttpStatus.BAD_REQUEST);
		}
		try {
			//Verifica la autenticacion en la bd y genera un token
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword());
			Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtProvider.generateToken(authentication);
            JwtDTO jwtDto = new JwtDTO(jwt);
            
            return new ResponseEntity<>(jwtDto, HttpStatus.OK);
            
		} catch (Exception e) {
			
			   return new ResponseEntity<>(new Message("Los datos ingresados no son validos"), HttpStatus.BAD_REQUEST);
		}	
	}
	
	
	//Genera un usuario y encripta lo pedido para seguridad. genera los roles, de forma automatica user, si se especifica admin
	//Necesita @Valid @RequestBody NewUser y BindingResult
	//Retorna un objeto mensaje de registro exitoso y respuesta CREATED(creado), si los datos son erroneos o no estan completos retorna un objeto mensaje con una frase de que lo intente nuevamente y BAD_REQUEST(solicitud incorrecta)
	@PostMapping("/register")
    public ResponseEntity<Object> resgister(@Valid @RequestBody NewUser newUser, BindingResult bindingResult) {
        
		if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new Message("Revise los campos e intente nuevamente"), HttpStatus.BAD_REQUEST);
        }
        User user = new User(newUser.getEmail(), passwordEncoder.encode(newUser.getPassword()), newUser.getUsername());
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getByRoleName(RoleList.ROLE_USER).get());
        
        if (newUser.getRoles().contains("admin")) {
            roles.add(roleService.getByRoleName(RoleList.ROLE_ADMIN).get());
        }
       
        user.setRoles(roles);
        userService.save(user);
        
        return new ResponseEntity<>(new Message("Registro exitoso! Inicie sesión"), HttpStatus.CREATED);
    }
    
}
