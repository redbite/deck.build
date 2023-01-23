package com.example.pocketMonsterCollector.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.pocketMonsterCollector.entity.Card;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SearchService {
    private HttpHeaders headers;
    private ObjectMapper mapper;
    @Value("${api.secret}")
    private String apiSecret;
    
    @PostConstruct
    private void init() {
//    	System.out.println(apiSecret);
    }
    
    public SearchService (ObjectMapper mapper){
        this.mapper = mapper;
        this.headers = new HttpHeaders();
    }
    
	private ResponseEntity<String> callApi(String url) {
		headers.set("X-Api-Key", apiSecret);
        HttpEntity entity = new HttpEntity(headers);
        RestTemplate template = new RestTemplate();
        return template.exchange(url, HttpMethod.GET, entity, String.class);
    }
	
	public List<Card> getPokemon (String name, String setSearch) throws IOException {
        List<Card> list = new ArrayList<>();
//      Integer pageSize = 40;
//      String url = "https://api.pokemontcg.io/v2/cards?q=name:"+name+"&pageSize="+pageSize;
        
//        setSearch=setSearch.replace(" ", "-");
//        setSearch=setSearch.toLowerCase();
        
    	String url = "https://api.pokemontcg.io/v2/cards?q=name:"+name;
    	
        if(!"ALL_SETS".equals(setSearch)) {
        	//filter by set
        	url = "https://api.pokemontcg.io/v2/cards?q=name:"+name+" set.name:"+setSearch;
        }
        System.out.println("URL: "+url);

        String pokejson = callApi(url).getBody();
//        System.out.println(pokejson);
        pokemonResultsList(pokejson,list);
        return list;
    }
	
	private void pokemonResultsList(String pokejson, List<Card> list) throws IOException {
        JsonNode response = mapper.readTree(pokejson);
        JsonNode pokemonCards = response.path("data");
//        System.out.println(response.path("data"));        
		
        for(JsonNode node : pokemonCards){
            Card pkmnCard = createPokeCard(node);
            list.add(pkmnCard);
        }
    }
	
    private Card createPokeCard(JsonNode node){
//    	System.out.println(" images: "+node.path("images").asText());
//    	System.out.println(" 	images large "+node.path("images").path("large").asText());
//    	System.out.println(" flavorText: "+node.path("flavorText").asText());
//    	System.out.println(" artist "+node.path("artist").asText());

        Card pkmnCard = new Card();
            pkmnCard.setId(node.path("id").asText());
            pkmnCard.setName(node.path("name").asText());
            pkmnCard.setNationalPokedexNumber(node.path("nationalPokedexNumber").asInt());
            pkmnCard.setImageUrl(node.path("images").path("small").asText());
            pkmnCard.setImageUrlHiRes(node.path("images").path("large").asText());
            pkmnCard.setSupertype(node.path("supertype").asText());
            pkmnCard.setSubtype(node.path("subtype").asText());
            pkmnCard.setEvolvesFrom(node.path("evolvesFrom").asText());
            pkmnCard.setHp(node.path("hp").asText());
            pkmnCard.setNumber(node.path("number").asText());
            pkmnCard.setArtist(node.path("artist").asText());
            pkmnCard.setRarity(node.path("rarity").asText());
            pkmnCard.setSeries(node.path("series").asText());
            pkmnCard.setSet(node.path("set").asText());
            pkmnCard.setSetCode(node.path("setCode").asText());
            pkmnCard.setFlavorText(node.path("flavorText").asText());
        return pkmnCard;
    }
    
}
