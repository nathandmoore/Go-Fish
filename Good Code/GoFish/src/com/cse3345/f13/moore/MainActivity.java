package com.cse3345.f13.moore;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Drawable[] mSuits;
	private Drawable[] mFaces;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
				Log.d("NM","Line 25");
        Resources res = getResources();
        
        /** Begin Game Logic */
        		Log.d("NM","Line 29");
        //get suit drawables
        TypedArray suitsIDs = res.obtainTypedArray(R.array.cardSuits);
        mSuits = new Drawable[suitsIDs.length()];
        for (int index = 0; index < suitsIDs.length(); index++) {
        	mSuits[index] = suitsIDs.getDrawable(index);
        }
        		Log.d("NM","Line 36");
        //get face card drawables
        TypedArray faceCardIDs = res.obtainTypedArray(R.array.faceCards);
        mFaces = new Drawable[faceCardIDs.length()];
        for (int index = 0; index < faceCardIDs.length(); index++) {
        	mFaces[index] = faceCardIDs.getDrawable(index);
        }
        		Log.d("NM","Line 43");
        //create and shuffle deck and have the draw pile be the deck
        Deck deck = new Deck();
        		Log.d("NM","Line 46");
        deck.shuffleDeck();  
        		Log.d("NM","Line 48");
        deck.initializeDrawPile();
        		Log.d("NM","Line 50");
        //generate 3 AI controlled hands and 1 player hand (5 cards each)
        Player[] players = new Player[4];
        /*
         * Player[0] = human
         * Player[1]-[3] = AI
         */
        		Log.d("NM","Line 57");
        for (int p = 0; p < 4; p++) {
        			Log.d("GenPlayers","Line 59");
        	Player player = new Player();
        	players[p] = player;
        	
        	for (int card = 0; card < 5; card++) {
        				Log.d("GenPlayers","Line 59" + player);
        		players[p].hand.add(deck.draw());
        				Log.d("GenPlayers","Line 66");
        	}
        			Log.d("GenPlayers","Line 68");
        }
        		Log.d("NM","Line 70");
        TextView temp = (TextView) findViewById(R.id.temp);
        temp.setText(players[0].hand.get(0).rank);
        		Log.d("NM","Line 73");
        //begin the match
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
private class Card {
    	
    	/** Member Variables **/
    	public String rank;
    	public Drawable suitImg;
    	public int suit;
    	public boolean isFaceCard;
    	public boolean isAce;
    	public Drawable face;
    	
    	/** Constructor **/
    	public Card(int insuit, Drawable insuitImg, String inrank, boolean inisFaceCard, boolean inisAce) {
    		
    		suit = insuit;
    		suitImg = insuitImg;
    		rank = inrank;    
    		isFaceCard = inisFaceCard;
    		isAce = inisAce;
    		setFace();
    		
    	}
    	
    	/** Methods **/
    	private void setFace() {
    		Log.d("NM","Line 108");
    		if (isFaceCard) {
    			
    			if (rank.equals("K")) { //king
    				
    				if (suit == 1) { //hearts
    					
    					face = mFaces[0];
    					
    				} else if (suit == 2) { //clubs
    					
    					face = mFaces[2];
    					
    				} else if (suit == 3) { //diamonds
    					
    					face = mFaces[4];
    					
    				} else { //spades
    					
    					face = mFaces[6];
    					
    				}
    				
    			} else if (rank.equals("Q")) { //queen
    				
    				
    				
    				if (suit == 1) { //hearts
    					
    					face = mFaces[1];
    					
    				} else if (suit == 2) { //clubs
    					
    					face = mFaces[3];
    					
    				} else if (suit == 3) { //diamonds
    					
    					face = mFaces[5];
    					
    				} else { //spades
    					
    					face = mFaces[7];
    					
    				}
    				
    			} else if (rank.equals("J")) { //jack
    				
    				
    				
    				if (suit == 1 || suit == 3) { //hearts and diamonds: red
    					
    					face = mFaces[9];
    					
    				} else { //clubs and spades: black
    					
    					face = mFaces[8];
    					
    				}
    				
    			}
    			
    		}
    		
    	}
    	
    }
    
    private class Player {
    	
    	/** Member Variables **/
    	public ArrayList<Card> hand;
    	public ArrayList<Card> quartets; //sets of 4 matching: just lists what the rank of the card is
    	
    	/** Constructor **/
    	public Player() {
    		
    		hand = new ArrayList<Card>(5);
    		
    	}
    	
    }
    
    private class Deck {
    	
    	/** Member Variables **/
    	public ArrayList<Card> cards;
    	public ArrayList<Card> drawPile;
    	
    	/** Constructor **/
    	public Deck() {
    		
    		cards = new ArrayList<Card>(52);
    		drawPile = new ArrayList<Card>(52);
    		generateDeck();
    		
    	}
    	
    	/** Methods **/
    	public void generateDeck() {
    				Log.d("NM","Line 207");
    		String rank;
    		boolean isFaceCard = true;
    		boolean isAce = false;
    		Drawable face;
    		
    		for (int suit = 0; suit < mSuits.length; suit++) {    				
    			
    			for (int card = 1; card < 14; card++) {
    				Log.d("GenDeck","Line 207");
    				if (card == 1) {
    					
    					rank = "A";
    					isAce = true;
    					isFaceCard = false;    							
    					
    				} else if (card == 11) {
    					
    					rank = "J";
    					
    				} else if (card == 12) {
    					
    					rank = "Q";
    					
    				} else if (card == 13) {
    					
    					rank = "K";
    					
    				} else {
    					
    					rank = String.valueOf(card);
    					isFaceCard = false;
    					
    				}
    						Log.d("GenDeck","Line 241");
    				Card newCard = new Card(suit+1, mSuits[suit], rank, isFaceCard, isAce);
    				cards.add(newCard);
    						Log.d("GenDeck","Line 244");
    			}
    			
    		}
    	}
    	
    	public void shuffleDeck() {
    				Log.d("NM","Line 251");
    		Collections.shuffle(drawPile);
    		
    	}
    	
    	public Card draw() { //pops the next card on the drawPile
    				Log.d("GenPlayers","Line 257");
    		return drawPile.remove(drawPile.size()-1);
    		
    	}
    	
    	public void initializeDrawPile() { //creates the draw pile from the deck
    		
    		for (int i = 0; i < cards.size(); i++) {
    			
    			drawPile.add(cards.get(i));
    			
    		}
    		
    	}
    	
    }

}
