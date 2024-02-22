package com.kodnest.tunehubproject.programming.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodnest.tunehubproject.programming.entities.Users;
import com.kodnest.tunehubproject.programming.repositories.UsersRepository;

@Service
public class UsersServiceImplementation implements UsersService {
	@Autowired
	UsersRepository repo;

	@Override
	public String addUser(Users user) {
		repo.save(user);
		return "user is created and saved";
	}

	@Override
	public boolean emailExists(String email) {
		if(repo.findByEmail(email)==null) {
			return false;
		}
		else {
			return true;
		}
		
	}

	/*@Override
	public boolean validateUser(String email, String password) {
		Users user=repo.findByEmail(email);
		String db_password=user.getPassword();//here we are validating only the password but not validating the email.So we kept this
		 																				entire code in comments and written the same overridden method below where we are
		 																				 validating the email and password.
		if(db_password.equals(password)) {
			return true;
		}
		
		else {
			return false;
		}
	}*/
	@Override
	public boolean validateUser(String email, String password) {
	    Users user = repo.findByEmail(email);
	    
	    // Check if the user exists , if "user"(reference variable)  contains email that is present in database then we validate the password
	    if (user != null) {
	    	//here we are validating the password
	        String db_password = user.getPassword();
	        // Compare the passwords
	        if (db_password.equals(password)) {
	            return true; // Passwords match
	        } 
	        else {
	            return false; // Passwords do not match
	        }
	    }
	    else {
	        return false; // User with the provided email does not exist
	    }
	}

	@Override
	public String getRole(String email) {
		/*
		Users user=repo.findByEmail(email);
		String role=user.getRole();
		return role;
		//i.e we  can write like this also
		*/
		//as well as the above three lines we have written it in just one line
		return(repo.findByEmail(email).getRole());
	}

	@Override
	public Users getUser(String email) {
		return repo.findByEmail(email);
	}

	@Override
	public void updateUser(Users user) {
		//here we are saving the user status after the user has paid the money
		repo.save(user);
	}

}
