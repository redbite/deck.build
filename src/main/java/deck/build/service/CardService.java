package deck.build.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import deck.build.entity.Card;
import deck.build.entity.Deck;
import deck.build.repository.CardRepository;

@Service
public class CardService {
	@Autowired
	CardRepository cardRepository;
	
	//Returns OK or error
	public Card createCard(String nameCard, String subtype, String supertype,
			String evolvesFrom, String artist, String hp, String series, String setName) {
		//checking rules
		Card card = new Card();
		
		//check on name being unique
		ArrayList<Card> existingCards = cardRepository.findAllByName(nameCard);
		if(existingCards.size() == 0) {
			card.setImageLarge(nameCard);
			card.setSubtype(subtype);
			card.setSupertype(supertype);
			card.setEvolvesFrom(evolvesFrom);
			card.setArtist(artist);
			card.setHp(hp);
			card.setHpInt(hp);
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