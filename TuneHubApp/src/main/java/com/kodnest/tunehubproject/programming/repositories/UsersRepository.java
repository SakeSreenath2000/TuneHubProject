package com.kodnest.tunehubproject.programming.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodnest.tunehubproject.programming.entities.Users;

public interface UsersRepository extends JpaRepository<Users,Integer>
{
	/*findByEmail() is created because to know whether the email is already registered or not */
	public Users findByEmail(String email);
	
}
