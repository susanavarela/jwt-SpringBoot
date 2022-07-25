package com.api.loginJwt.security.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.api.loginJwt.security.enums.RoleList;

//Clase que impacta en la base de datos, guarda los roles
@Entity
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@Enumerated(EnumType.STRING)
	private RoleList roleName;
	

	public Role(@NotNull RoleList roleName) {
		super();
		this.roleName = roleName;
	}

	public Role() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public RoleList getRoleName() {
		return roleName;
	}

	public void setRoleName(RoleList roleName) {
		this.roleName = roleName;
	}

}
