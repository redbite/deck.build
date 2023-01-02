package com.example.pocketMonsterCollector.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pocketMonsterCollector.entity.Deck;
import com.example.pocketMonsterCollector.repository.DeckRepository;

@Service
public class DeckService {
	@Autowired
	DeckRepository deckRepository;
	
	//Returns OK or error
	public Deck createDeck(String name, String creator) {
		//checking rules
		Deck deck = new Deck();
		
		//check on name being unique
		ArrayList<Deck> existingDecks = deckRepository.findAllByName(name);
		if(existingDecks.size() == 0) {
			deck.setCreator(creator);
			deck.setName(name);
			deckRepository.save(deck);
		}
		
		return deck;
	}
	
	public Deck getDeck(String name) {
		return deckRepository.findAllByName(name).get(0);
	}
	
	public Deck addCardToDeck(String name, String card, Integer number) {
		Deck deck = getDeck(name);
		deck.getCards().put(card, number);
		deckRepository.save(deck);
		return deck;
	}
	
	public ArrayList<String> getAllDeckNames() {
		return deckRepository.findAllNames();
	}
}