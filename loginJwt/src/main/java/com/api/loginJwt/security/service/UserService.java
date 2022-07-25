package com.api.loginJwt.security.service;

import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.loginJwt.security.entities.User;
import com.api.loginJwt.security.repositories.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	UserRepository userRepository;

	public Optional<User> getByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public boolean existByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	public void save(User user) {
		userRepository.save(user);
	}
}
