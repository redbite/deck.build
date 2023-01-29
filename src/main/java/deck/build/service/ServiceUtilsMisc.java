package deck.build.service;

import java.util.HashMap;

public class ServiceUtilsMisc {
	public static Integer countCards(HashMap<String, Integer> cards) {
		Integer count=0;
		for(String cardName : cards.keySet()) {
			count+=cards.get(cardName);
		}
		return count;
	}
	
	/*
	 * Split the cards in 2 decks: <=4 and >4
	 * Split the <=4 cards in N decks, subdecks
	 * For each subdeck order the cards:
	 * 	- by size, by bigger to smaller
	 * 	- if a card has an evolve from, search if there is its pre-evo
	 *  - lastly the basic forms without any evo
	 *  - in the end put the trainer cards (TODO: cards with no basic or evolves from ?)
	 *  the deck >4 is mostly made of energy  
	 */
	public static HashMap<String, Integer> orderDeck(HashMap<String, Integer> cards){
		//get all the cards with <4 size
		HashMap<String, Integer> deckGT4 = new HashMap<String, Integer>();
		HashMap<String, Integer> deckLT4 = new HashMap<String, Integer>();
		for(String card: cards.keySet()) {
			Integer cardX = cards.get(card);
			if(cardX>4) {
				deckGT4.put(card, cardX);
			}else {
				deckLT4.put(card, cardX);
			}
		}
		//check if the cards are basic form or evolves from 
		for(String card: deckLT4.keySet()) {
//			if(card.getEvolvesFrom() == null) {}
		}
		//divide cards with size <4 in subzones
		
		//for each subsize
			//
		
		return null;
	}
}
