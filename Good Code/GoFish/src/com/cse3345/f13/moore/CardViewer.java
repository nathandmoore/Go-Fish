package com.cse3345.f13.moore;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CardViewer extends ListActivity {
	
	public static final String CARD = "cardPickedFromActivity:CardViewer";
	private String mCard;
	private CardAdapter mCardAdapter;
	private Bitmap[] mSuits;
	private Bitmap[] mFaces;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		Log.d("NDM2","Line 35");
		
		//get suit bitmaps
		Resources res = getResources();
        TypedArray suitsIDs = res.obtainTypedArray(R.array.cardSuits);
        mSuits = new Bitmap[suitsIDs.length()];
        
        for (int index = 0; index < suitsIDs.length(); index++){
        	mSuits[index] = ((BitmapDrawable)suitsIDs.getDrawable(index)).getBitmap();
        }
		
        //get face card bitmaps
        TypedArray faceCardIDs = res.obtainTypedArray(R.array.faceCards);
        mFaces = new Bitmap[faceCardIDs.length()];
        
        for (int index = 0; index < faceCardIDs.length(); index++){
        	mFaces[index] = ((BitmapDrawable)faceCardIDs.getDrawable(index)).getBitmap();
        }
		
		Intent i = getIntent();
		ArrayList<PlayerCard> Hand = (ArrayList<PlayerCard>) i.getSerializableExtra("Hand");
		Log.d("NDM2","Array size: " + Hand.size());
		mCardAdapter = new CardAdapter(this,
				R.layout.view_cards,
				Hand);
		/** Set the handle */
		setListAdapter(mCardAdapter);
	}
	
	@Override
	public void onListItemClick(ListView listView, View view, int position, long id) {
		
		Log.d("NDM2","HEREERERE " + position);
		
		mCard = mCardAdapter.getItem(position).rank;
		
		Log.d("NDM2","HEREERERE line 74");
		
		Intent i = new Intent();
		i.putExtra(CARD, mCard);
		setResult(Activity.RESULT_OK,i);
		finish(); //close activity
	}
	
	private class CardAdapter extends ArrayAdapter<PlayerCard> {
		
		private final Context context;
		private final List<PlayerCard> hand;
		private final int resource;
		
		public CardAdapter(Context context, int resource, List<PlayerCard> hand) {
			
			super(context, resource, hand);
			Log.d("NDM2","Hand size: " + hand.size());
			this.context = context;
			this.hand = hand;
			Log.d("NDM2","This.Hand size: " + this.hand.size());

//			for(int i = 0; i < hand.size(); i++) {
//				
//				this.hand.add(hand.get(i));
//				
//			}
			
			this.resource = resource;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Log.d("NDM2","Position " + position);
			View cardView;
			
			if (convertView != null) {		
				
				cardView = convertView;	
				
			} else {
			
				LayoutInflater inflater = (LayoutInflater) context
				        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				cardView = inflater.inflate(R.layout.view_cards, parent, false);
			}
			
			//Set Rank
			Log.d("NDM2","Hand size again: " + hand.size());
			TextView rank = (TextView) cardView.findViewById(R.id.rank);
			TextView rank2 = (TextView) cardView.findViewById(R.id.rank2);
			String r = hand.get(position).rank;
			rank.setText(r);
			rank2.setText(r);
			Log.d("NDM2","Line 125");
			//Set Suit
			ImageView suit = (ImageView) cardView.findViewById(R.id.suit);
			ImageView suit2 = (ImageView) cardView.findViewById(R.id.suit2);
			suit.setImageBitmap(mSuits[hand.get(position).suit - 1]);
			suit2.setImageBitmap(mSuits[hand.get(position).suit - 1]);
			
			//Set face
			TextView face = (TextView) cardView.findViewById(R.id.face);
			int f = hand.get(position).face;
			if (hand.get(position).isFaceCard == 1) {
				
				Drawable x = new BitmapDrawable(mFaces[f]);
				face.setBackgroundDrawable(x);
				face.setText(" ");
				
			} else {
				
				face.setBackgroundColor(0xFFFFFFFF);
				if (r.equals("10")) {
					
					face.setText("X");
					
				} else {
					
					face.setText(r);
					
				}
				
				
			}
			
			 return cardView;
		}
		
	}
	
}
