package com.example.pocketMonsterCollector.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.pocketMonsterCollector.entity.Card;
import com.example.pocketMonsterCollector.entity.Deck;
import com.example.pocketMonsterCollector.repository.CardRepository;

@Service
public class CardService {
	@Autowired
	CardRepository cardRepository;
	
	//Returns OK or error
	public Card createCard(String nameCard, String subtype, String evolvesFrom,
			String artist, String hp, String series,String setName) {
		//checking rules
		Card card = new Card();
		
		//check on name being unique
		ArrayList<Card> existingCards = cardRepository.findAllByName(nameCard);
		if(existingCards.size() == 0) {
			card.setImageLarge(nameCard);
			card.setSubtype(subtype);
			card.setEvolvesFrom(evolvesFrom);
			card.setArtist(artist);
			card.setHp(hp);
			card.setSeries(series);
			card.setSetName(setName);
			cardRepository.save(card);
		}else {
			card=existingCards.get(0);
		}
		
		return card;
	}
	
	public Card getCard(String name) {
		return cardRepository.findAllByName(name).get(0);
	}
	
	@Transactional
	public String deleteCard(String name) {
		try {
			cardRepository.delete(name);
			return "Card "+name+" deleted";
		}catch(Exception e) {
			return "Card "+name+" couldn't be deleted";
		}
	}
}