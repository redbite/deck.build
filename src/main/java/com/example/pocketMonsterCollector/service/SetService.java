package com.example.pocketMonsterCollector.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.pocketMonsterCollector.entity.Card;
import com.example.pocketMonsterCollector.entity.SetCards;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SetService {
	private HttpHeaders headers;
    private ObjectMapper mapper;
    @Value("${api.secret}")
    private String apiSecret;
    
    public SetService (ObjectMapper mapper){
        this.mapper = mapper;
        this.headers = new HttpHeaders();
    }
    
    private ResponseEntity<String> callApi(String url) {
		headers.set("X-Api-Key", apiSecret);
        HttpEntity entity = new HttpEntity(headers);
        RestTemplate template = new RestTemplate();
        return template.exchange(url, HttpMethod.GET, entity, String.class);
    }
    
    public List<SetCards> getSets() throws IOException{
    	List<SetCards> list = new ArrayList<>();
    	String url="https://api.pokemontcg.io/v2/sets/";
    	String setsJson = callApi(url).getBody();
    	setResultsList(setsJson,list);
    	return list;
    }

	private void setResultsList(String setsJson, List<SetCards> list) throws IOException{
		JsonNode response = mapper.readTree(setsJson);
		System.out.println(response);
		JsonNode setNodes = response.path("data");
		for(JsonNode node : setNodes){
            SetCards set = createSet(node);
            list.add(set);
        }
	}

	private SetCards createSet(JsonNode node) {
		SetCards set = new SetCards();
		set.setName(node.path("name").asText());
		set.setLogo(node.path("images").path("logo").asText());
		set.setReleaseDate(node.path("releaseDate").asText());
		return set;
	}

}
