package com.cse3345.f13.moore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

//	private Bitmap[] mSuits;
//	private Bitmap[] mFaces;
	private int mId = 1;
	private Player[] mPlayers = new Player[4];
	private Deck mDeck;
	private String mRankSelected;
	public static final int PICK_CARD = 101;
	private boolean gameOn = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_main);
		
        Resources res = getResources();
        
        /** Begin Game Logic */

        //get suit drawables
//        TypedArray suitsIDs = res.obtainTypedArray(R.array.cardSuits);
//        mSuits = new Bitmap[suitsIDs.length()];
//        
//        for (int index = 0; index < suitsIDs.length(); index++){
//        	mSuits[index] = ((BitmapDrawable)suitsIDs.getDrawable(index)).getBitmap();
//        }
//        
//        Log.d("NDM","Line 46");
//        
//        //get face card drawables
//        TypedArray faceCardIDs = res.obtainTypedArray(R.array.faceCards);
//        mFaces = new Bitmap[faceCardIDs.length()];
//        
//        for (int index = 0; index < faceCardIDs.length(); index++){
//        	mFaces[index] = ((BitmapDrawable)faceCardIDs.getDrawable(index)).getBitmap();
//        }
        
        Log.d("NDM","Line 54");
        
        //create and shuffle deck and have the draw pile be the deck
        mDeck = new Deck();
        
        mDeck.initializeDrawPile();
        
        Log.d("NDM","Line 56");
        //generate 3 AI controlled hands and 1 player hand (5 cards each)
        
        initPlayers();       
        
        //add click listener to the player's card
        ImageView playerCards = (ImageView) findViewById(R.id.firsts_cards);
        playerCards.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// open new activity that shows the player's cards
				// player will select which card to ask for
				// the activity will close and return to main
				pickCard();
			}
			
		});
        
        //add click listener to the draw button
        Button draw = (Button) findViewById(R.id.draw);
        draw.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// open new activity that shows the player's cards
				// player will select which card to ask for
				// the activity will close and return to main
				drawCard(0);
			}
			
		});
        
        //click listener on end match button
        Button end = (Button) findViewById(R.id.end);
        end.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				endMatch();
				
			}
			
		});
        
        /* Begin the match */
        //while (gameOn) {
        //display user's cards
        pickCard();
        //check other player's cards and give the player any cards or go fish
        //goFish(0);
        //simulate the other players (for now, just do this automatically without user interaction)
        //takeAIsTurns();
        //let the player start another game
        //change end game button color til the click it?
        //}
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * Initializes 3 AIs and 1 human hands
	 */
	public void initPlayers() {
		
		/*
         * Player[0] = human
         * Player[1]-[3] = AI
         */
        for (int p = 0; p < 4; p++) {
        	
        	Player player = new Player();
        	mPlayers[p] = player;
        	Log.d("NDM","Line 149");
        	for (int card = 0; card < 5; card++) {
        		Log.d("NDM","Line 151");
        		mPlayers[p].hand.add(mDeck.draw());
        		Log.d("Player: " + p, "Card: " + mPlayers[p].hand.get(card).rank);
        		
        	}
        	Log.d("NDM","Line 156");
        	mPlayers[p].checkForQuartet(p);
        	Log.d("NDM","Line 158");		
        } 
		
	}
	
	/** 
	 * Sends the player to the activity that lets them view their cards and select one
	 */	
	public void pickCard() {
		
		ArrayList<PlayerCard> playerCards = new ArrayList<PlayerCard>(0);
		
		String rank;
		int suit;
		int isFace;
		int isAce;
		int face;
		
		for (int i = 0; i < mPlayers[0].hand.size(); i++) {
			
			rank = mPlayers[0].hand.get(i).rank;
			suit = mPlayers[0].hand.get(i).suit;
			isFace = mPlayers[0].hand.get(i).isFaceCard;
			isAce = mPlayers[0].hand.get(i).isAce;
			face = mPlayers[0].hand.get(i).face;
			
			PlayerCard pc = new PlayerCard(rank,suit,isFace,isAce,face);
			
			playerCards.add(pc);
			
		}
		
		Intent i = new Intent(this, CardViewer.class);
		i.putExtra("Hand", playerCards);
		startActivityForResult(i, PICK_CARD);
		
	}
	
	/** 
	 * Gets the card the player picked
	 */	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == PICK_CARD && resultCode == Activity.RESULT_OK) {
			
			mRankSelected = data.getStringExtra(CardViewer.CARD);
			Log.d("Picked Card","card = " + mRankSelected);
			goFish(0);
			
		}	
		
	}
	
	/** 
	 * Searches for the card picked by the player
	 * if it can't be found then it makes them draw from the pile
	 */
	public void goFish(int originator) {
		
		boolean noMatch = true;
		
		//search other players
		for (int player = 0; player < mPlayers.length; player++) {
			
			if (player != originator) { //skip whoever's turn it is
				
				for (int checkAgainst = 0; checkAgainst < mPlayers[player].hand.size(); checkAgainst++) { //check other player's cards
					
					if (mRankSelected.equals(mPlayers[player].hand.get(checkAgainst).rank)) { //if match
						
						noMatch = false;
						//remove the card from whosever hand has it and add it to the requester
						mPlayers[originator].hand.add(mPlayers[player].hand.remove(checkAgainst)); 
						Log.d("New Card for Player " + originator,"Rank: " + mRankSelected);
						
					}
					
				}
				
			}
			
		}
		
		if (noMatch) //if no match go fish!			
			drawCard(originator);
		
	}
	
	/** 
	 * Draws a card from the pile
	 */	
	public void drawCard(int originator) {
		
		mPlayers[originator].hand.add(mDeck.draw());
		Log.d("New Card for Player " + originator,"Rank: " + mPlayers[originator].hand.get(mPlayers[originator].hand.size() - 1).rank);
		
	}
	
	/**
	 * Resets the game
	 */	
	public void endMatch() {
		
		for (int i = 0; i < mPlayers.length; i++) {
			
			mPlayers[i].clear();
			
		}
		
		mDeck.initializeDrawPile();
		initPlayers();
		
	}
	
	@SuppressWarnings("serial")
	private class Card implements Serializable {
    	
    	/**
		 * 
		 */
		//private static final long serialVersionUID = 1;
		/** Member Variables **/
    	public String rank;
    	//public Bitmap suitImg;
    	public int suit;
    	public int isFaceCard;
    	public int isAce;
    	//public Bitmap face;
    	public int face;
    	
    	/** Constructor **/
    	public Card(int insuit, String inrank, int inisFaceCard, int inisAce) {
    		
    		suit = insuit;
    		//suitImg = insuitImg;
    		rank = inrank;    
    		isFaceCard = inisFaceCard;
    		isAce = inisAce;
    		setFace();
    		
    	}
    	
    	/** Methods **/
    	private void setFace() {
    		Log.d("NM","Line 108");
    		if (isFaceCard == 1) {
    			
    			if (rank.equals("K")) { //king
    				
    				if (suit == 1) { //hearts
    					
    					face = 0;
    					
    				} else if (suit == 2) { //clubs
    					
    					face = 2;
    					
    				} else if (suit == 3) { //diamonds
    					
    					face = 4;
    					
    				} else { //spades
    					
    					face = 6;
    					
    				}
    				
    			} else if (rank.equals("Q")) { //queen
    				
    				
    				
    				if (suit == 1) { //hearts
    					
    					face = 1;
    					
    				} else if (suit == 2) { //clubs
    					
    					face = 3;
    					
    				} else if (suit == 3) { //diamonds
    					
    					face = 5;
    					
    				} else { //spades
    					
    					face = 7;
    					
    				}
    				
    			} else if (rank.equals("J")) { //jack
    				
    				
    				
    				if (suit == 1 || suit == 3) { //hearts and diamonds: red
    					
    					face = 9;
    					
    				} else { //clubs and spades: black
    					
    					face = 8;
    					
    				}
    				
    			}
    			
    		}
    		
    	}
    	
    } //End Card//
    
	
    @SuppressWarnings("serial")
	private class Player implements Serializable {
    	
    	/** Member Variables **/
    	public ArrayList<Card> hand;
    	public ArrayList<Card> quartets; //sets of 4 matching: just lists what the rank of the card is
    	
    	/** Constructor **/
    	public Player() {
    		
    		hand = new ArrayList<Card>(0);
    		quartets = new ArrayList<Card>(0);
    		
    	}
    	
    	public void clear() {
    		
    		hand.clear();
    		quartets.clear();
    		
    	}
    	
    	
    	//there cannot be more than 4 of any rank card since there are only 4 suits and one card of each rank per suit
    	public void checkForQuartet(int originator) {
    		    		
    		for (int card = 0; card < hand.size() - 1; card++) { //go through hand
    			
    			int[] anyMatches = new int[3]; //store indices of matching cards
        		int counter = 0; //count number of matches
        		//reset the abover for each card
    			
    			for (int next = card + 1; next < hand.size(); next++) { //check for matches
    				
    				if (hand.get(card).rank.equals(hand.get(next).rank)) {
    					
    					anyMatches[counter] = next;
    					counter++;
    					
    				}
    				
    			}
    			 
    			if (counter == 3) { //if 3 matching cards found
 					
 					for (int i = 0; i < 3; i++) {
 						
 						quartets.add(hand.remove(anyMatches[i]));
 						
 					} 					

					quartets.add(hand.remove(card));
					card--; //because a card was just removed, the size of the hand is 1 less
					
					//also need to update the Quartets counter on the main screen					
					int numQuarts = quartets.size() - 1;
					
					if (originator == 0) { //is player
						
						TextView quarts = (TextView) findViewById(R.id.firsts_quartets);
						quarts.setText("Quartets: " + numQuarts);
						
					} else if (originator == 1) { //player 2
						
						TextView quarts = (TextView) findViewById(R.id.seconds_quartets);
						quarts.setText("Quartets: " + numQuarts);
						
					} else if (originator == 2) { //player 3
						
						TextView quarts = (TextView) findViewById(R.id.thirds_quartets);
						quarts.setText("Quartets: " + numQuarts);
						
					} else { //player 4
						
						TextView quarts = (TextView) findViewById(R.id.fourths_quartets);
						quarts.setText("Quartets: " + numQuarts);
						
					}
 					
 				}
    			 
    		}
    		
    	}
    	
    } //End Player//
    
    private class Deck {
    	
    	/** Member Variables **/
    	public ArrayList<Card> cards;
    	public ArrayList<Card> drawPile;
    	
    	/** Constructor **/
    	public Deck() {
    		
    		cards = new ArrayList<Card>(0);
    		drawPile = new ArrayList<Card>(0);
    		generateDeck();
    		
    	}
    	
    	/** Methods **/
    	public void generateDeck() {
    				Log.d("NM","Line 207");
    		String rank;
    		int isFaceCard;
    		int isAce;
    		
    		for (int suit = 0; suit < 4; suit++) {    				
    			
    			for (int card = 1; card < 14; card++) {
    				Log.d("GenDeck","Line 207");
    				if (card == 1) {
    					
    					rank = "A";
    					isAce = 1;
    					isFaceCard = 0;    							
    					
    				} else if (card == 11) {
    					
    					rank = "J";
    					isFaceCard = 1;
    					isAce = 0;
    					
    				} else if (card == 12) {
    					
    					rank = "Q";
    					isFaceCard = 1;
    					isAce = 0;
    					
    				} else if (card == 13) {
    					
    					rank = "K";
    					isFaceCard = 1;
    					isAce = 0;
    					
    				} else {
    					
    					rank = String.valueOf(card);
    					isFaceCard = 0;
    					isAce = 0;
    					
    				}
    						Log.d("GenDeck","Line 241");
    				Card newCard = new Card(suit+1, rank, isFaceCard, isAce);
    				cards.add(newCard);
    						Log.d("GenDeck","Line 244");
    			}
    			
    		}
    	}
    	
    	public void shuffleDeck() {
    				Log.d("NM","Line 251");
    		Random r = new Random( System.currentTimeMillis() );
    		
    		Collections.shuffle(cards,r);
    		
    	}
    	
    	public Card draw() { //pops the next card on the drawPile
    				Log.d("GenPlayers","Line 257");
    		return drawPile.remove(drawPile.size()-1);
    		
    	}
    	
    	public void initializeDrawPile() { //creates the draw pile from the deck
    		
    		shuffleDeck();
    		
    		for (int i = 0; i < cards.size(); i++) {
    			
    			drawPile.add(cards.get(i));
    			
    		}
    		
    	}
    	
    } //End Deck//

} /*/End Code/*/
