package com.api.loginJwt.security.service;

import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.loginJwt.security.entities.Role;
import com.api.loginJwt.security.enums.RoleList;
import com.api.loginJwt.security.repositories.RoleRepository;

@Service
@Transactional
public class RoleService {

	@Autowired
	RoleRepository roleRepository;

	public Optional<Role> getByRoleName(RoleList roleName) {
		return roleRepository.findByRoleName(roleName);
	}
}
