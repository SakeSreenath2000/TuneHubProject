package com.kodnest.tunehubproject.programming.services;

import java.util.List;

import com.kodnest.tunehubproject.programming.entities.PlayList;

public interface PlayListService {

	public void addPlaylist(PlayList playlist);

	public List<PlayList> fetchPlaylists();
	
	public void createCustomerPlaylist(PlayList playlist);


}
