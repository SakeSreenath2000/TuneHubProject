package com.kodnest.tunehubproject.programming.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kodnest.tunehubproject.programming.entities.Songs;
import com.kodnest.tunehubproject.programming.entities.Users;
import com.kodnest.tunehubproject.programming.services.SongService;
import com.kodnest.tunehubproject.programming.services.UsersService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsersController {
	@Autowired
	UsersService userv;
	//we can use the Parameterized constructor here or else we can use @Autowired annotation
	@Autowired
	SongService songserv;

	@PostMapping("/register")
	public String addUser(@ModelAttribute Users user) {
		/*here we are checking whether the email entered is already registered or not if the email is not registered then 
		 																																user will be added or else we will redirect to registerfail page*/
		//we are checking the email whether already exists or not by using emailExists() which is present in UsersService 
		boolean userstatus = userv.emailExists(user.getEmail());
		/*if the emailExists() will return false i.e means the email is not registered then only the user will be added and then 
		 																																											it will be redirected to registersuccess*/
		if (userstatus == false) {
			userv.addUser(user);
			return "registersuccess";
		} 
		//if email is already registered then it will be redirected to registerfail page
		else {
			return "registerfail";
		}

	}

	@PostMapping("/login")
	public String validateUser(@RequestParam String email, @RequestParam String password,HttpSession session) {
		/*
		 boolean loginstatus = userv.validateUser(email, password); 
		 if (loginstatus == true)
		 { 
		 	String role=userv.getRole(email); 
		 	if(role.equals("admin")) 
		 	{ 
		 		return "adminhome"; 
		 	} 
		 	else 
		 	{ 
		 		return "customerhome"; 
		 	}
		 */
		//Calling validateUser() present UsersService to check the details entered by the user is registered 
		if (userv.validateUser(email, password) == true) {
			session.setAttribute("email", email);
			//checking wheather the user is admin or customer
			//if user is admin then he will be redirected to adminhome page
			//to know wheather user is admin or customer we are calling the getRole() present in UsersService which will return the role of the email of the user
			if (userv.getRole(email).equals("admin")) {
				return "adminhome";

			} 
			//if user is customer then he will be redirected to customerhome page
			else {
				return "customerhome";
			}

		} 
		//if the details entered by the user is not valid i.e means not present in database(Not Registered) then he will e redirected to loginfail page
		else {
			return "loginfail";
		}

	}
	
	@GetMapping("/exploreSongs")
	public String exploreSongs(HttpSession session,Model model) {
		//session objects are not created by us they are automatically created by the SpringBoot.
		/*sessions are used to track that which user has logged in and track the entire work done by the user until he logout , 
		 																																		if he logout the session will be destroyed automatically.*/
		//session.getAttribute() returns object class in order to take the email we downcast it in to String type
		String email=(String) session.getAttribute("email");
		/*i.e we are getting the email from the session and using getUser() from the UsersService to know whether the user is 
		  																																																				paid customer or not*/
		Users user=userv.getUser(email);
		//checking whether user is Premium user or not 
		//isPremium() will get whether user is paid customer or not in the form of true or false , if it is true then the user is paid customer
		boolean userStatus=user.isPremium();
		//if user is premium then it will be redirected to "displaysongs"
		if(userStatus==true) {
				/*All songs are fetched using fetchAllSongs() present in SongService and it returns the list of all songs present in the database 
				and they are collected in a List and displayed*/
			List<Songs> songslist=songserv.fetchAllSongs();
			/*if we want to display any data on the web page we cannot directly do it rather we have to use Model and send the data to the
				model and then by using Thyme leaf we can display the model on the we page.*/
			model.addAttribute("songslist", songslist);
			return "displaysongs";
		}
		//if not then user will be redirected to payment page where user can pay the money and become premium customer
		else {
			return "payment";
		}
		
	}
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		//session objects are not created by us they are automatically created by the SpringBoot.
		/*sessions are used to track that which user has logged in and track the entire work done by the user until he logout , 
		 																					if he logout the session will be destroyed automatically using "invalidate session".*/
		session.invalidate();
		//After logout the user is redirected to login again to login
		return "login";
	}
	

}
