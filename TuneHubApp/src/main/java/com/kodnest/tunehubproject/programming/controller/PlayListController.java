package com.kodnest.tunehubproject.programming.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.kodnest.tunehubproject.programming.entities.PlayList;
import com.kodnest.tunehubproject.programming.entities.Songs;
import com.kodnest.tunehubproject.programming.entities.Users;
import com.kodnest.tunehubproject.programming.services.PlayListService;
import com.kodnest.tunehubproject.programming.services.SongService;
import com.kodnest.tunehubproject.programming.services.UsersService;

import jakarta.servlet.http.HttpSession;

@Controller
public class PlayListController {
	
	@Autowired
	PlayListService pservo;
	//we can use the Parameterized constructor here or else we can use @Autowired annotation
	
	@Autowired
	SongService sserv;
	//we can use the Parameterized constructor here or else we can use @Autowired annotation
	
	@Autowired
	UsersService userv;
	
	@GetMapping("/createplaylist")
	public String createplayList(Model model) {
		//Fetching the songs using SongService
		List<Songs> songslist=sserv.fetchAllSongs();
		//Adding the songs into the Model
		model.addAttribute("songslist", songslist);
		//Sending the control to createplaylist.html file
		return "createplaylist";
		
	}
	@PostMapping("/addplaylist")
	public String addPlayList(@ModelAttribute PlayList playlist) {
		//System.out.println(playlist);
		
		//adding the playlist
		pservo.addPlaylist(playlist);
		
		//update song table
		List<Songs> songsList=playlist.getSongs();
		//Using the for each loop here we are traversing the songs
		for(Songs song:songsList) {
			song.getPlaylist().add(playlist);
			//here we are updating the song according to the playlist that admin has added the song
			sserv.updateSong(song);
		}
		return "playlistsuccess";
	}
	
	@GetMapping("/viewplaylist")
	public String viewPlayList(Model model) {
		//Fetching the playlists using 	PlayListService
		List<PlayList> plist=pservo.fetchPlaylists();
		//System.out.println(plist);
		//Adding the playlist into the Model
		/*if we want to display any data on the web page we cannot directly do it rather we have to use Model and send the data to the
		  																								model and then by using Thyme leaf we can display the model on the we page.*/
		model.addAttribute("plist", plist);
		//Sending the control to viewplaylist.html file
		return "viewplaylist";
	}
	
	@GetMapping("/viewCustomersPlaylist")
	public String viewCustomersPlaylist(HttpSession session,Model model) {
		String email=(String) session.getAttribute("email");
		Users user=userv.getUser(email);
		boolean userStatus=user.isPremium();
		if(userStatus==true) {
		//Fetching the playlists using 	PlayListService
		List<PlayList> plist=pservo.fetchPlaylists();
		//System.out.println(plist);
		//Adding the playlist into the Model
		/*if we want to display any data on the web page we cannot directly do it rather we have to use Model and send the data to the
		  																								model and then by using Thyme leaf we can display the model on the we page.*/
		model.addAttribute("plist", plist);
		//Sending the control to viewplaylist.html file
		return "viewCustomersPlaylist";
		}
		else {
			return "payment";
		}
	}
	
	@GetMapping("/createCustomersPlaylist")
	public String createCustomersPlaylist(HttpSession session,Model model) {
		String email=(String) session.getAttribute("email");
		Users user=userv.getUser(email);
		boolean userStatus=user.isPremium();
		if(userStatus==true) {
		//Fetching the songs using SongService
		List<Songs> songslist=sserv.fetchAllSongs();
		//Adding the songs into the Model
		model.addAttribute("songslist", songslist);
		//Sending the control to createCustomersPlaylist.html file
		return "createCustomersPlaylist";
		}
		else {
			return "payment";
		}
		
	}
	
	@PostMapping("/addCustomersPlaylist")
	public String addCustomersPlaylist(@ModelAttribute PlayList playlist) {
		//System.out.println(playlist);
		
		//adding the playlist
		pservo.createCustomerPlaylist(playlist);
		
		//update song table
		List<Songs> songsList=playlist.getSongs();
		//Using the for each loop here we are traversing the songs
		for(Songs song:songsList) {
			song.getPlaylist().add(playlist);
			//here we are updating the song according to the playlist that customer has added the song
			sserv.updateSong(song);
		}
		return "customerplaylistsuccess";
	}
	
	

}
