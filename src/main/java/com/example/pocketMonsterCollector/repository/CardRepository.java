package com.example.pocketMonsterCollector.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.pocketMonsterCollector.entity.Card;
import com.example.pocketMonsterCollector.entity.Deck;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>{
	@Query("SELECT c FROM Card c WHERE imageLarge=:name")
	ArrayList<Card> findAllByName(String name);
	
	@Modifying
	@Query("DELETE FROM Card WHERE imageLarge=:name ")
	void delete(String name);
	
}
