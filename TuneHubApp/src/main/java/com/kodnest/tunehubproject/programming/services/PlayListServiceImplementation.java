package com.kodnest.tunehubproject.programming.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodnest.tunehubproject.programming.entities.PlayList;
import com.kodnest.tunehubproject.programming.entities.Songs;
import com.kodnest.tunehubproject.programming.repositories.PlayListRepository;

@Service
public class PlayListServiceImplementation implements PlayListService{
	@Autowired
	PlayListRepository prepo;
	@Override
	public void addPlaylist(PlayList playlist) {
		prepo.save(playlist);
	}
	@Override
	public List<PlayList> fetchPlaylists() {
		//List<PlayList> pl=prepo.findAll();
		//return  pl;
		return prepo.findAll();
	}
	@Override
	public void createCustomerPlaylist(PlayList playlist) {
		prepo.save(playlist);
		
	}


}
