package com.api.loginJwt.security.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.api.loginJwt.security.entities.User;

//Clase de comunicacion con la base de datos de User
public interface UserRepository extends JpaRepository<User, String> {

	Optional<User> findByUsername(String username);

	boolean existsByUsername(String username);
}
