package com.example.appddiction.repositories;

import com.example.appddiction.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByFirstName(String firstName);
	User findByLastName(String lastName);
	User findByEmail(String email);
}
