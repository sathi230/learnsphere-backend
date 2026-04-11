package com.learnsphere.learnsphere.repository;

import com.learnsphere.learnsphere.entity.Course;
import com.learnsphere.learnsphere.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAndPassword(String email, String password);

	boolean existsByEmail(String email);
	
	
	

	User findByEmail(String name);
	long countByRole(String role);
	List<User> findByRole(String role);

	Optional<User> findByMobile(String mobile);

	Optional<User> findByNameAndMobile(String studentName, String mobile);
	
	
	

    
}
