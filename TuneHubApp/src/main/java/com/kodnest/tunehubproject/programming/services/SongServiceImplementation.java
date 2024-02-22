package com.kodnest.tunehubproject.programming.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodnest.tunehubproject.programming.entities.Songs;
import com.kodnest.tunehubproject.programming.repositories.SongRepository;

@Service
public class SongServiceImplementation implements SongService{
	@Autowired
	SongRepository srepo;

	@Override
	public String addSongs(Songs song) {
		srepo.save(song);
		return "Song is Added and Saved";
	}

	@Override
	public boolean songExists(String name) {
		//if song does not exists then it will return false i.e means if song does not exists then admin can be able to add songs
		if(srepo.findByName(name)==null) {
			return false;
		}
		//if song already exists with the song name that is decided to add then it will return true saying song already present again we can't add  it
		else {
			return true;
		}
		
	}

	@Override
	public List<Songs> fetchAllSongs() {
		//here all the fetched using inbuilt method findAll() and kept in a list and again we are returning the songslist
		List<Songs> songslist=srepo.findAll();
		return songslist;
	}

	@Override
	public void updateSong(Songs song) {
		//here we are updating the song according to the playlist that admin has added the song and saving the changes done
		srepo.save(song);
		
	}

}
