package com.cse3345.f13.moore;

import java.io.Serializable;

public class PlayerCard implements Serializable {

	public String rank;
	public int suit;
	public int isFaceCard;
	public int isAce;
	public int face;
	
	public PlayerCard(String s, int su, int i, int is, int f) {
		
		rank = s;
		suit = su;
		isFaceCard = i;
		isAce = is;
		face = f;
		
	}
	
}
