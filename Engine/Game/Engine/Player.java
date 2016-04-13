package Engine;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class Player {
	public Game game = null;
	public String name = null;
	public byte actions = 0;
	public byte buys = 0;
	public int coins = 0;
	public List<Card> deck = new ArrayList<Card>();
	public List<Card> hand = new ArrayList<Card>();
	public List<Card> discardPile = new ArrayList<Card>();
	
	// Player card actions
	public void shuffleDiscardPile() {
		Collections.shuffle(discardPile);
	}
	
	public Card[] getDeckCards(int amount) {
		Card[] output = new Card[amount];
		
		if (deck.size() >= amount) {
			for (int i = 0; i < amount; i++) {
				output[i] = deck.get(i);
				deck.remove(i);
			}
		}
		else {
			deck.toArray(output);
		}
		return output;
	}
	
	public void transferCards(List<Card> source, List<Card> destination, int amount, boolean removeSource) throws Exception {
		for (int i = 0; i < amount; i++) {
			destination.add(source.get(i));
			if (removeSource) { source.remove(i); }
		}
	}
	
	public void transferCards(List<Card> source, List<Card> destination, Card[] cards, boolean removeSource) throws Exception {
		int sourceIndex = 0;
		for (int i = 0; i < cards.length; i++) {
			sourceIndex = source.indexOf(cards[i]);
			destination.add(source.get(sourceIndex));
			if (removeSource) { source.remove(sourceIndex); }
		}
	}
	
	public Player(String name) {
		this.name = name;
	}
}