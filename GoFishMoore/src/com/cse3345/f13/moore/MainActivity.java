package com.cse3345.f13.moore;

import java.util.ArrayList;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {

	private Drawable[] mSuits;
	private Drawable[] mFaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Resources res = getResources();
        
        /** Begin Game Logic */
        
        //get suit drawables
        TypedArray suitsIDs = res.obtainTypedArray(R.array.cardSuits);
        mSuits = new Drawable[suitsIDs.length()];
        for (int index = 0; index < suitsIDs.length(); index++) {
        	mSuits[index] = suitsIDs.getDrawable(index);
        }
        
        //get face card drawables
        TypedArray faceCardIDs = res.obtainTypedArray(R.array.faceCards);
        mFaces = new Drawable[faceCardIDs.length()];
        for (int index = 0; index < faceCardIDs.length(); index++) {
        	mFaces[index] = faceCardIDs.getDrawable(index);
        }
        
        //create deck
        Deck deck = new Deck();
        
    }
    
    private class Card {
    	public String value;
    	public Drawable suitImg;
    	public int suit;
    	public boolean isFaceCard;
    	public boolean isAce;
    	public Drawable face;
    	
    	public Card(int insuit, Drawable insuitImg, String invalue, boolean inisFaceCard, boolean inisAce) {
    		
    		suit = insuit;
    		suitImg = insuitImg;
    		value = invalue;    
    		isFaceCard = inisFaceCard;
    		isAce = inisAce;
    		setFace();
    	}
    	
    	private void setFace() {
    		
    		if (isFaceCard) {
    			
    			if (value.equals("K")) { //king
    				
    				if (suit == 1) { //hearts
    					
    					face = mFaces[0];
    					
    				} else if (suit == 2) { //clubs
    					
    					face = mFaces[2];
    					
    				} else if (suit == 3) { //diamonds
    					
    					face = mFaces[4];
    					
    				} else { //spades
    					
    					face = mFaces[6];
    					
    				}
    				
    			} else if (value.equals("Q")) { //queen
    				
    				
    				
    				if (suit == 1) { //hearts
    					
    					face = mFaces[1];
    					
    				} else if (suit == 2) { //clubs
    					
    					face = mFaces[3];
    					
    				} else if (suit == 3) { //diamonds
    					
    					face = mFaces[5];
    					
    				} else { //spades
    					
    					face = mFaces[7];
    					
    				}
    				
    			} else if (value.equals("J")) { //jack
    				
    				
    				
    				if (suit == 1 || suit == 3) { //hearts and diamonds: red
    					
    					face = mFaces[9];
    					
    				} else { //clubs and spades: black
    					
    					face = mFaces[8];
    					
    				}
    				
    			}
    			
    		}
    		
    	}
    }
    
    private class Hand {
    	public ArrayList<Card> cards;
    	
    	public Hand() {
    		cards = new ArrayList<Card>(5);
    	}
    	
    }
    
    private class Deck {
    	public ArrayList<Card> cards;
    	
    	public Deck() {
    		cards = new ArrayList<Card>(52);
    		generateDeck();
    	}
    	
    	private void generateDeck() {
    		
    		String value;
    		boolean isFaceCard = true;
    		boolean isAce = false;
    		Drawable face;
    		
    		for (int suit = 0; suit < mSuits.length; suit++) {    				
    			
    			for (int card = 1; card < 14; card++) {
    				
    				if (card == 1) {
    					
    					value = "A";
    					isAce = true;
    					isFaceCard = false;    							
    					
    				} else if (card == 11) {
    					
    					value = "J";
    					
    				} else if (card == 12) {
    					
    					value = "Q";
    					
    				} else if (card == 13) {
    					
    					value = "K";
    					
    				} else {
    					
    					value = String.valueOf(card);
    					isFaceCard = false;
    					
    				}
    				
    				Card newCard = new Card(suit+1, mSuits[suit], value, isFaceCard, isAce);
    				cards.add(newCard);
    			}
    			
    		}
    	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
