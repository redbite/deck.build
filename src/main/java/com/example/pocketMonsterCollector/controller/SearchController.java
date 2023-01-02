package com.example.pocketMonsterCollector.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
			return "search_results";
		}
		catch(IOException ioe){
			return "";
		}
	}
	
	@GetMapping("/openCard")
	public String openCard(@RequestParam(value = "name") String name, String numberOfCards, Model model){
		model.addAttribute("name", name);
		model.addAttribute("numberOfCards", numberOfCards);
		return "deck_builder"; //	src/main/resources/templates/???
	}
	
	@GetMapping("/createDeck")
	public String createDeck(@RequestParam(value="name") String name, String creator, Model model) {
		Deck deck = deckService.createDeck(name, creator);
		model.addAttribute("deck",deck);
		return "deck_builder";
	}
}
