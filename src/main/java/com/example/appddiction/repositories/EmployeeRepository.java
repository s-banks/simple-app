package com.example.appddiction.repositories;

import com.example.appddiction.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	Employee findByFirstName(String firstName);
	Employee findByLastName(String lastName);
	Employee findByEmail(String email);
}
