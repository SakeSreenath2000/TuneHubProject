package com.kodnest.tunehubproject.programming.services;

import java.util.List;

import com.kodnest.tunehubproject.programming.entities.Songs;

public interface SongService {
	public String addSongs(Songs song);
	public boolean songExists(String name);
	public List<Songs> fetchAllSongs();
	public void updateSong(Songs song);
	
}
