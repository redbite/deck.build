package com.example.pocketMonsterCollector.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.pocketMonsterCollector.entity.Card;
import com.example.pocketMonsterCollector.entity.Deck;
import com.example.pocketMonsterCollector.entity.SetCards;
import com.example.pocketMonsterCollector.service.CardService;
import com.example.pocketMonsterCollector.service.DeckService;
import com.example.pocketMonsterCollector.service.SearchService;
import com.example.pocketMonsterCollector.service.ServiceUtilsMisc;
import com.example.pocketMonsterCollector.service.SetService;

@Controller
public class SearchController {
	@Autowired
	SearchService searchService;
	@Autowired
	DeckService deckService;
	@Autowired
	SetService setService;
	@Autowired
	CardService cardService;
	
	//testing purpose
//	@GetMapping("/searchCardsJSON")
//	public ResponseEntity<?> getCardsJSON(@RequestParam(value = "name") String name, Model model){
//		try {
//			return new ResponseEntity<>(searchService.getPokemon(name,"ALL_SETS"), HttpStatus.OK);
//		}
//		catch(IOException ioe){
//			return new ResponseEntity<>(ioe.getMessage(), HttpStatus.BAD_REQUEST);
//		}
//	}
	
	@GetMapping("/searchCards")
	public String getCards(String name, Model model, String setSearch){
		if(!name.contains(" ")) {
			try {
//				if(partialcheckbox) {
					name=name+"*";
//				}
				List<Card> listCard = searchService.getPokemon(name, setSearch);
//				for(Card card: listCard) {
//					System.out.println(card.toString());
//				}
				model.addAttribute("listCard",listCard);
				model.addAttribute("name",name);
				
				ArrayList<String> decks = deckService.getAllDeckNames();
				Collections.reverse(decks);
				model.addAttribute("decks",decks);
				return "search_results";
			}
			catch(IOException ioe){
				return "";
			}
		}else {
			String message="Try to search without inserting spaces";
			model.addAttribute("message",message);
			return "search_results";

		}
	}
	
	@GetMapping("/home")
	public String loadHome(Model model) {
		ArrayList<Deck> decks = new ArrayList<>(deckService.getAllDecks());
		Collections.reverse(decks);
		model.addAttribute("decks", decks);
		try {
			ArrayList<SetCards> sets = new ArrayList<>(setService.getSets());
			model.addAttribute("sets",sets);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "home";
	}
	
	@GetMapping("/addCard")
	public String addCard( String nameDeck, String nameCard, Integer numberOfCards, 
			String subtypes, String evolvesFrom, String artist, String hp, 
			String series, String setName, Model model){		
		Deck deck = deckService.addCardToDeck(nameDeck, nameCard, numberOfCards);
		Card card = cardService.createCard(nameCard,subtypes,evolvesFrom,artist, hp, series,setName);
		System.out.println("card after save "+card.getImageLarge()+" | SUBTYPE: "+card.getSubtype()+", evolves from: "+card.getEvolvesFrom()
			+" HP="+card.getHp()+ " Artist "+card.getArtist()+ " " +card.getSetName()+"#"+card.getSeries()
				);
		
		model.addAttribute("deck",deck);
		String message = "The card has been added to the deck"; 
		model.addAttribute("message",message);
		Integer countCards = ServiceUtilsMisc.countCards(deck.getCards());
		model.addAttribute("count",countCards);
		return "deck_builder"; //	src/main/resources/templates/
	}

	@GetMapping("/deleteCard")
	public String deleteCard(String name, String nameCard, String creator, Model model) {
		Deck deck = deckService.getDeck(name);
		Set<String> cardsSet = deck.getCards().keySet();
		for(String card: cardsSet) {
			if(nameCard.equals(card)) {
				deck.getCards().remove(card);
				deckService.save(deck);
				String message = "The card selected has been deleted"; 
				model.addAttribute("message",message);
				break;
			}
		}
		model.addAttribute("deck", deck);
		Integer countCards = ServiceUtilsMisc.countCards(deck.getCards());
		model.addAttribute("count",countCards);
		return "deck_builder";
	}
	
	@GetMapping("/createDeck")
	public String createDeck(String name, String creator, String deckBox, Model model, RedirectAttributes redirAttrs) {
		Deck deck = deckService.createDeck(name, creator, deckBox);
		model.addAttribute("deck",deck);
		
		String message = "The deck "+name+" was created by the user "+creator; 
		redirAttrs.addFlashAttribute("message", message);
		model.addAttribute("message",message);
		
		ArrayList<Deck> decks = new ArrayList<>(deckService.getAllDecks());
		Collections.reverse(decks);
		model.addAttribute("decks", decks);
		
		try {
			ArrayList<SetCards> sets = new ArrayList<>(setService.getSets());
			model.addAttribute("sets",sets);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "home";
	}   
	
	@GetMapping("/deleteDeck")
	public String deleteDeck(String name, Model model) {
		String message = deckService.deleteDeck(name);
		model.addAttribute("message_delete",message);
		
		ArrayList<Deck> decks = new ArrayList<>(deckService.getAllDecks());
		Collections.reverse(decks);
		model.addAttribute("decks", decks);
		
		try {
			ArrayList<SetCards> sets = new ArrayList<>(setService.getSets());
			model.addAttribute("sets",sets);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "home";
	}    
	
	@GetMapping("/viewDeck")
	public String viewDeck(String name, Model model) {
		System.out.println("searching deck "+name);
		Deck deck = deckService.getDeck(name);
		model.addAttribute("deck",deck);
		Integer countCards = ServiceUtilsMisc.countCards(deck.getCards());
		model.addAttribute("count",countCards);
		return "deck_builder";
	}
	
	//testing purpose
//	@GetMapping("/searchSetsJSON")
//	public ResponseEntity<?> getSetsJSON(Model model){
//		try {
//			return new ResponseEntity<>(setService.getSets(), HttpStatus.OK);
//		}
//		catch(IOException ioe){
//			return new ResponseEntity<>(ioe.getMessage(), HttpStatus.BAD_REQUEST);
//		}
//	}
	
//	@GetMapping("/searchSets")
//	public String getSets getSets(Model model) {
//		setService.getSets();
//		return "Sets"; 
//	}
}
