package com.kodnest.tunehubproject.programming.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodnest.tunehubproject.programming.entities.Songs;

public interface SongRepository extends JpaRepository<Songs,Integer>{
	/*findByName() is created to know the with already present song cannot be added i.e to not to add duplicate songs , 
	 																		we use this findByName() to know whether the song is already exists in database.*/
	public Songs findByName(String name);
}
