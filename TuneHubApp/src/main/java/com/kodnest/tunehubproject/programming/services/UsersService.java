package com.kodnest.tunehubproject.programming.services;

import com.kodnest.tunehubproject.programming.entities.Users;

public interface UsersService {
	
	public String addUser(Users user);
	public boolean emailExists(String email);
	public boolean validateUser(String email, String password);
	public String getRole(String email);
	public Users getUser(String email);
	//here we are using this method to update the user status after paying the money to become premium customer
	public void updateUser(Users user);
}
