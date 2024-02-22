package com.kodnest.tunehubproject.programming.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.kodnest.tunehubproject.programming.entities.Songs;
import com.kodnest.tunehubproject.programming.entities.Users;
import com.kodnest.tunehubproject.programming.services.SongService;
import com.kodnest.tunehubproject.programming.services.UsersService;

import jakarta.servlet.http.HttpSession;

@Controller
public class SongsController {
	
	@Autowired
	SongService songserv;
	
	@Autowired
	UsersService userv;
	
	
	
	@PostMapping("/addsongs")
	public String addSongs(@ModelAttribute Songs song) {
		/*here we are checking whether the song decided to add is already exists or not , if the song does not exits then songExists() will
		  return false i.e means the song is not present  if it returns false then only we can add the song  to the database , if not it will return
		   true then we can't add the song and using the name of the song we are checking whether song exists or not*/
		boolean songstatus = songserv.songExists(song.getName());
		//if songExists() return false then Admin will be able to add the songs and it will be redirected to songsuccess.html file
		if (songstatus == false) 
		{
			songserv.addSongs(song);
			return "songsuccess";
		} 
		//if songExists() return True then Admin will not  be able to add the songs and it will be redirected to songfail.html file
		else 
		{
			return "songfail";
		}
		
	}
	// i.e "/map-viewsongs" url is present in adminhome.html page to view songs by admin in admin page
	@GetMapping("/map-viewsongs")
	public String viewSongs(Model model) {
		/*All songs are fetched using fetchAllSongs() present in SongService and it returns the list of all songs present in the database 
		 																																and they are collected in a List and displayed*/
		List<Songs> songslist=songserv.fetchAllSongs();
		/*if we want to display any data on the web page we cannot directly do it rather we have to use Model and send the data to the
																model and then by using Thyme leaf we can display the model on the we page.*/
		model.addAttribute("songslist", songslist);
		return "displaysongs";
		
	}
	// i.e "/viewsongs" url is present in customerhome.html page to view songs by customer in customer page
	@GetMapping("/viewCustomerSongs")
	public String viewCustomerSongs(HttpSession session,Model model) {
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
					//return "samplepayment";
					//samplepayment.html page is created to test whether the payment is working properly or not
				}
			
	}


}
