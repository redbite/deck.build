package com.example.pocketMonsterCollector.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pocketMonsterCollector.entity.Deck;
import com.example.pocketMonsterCollector.repository.DeckRepository;

@Service
public class DeckService {
	@Autowired
	DeckRepository deckRepository;
	
	//Returns OK or error
	public Deck createDeck(String name, String creator, String deckBox) {
		//checking rules
		Deck deck = new Deck();
		
		//check on name being unique
		ArrayList<Deck> existingDecks = deckRepository.findAllByName(name);
		if(existingDecks.size() == 0) {
			deck.setCreator(creator);
			deck.setName(name);
			deck.setDeckBox(deckBox);
			deckRepository.save(deck);
		}
		
		return deck;
	}
	
	public Deck getDeck(String name) {
		return deckRepository.findAllByName(name).get(0);
	}
	
	public String deleteDeck(String name) {
		try {
			deckRepository.delete(name);
			return "deck deleted";
		}catch(Exception e) {
			return "deck delete: error "+e.toString();
		}
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

	public void save(Deck deck) {
		deckRepository.save(deck);
	}

	public List<Deck> getAllDecks() {
		return deckRepository.findAll();
	}
}