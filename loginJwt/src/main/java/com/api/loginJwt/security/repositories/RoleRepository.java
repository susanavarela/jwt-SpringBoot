package com.api.loginJwt.security.repositories;

import java.util.Optional;

import com.api.loginJwt.security.entities.Role;
import com.api.loginJwt.security.enums.RoleList;

import org.springframework.data.jpa.repository.JpaRepository;

//Clase de comunicacion con la base de datos de Role
public interface RoleRepository extends JpaRepository<Role, Integer>{

		Optional<Role> findByRoleName(RoleList roleName);
}
