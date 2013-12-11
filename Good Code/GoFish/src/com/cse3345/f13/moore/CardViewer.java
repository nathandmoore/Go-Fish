package com.cse3345.f13.moore;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cse3345.f13.moore.MainActivity.Card;

public class CardViewer extends ListActivity {
	
	public static final String CARD = "cardPickedFromActivity:CardViewer";
	private String mCard;
	private ArrayList<Card> mHand;
	private CardAdapter mCardAdapter;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		/** 
		 *	On something selected or close:	*
		 	
		 	mCard = rank of card selected
		 	
		 	Intent i = new Intent();
			i.putExtra(mCard);
			setResult(Activity.RESULT_OK,i);
			finish(); //close activity
		
		 */
		
		Intent i = getIntent();
		mHand = (ArrayList<Card>) i.getSerializableExtra("Hand");
		
		mCardAdapter = new CardAdapter(this,
				R.layout.view_cards,
				mHand);
		/** Set the handle */
		setListAdapter(mCardAdapter);
	}
	
	@Override
	public void onListItemClick(ListView listView, View view, int position, long id) {
		
		mCard = mCardAdapter.getItem(position).rank;
			 	
		Intent i = new Intent();
		i.putExtra(CARD, mCard);
		setResult(Activity.RESULT_OK,i);
		finish(); //close activity
	}
	
	private class CardAdapter extends ArrayAdapter<Card> {
		
		private final Context context;
		private final List<Card> hand;
		private final int resource;
		
		public CardAdapter(Context context, int resource, List<Card> hand) {
			
			super(context, resource, hand);
			
			this.context = context;
			this.hand = hand;
			this.resource = resource;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			View cardView;
			
			if (convertView != null) {		
				
				cardView = convertView;	
				
			} else {
			
				LayoutInflater inflater = (LayoutInflater) context
				        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				cardView = inflater.inflate(R.layout.view_cards, parent, false);
			}
			
			//do work
			
			
			 return cardView;
		}
		
	}
	
}
