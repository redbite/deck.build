package com.example.pocketMonsterCollector.service;

import java.util.HashMap;

public class ServiceUtilsMisc {
	public static Integer countCards(HashMap<String, Integer> cards) {
		Integer count=0;
		for(String cardName : cards.keySet()) {
			count+=cards.get(cardName);
		}
		return count;
	}
}
