package com.example.pocketMonsterCollector.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.pocketMonsterCollector.entity.Deck;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long>{
	@Query("SELECT d FROM Deck d WHERE name=:name")
	ArrayList<String> findAllByName(String name);
	
}
