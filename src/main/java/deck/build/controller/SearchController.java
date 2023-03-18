package deck.build.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import deck.build.entity.Card;
import deck.build.entity.CardCompositeX;
import deck.build.entity.Deck;
import deck.build.entity.SetCards;
import deck.build.service.CardService;
import deck.build.service.DeckService;
import deck.build.service.SearchService;
import deck.build.service.ServiceUtilsMisc;
import deck.build.service.SetService;

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
	
	
	String defaultCard = "https://i.imgur.com/nbO7LSF.png";
	
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
	public String getCards(String name, Model model, String setSearch, String deckViewName){
		System.out.println("Request search cards: ["+name+"] ["+setSearch+"] ["+deckViewName+"].");
		if(name.contains(" ")) {
			name=name.replace(" ", "*");
		}
		try {
			name=name+"*";
			List<Card> listCard = searchService.getPokemon(name, setSearch);
			model.addAttribute("listCard",listCard);
			model.addAttribute("name",name);
			
			ArrayList<String> decks = deckService.getAllDeckNames();
			//remove deckViewName, if it is not empty
			if(!StringUtils.isEmpty(deckViewName)) {
				int indexDeck = deckService.findDeckIndex(deckViewName, decks);
				if(indexDeck!=-1) {
					decks.remove(indexDeck);
				}
			}else {
				deckViewName = "NODECK";
			}
			
			Collections.reverse(decks);
			model.addAttribute("decks",decks);
			
			model.addAttribute("deckViewName", deckViewName);
			return "search_results";
		}
		catch(IOException ioe){
			return "error";
		}
	}
	
	@GetMapping("/home")
	public String loadHome(Model model) {
		try {
			ArrayList<Deck> decks = new ArrayList<>(deckService.getAllDecks());
			Collections.reverse(decks);
			
			//adding deck leader image
			for(Deck deck: decks) {
				if(deck.getCards().isEmpty()) {
					deck.setDeckLeadImage(defaultCard);
				}else {
					ArrayList<Card> sortDeckHP = deckService.sortDeckHP(deck);
					if(StringUtils.isEmpty(deck.getDeckLeadImage())){
						//if it is not set any deck leader, show the one with more HP
						deck.setDeckLeadImage(sortDeckHP.get(0).getImageLarge());
					}else {
						//show chosen leader
					}
				}
			}
			
			model.addAttribute("decks", decks);
			
			return "home";
		}catch(Exception ioe){
			return "error";
		}
	}
	
	@GetMapping("/addCard")
	public String addCard(String nameDeck, String nameCard, Integer numberOfCards, 
			String subtype, String supertype, String evolvesFrom, String artist, String hp, 
			String series, String setName, Model model){		
		try {
			Deck deck = deckService.addCardToDeck(nameDeck, nameCard, numberOfCards);
			Card card = cardService.createCard(nameCard,subtype,supertype,evolvesFrom,artist, hp, series,setName);
//			System.out.println("card after save "+card.getImageLarge()+" | SUBTYPE: "+card.getSubtype()+", evolves from: "+card.getEvolvesFrom()
//				+" HP="+card.getHp()+ " Artist "+card.getArtist());
			
			ArrayList<Card> sortDeckHP = deckService.sortDeckHP(deck);
			//setting deck lead image
			if(deck.getCards().isEmpty()) {
				deck.setDeckLeadImage(defaultCard);
			}else {
				deck.setDeckLeadImage(sortDeckHP.get(0).getImageLarge());
			}
			model.addAttribute("deck",deck);

			model.addAttribute("sortDeckHP",sortDeckHP);
			String message = "The card has been added to the deck"; 
			model.addAttribute("message",message);
			Integer countCards = ServiceUtilsMisc.countCards(deck.getCards());
			model.addAttribute("count",countCards);
			return "deck_builder"; //	src/main/resources/templates/
		}catch(Exception ioe){
			return "error";
		}
	}

	@GetMapping("/deleteCard")
	public String deleteCard(String name, String nameCard, String creator, Model model) {
		try{
			Deck deck = deckService.getDeck(name);
			Set<String> cardsSet = deck.getCards().keySet();
			for(String card: cardsSet) {
				if(nameCard.equals(card)) {
					deck.getCards().remove(card);
					if(deck.getDeckLeadImage()!=null) {
						if(deck.getDeckLeadImage().equals(nameCard)) {
							deck.setDeckLeadImage("");
						}
					}
					deckService.save(deck);
					String message = "The card selected has been deleted"; 
					model.addAttribute("message",message);
					break;
				}
			}
			ArrayList<Card> sortDeckHP = deckService.sortDeckHP(deck);
			if(deck.getCards().isEmpty()) {
				deck.setDeckLeadImage(defaultCard);
			}else {
				deck.setDeckLeadImage(sortDeckHP.get(0).getImageLarge());
			}
			model.addAttribute("deck", deck);
			model.addAttribute("sortDeckHP",sortDeckHP);
			Integer countCards = ServiceUtilsMisc.countCards(deck.getCards());
			model.addAttribute("count",countCards);
			return "deck_builder";
		}catch(Exception ioe){
			return "error";
		}
	}
	
	@GetMapping("/createDeck")
	public String createDeck(String name, String creator, String deckBox, Model model, RedirectAttributes redirAttrs) {
		try {
			if(name.length()>15) {
				String message = "The deck "+name+" is too long: the maximum lenght is 15"; 
				model.addAttribute("message",message);
			}else {
				Deck newDeck = deckService.createDeck(name, creator, deckBox);
				newDeck.setDeckLeadImage(defaultCard);
				model.addAttribute("deck",newDeck);
				
				String message = "The deck "+name+" was created by the user "+creator; 
				redirAttrs.addFlashAttribute("message", message);
				model.addAttribute("message",message);
			}
			
			ArrayList<Deck> decks = new ArrayList<>(deckService.getAllDecks());
			Collections.reverse(decks);
			
			//adding deck leader image
			for(Deck deck: decks) {
				if(deck.getCards().isEmpty()) {
					deck.setDeckLeadImage(defaultCard);
				}else {
					ArrayList<Card> sortDeckHP = deckService.sortDeckHP(deck);
					if(StringUtils.isEmpty(deck.getDeckLeadImage())){
						//if it is not set any deck leader, show the one with more HP
						deck.setDeckLeadImage(sortDeckHP.get(0).getImageLarge());
					}else {
						//show chosen leader
					}			
				}
			}
			
			model.addAttribute("decks", decks);
			
			return "home";
		}catch(Exception ioe) {
			return "error";
		}
	}   
	
	@GetMapping("/deleteDeck")
	public String deleteDeck(String name, Model model) {
		try {
			String message = deckService.deleteDeck(name);
			model.addAttribute("message_delete",message);
			
			ArrayList<Deck> decks = new ArrayList<>(deckService.getAllDecks());
			Collections.reverse(decks);
			
			//adding deck leader image
			for(Deck deck: decks) {
				if(deck.getCards().isEmpty()) {
					deck.setDeckLeadImage(defaultCard);
				}else {
					ArrayList<Card> sortDeckHP = deckService.sortDeckHP(deck);
					if(StringUtils.isEmpty(deck.getDeckLeadImage())){
						//if it is not set any deck leader, show the one with more HP
						deck.setDeckLeadImage(sortDeckHP.get(0).getImageLarge());
					}else {
						//show chosen leader
					}			}
			}
					
			model.addAttribute("decks", decks);
			
			try {
				ArrayList<SetCards> sets = new ArrayList<>(setService.getSets());
				model.addAttribute("sets",sets);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return "home";
		}catch(Exception ioe) {
			return "error";
		}
	}    
	
	@GetMapping("/viewDeck")
	public String viewDeck(String name, Model model) {
		try {
			System.out.println("searching deck "+name);
			Deck deck = deckService.getDeck(name);		
			model.addAttribute("deck",deck);

//			HashMap<String,Integer> sortedDeck= deckService.sortDeck(deck); 
//			HashMap<Integer,CardCompositeX> sortedCards= deckService.sortDeck(deck); 
//			model.addAttribute("sortedCards",sortedCards);

			ArrayList<Card> sortDeckHP = deckService.sortDeckHP(deck);
			//setting deck lead image
			if(deck.getCards().isEmpty()) {
				deck.setDeckLeadImage(defaultCard);
			}else {
				if(StringUtils.isEmpty(deck.getDeckLeadImage())){
					//if it is not set any deck leader, show the one with more HP
					deck.setDeckLeadImage(sortDeckHP.get(0).getImageLarge());
				}else {
					//show chosen leader
				}
			}
			model.addAttribute("sortDeckHP",sortDeckHP);
			
			Integer countCards = ServiceUtilsMisc.countCards(deck.getCards());
			model.addAttribute("count",countCards);
			return "deck_builder";
		}catch(Exception ioe) {
			return "error";
		}
	}
	
	@GetMapping("/starCard")
	public String starCard(String name, String nameCard, String creator, Model model) {
		try {
			System.out.println("starCard "+name+" by "+creator+" :"+nameCard);
			Deck deck = deckService.getDeck(name);		
			
			ArrayList<Card> sortDeckHP = deckService.sortDeckHP(deck);
			
			int indexStarCard = 0;
			for (Card card : sortDeckHP) {
				if(card.getImageLarge().equals(nameCard)) {
					indexStarCard=sortDeckHP.indexOf(card);
					System.out.println("Starring card "+nameCard+"="+card.getImageLarge()+" at index "+indexStarCard);
					String message = "New card starred as deck leader";
					model.addAttribute("message",message);
					break;
				}
			}
			deck.setDeckLeadImage(sortDeckHP.get(indexStarCard).getImageLarge());
			deckService.save(deck);
			model.addAttribute("deck",deck);

			model.addAttribute("sortDeckHP",sortDeckHP);
			
			Integer countCards = ServiceUtilsMisc.countCards(deck.getCards());
			model.addAttribute("count",countCards);
			return "deck_builder";
		}catch(Exception ioe) {
			return "error";
		}
	}
	
	@GetMapping("/plusOneCard")
	public String plusOneCard(String name, String nameCard, String creator, String quantity, Model model) {
		try {
			System.out.println("+1Card "+name+" by "+creator+" :"+nameCard+" | quantity "+quantity);
			Deck deck = deckService.getDeck(name);		
			
			HashMap<String,Integer> mapCards = deck.getCards();
			Integer quantityCard = mapCards.get(nameCard);
			quantityCard = quantityCard +1;
			mapCards.put(nameCard, quantityCard);			
			model.addAttribute("deck",deck);

			ArrayList<Card> sortDeckHP = deckService.sortDeckHP(deck);
			model.addAttribute("sortDeckHP",sortDeckHP);

			String message = "Card quantity increased";
			model.addAttribute("message",message);

			return "deck_builder";
		}catch(Exception ioe) {
			return "error";
		}
	}
	
	@GetMapping("/minusOneCard")
	public String minusOneCard(String name, String nameCard, String creator, String quantity, Model model) {
		try {
			System.out.println("-1Card "+name+" by "+creator+" :"+nameCard+" | quantity "+quantity);
			Deck deck = deckService.getDeck(name);		
			
			HashMap<String,Integer> mapCards = deck.getCards();
			Integer quantityCard = mapCards.get(nameCard);
			quantityCard = quantityCard -1;
			mapCards.put(nameCard, quantityCard);			
			model.addAttribute("deck",deck);
			
			ArrayList<Card> sortDeckHP = deckService.sortDeckHP(deck);
			model.addAttribute("sortDeckHP",sortDeckHP);
			
			String message = "Card quantity increased";
			model.addAttribute("message",message);
			
			return "deck_builder";
		}catch(Exception ioe) {
			return "error";
		}
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
	
	@GetMapping("/artists")
	public String showArtists(Model model) {
		return "artists";
	}
	
	@GetMapping("/searchArtist")
	public String searchCardsArtist(String artist, Model model) {
		return "search_artist_cards";
	}
	
	
}
