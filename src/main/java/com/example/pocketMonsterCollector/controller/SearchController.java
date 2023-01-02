package com.example.pocketMonsterCollector.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import com.example.pocketMonsterCollector.service.DeckService;
import com.example.pocketMonsterCollector.service.SearchService;

@Controller
public class SearchController {
	@Autowired
	SearchService searchService;
	@Autowired
	DeckService deckService;
	
	/*
	 * Testing purpose
	 */
	@GetMapping("/searchCardsJSON")
	public ResponseEntity<?> getCardsJSON(@RequestParam(value = "name") String name, Model model){
		try {
			return new ResponseEntity<>(searchService.getPokemon(name), HttpStatus.OK);
		}
		catch(IOException ioe){
			return new ResponseEntity<>(ioe.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/searchCards")
	public String getCards(@RequestParam(value = "name") String name, Model model){
		try {
			List<Card> listCard = searchService.getPokemon(name);
			for(Card card: listCard) {
				System.out.println(card.toString());
			}
			model.addAttribute("listCard",listCard);
			model.addAttribute("name",name);
			
			ArrayList<String> decks = deckService.getAllDeckNames();
			model.addAttribute("decks",decks);
			return "search_results";
		}
		catch(IOException ioe){
			return "";
		}
	}
	
	@GetMapping("/addCard")
	public String addCard( String nameDeck, String nameCard, Integer numberOfCards, Model model){		
		Deck deck = deckService.addCardToDeck(nameDeck, nameCard, numberOfCards);
		model.addAttribute("deck",deck);
		return "deck_builder"; //	src/main/resources/templates/???
	}
	
	/*
	 * refreshes the page giving a flash message (alert) feedback via RedirectAttributes
	 */
	@GetMapping("/createDeck")
	public String createDeck(String name, String creator, Model model, RedirectAttributes redirAttrs) {
		Deck deck = deckService.createDeck(name, creator);
		model.addAttribute("deck",deck);
		
		String message = "The deck "+name+" was created by the user "+creator; 
		redirAttrs.addFlashAttribute("message", message);
		model.addAttribute("message",message);
		return "home";
	}
}
