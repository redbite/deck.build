package deck.build.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import deck.build.entity.Card;
import deck.build.entity.CardCompositeX;
import deck.build.entity.Deck;
import deck.build.repository.DeckRepository;

@Service
public class DeckService {
	@Autowired
	DeckRepository deckRepository;
	@Autowired
	CardService cardService;
	
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
	
	@Transactional
	public String deleteDeck(String name) {
		try {
			deckRepository.delete(name);
			return "Deck "+name+" deleted";
		}catch(Exception e) {
			return "Deck "+name+" couldn't be deleted";
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

	/*
	 * Given a deck,
	 * returns the deck sorted
	 * 	1. HP (from grater to smaller)
	 * 	2. Evolutions
	 *  3. Basic cards
	 *  4. Trainer or Energy cards by quantity, decreasing
	 */
	public HashMap<Integer, CardCompositeX> sortDeck(Deck deck) {
		HashMap<Integer, CardCompositeX> sortedDeck = new HashMap<>(); //KEY: order		VALUE: data
		ArrayList<Card> cards = new ArrayList<>();
		
		//Populate cards
		for(String cardName: deck.getCards().keySet()) {
			Card card = cardService.getCard(cardName);
			cards.add(card);
		}
		
		//find maximum hp
		Integer maximumHp=findMaxHP(cards);
		
		//ordering cards for HP
		Collections.sort(cards, Card.compareHp);
		Collections.reverse(cards);
		
//		ArrayList<Card> cardsAux = cards;
//		
//		while(!cards.isEmpty()) {
//			for(Card card: cards) {
//				if(!StringUtils.isEmpty(card.getEvolvesFrom())) {
//					for(Card cAux: cardsAux) {
//						if(cAux.getPkmnName().equals(card.getEvolvesFrom())) {
//							
//						}
//					}
//				}
//			}
//		}
		
		//getting the number of cards (hashmap key by its value) 
		Integer order = 0;
		for(Card card: cards) {
			Integer numberOfCards = deck.getCards().get(card.getImageLarge());
			CardCompositeX cardCX = new CardCompositeX(card, numberOfCards, null);
			sortedDeck.put(order, cardCX);
			order++;
		}
		
		return sortedDeck;
	}
	
	/*
	 * returns an arrayList which contains the card ordered by HP decreasing:
	 * cards with populated HP value
	 * cards with HP value 1 (cards which have supertype!= pokemon and energy (trainer cards))
	 * cards with Hp valye 0 (cards which have supertype== energy (energy cards))
	 */
	public ArrayList<Card> sortDeckHP(Deck deck) {
		HashMap<Integer, CardCompositeX> sortedDeck = new HashMap<>(); //KEY: order		VALUE: data
		ArrayList<Card> cards = new ArrayList<>();
		
		//Populate cards
		for(String cardName: deck.getCards().keySet()) {
			Card card = cardService.getCard(cardName);
			cards.add(card);
		}
		
		//find maximum hp
//		Integer maximumHp=findMaxHP(cards);
		
		//ordering cards for HP
		Collections.sort(cards, Card.compareHp);
		Collections.reverse(cards);
		
		return cards;
	}
	
	
	
	
	public Integer findMaxHP(ArrayList<Card> cards) {
		Integer max = 0;
		for(Card card: cards) {
			if(card.getHpInt()>max) {
				max=card.getHpInt();
			}
		}
		return max;
	}
	
	/*
	 * given a deck name and an arraylist of deck names, 
	 * return the index of the element which has the same name
	 * returns -1 if it's not present
	 */
	public Integer findDeckIndex(String deckName, ArrayList<String> decks) {
		int index=0;
		for(String str: decks) {
			if(deckName.equals(str)) {
				return index;
			}
			index++;
		}
		return -1;
	}
	
	
}