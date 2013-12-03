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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Resources res = getResources();
        /** Begin Game Logic */
        //get suit drawables
        TypedArray suitsIDs = res.obtainTypedArray(R.array.cardSuits);
        BitmapDrawable[] suits = new BitmapDrawable[suitsIDs.length()];
        for (int index = 0; index < suitsIDs.length(); index++) {
        	suits[index] = (BitmapDrawable) suitsIDs.getDrawable(index);
        }
        Deck deck = new Deck(suits);
        
    }
    
    private class Card {
    	public String value;
    	public Drawable suit;
    	
    	public Card(Drawable s, String v) {
    		suit = s;
    		value = v;    		
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
    	public BitmapDrawable[] suits;
    	
    	public Deck(BitmapDrawable[] b) {
    		cards = new ArrayList<Card>(52);
    		suits = new BitmapDrawable[b.length];
    		System.arraycopy(b, 0, suits, 0, b.length);
    		generateDeck();
    	}
    	
    	private void generateDeck() {
    		
    		String value;
    		
    		for (int suit = 0; suit < suits.length; suit++) {    				
    			
    			for (int card = 1; card < 14; card++) {
    				
    				if (card == 1)
    					value = "A";
    				else if (card == 11)
    					value = "J";
    				else if (card == 12)
    					value = "Q";
    				else if (card == 13)
    					value = "K";
    				else
    					value = String.valueOf(card);
    				
    				Card newCard = new Card(suits[suit],value);
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
