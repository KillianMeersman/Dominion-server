package Core;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class Player {

    private int id;
    private String name = null;
    private byte actions = 1;
    private byte buys = 1;
    private byte coins = 0;
    private PlayerPhase phase = PlayerPhase.PHASE_BUY;
    protected ArrayList<Card> deck = new ArrayList<>();
    protected ArrayList<Card> hand = new ArrayList<>();
    protected ArrayList<Card> discard = new ArrayList<>();
    public boolean inActionMode = false;
    private int turns = 0;

    protected void addTurn() {
        turns++;
    }
    
    protected int getTurns() {
        return turns;
    }
    
    protected void addAction(int amount) {
        actions += amount;
    }

    protected void addBuy(int amount) {
        buys += amount;
    }
    
    protected void addCoins(int amount){
        coins += amount;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }
    
    public int getActions() {
        return actions;
    }

    public int getBuys() {
        return buys;
    }
    
    public int getCoins() {
        return coins;
    }
    
    public int getTreasureWorth(PlayerPlace source) {
        int out = 0;
        for (Card card : getTreasureCards(PlayerPlace.PLACE_HAND)) {
            out += ((TreasureCard) card).getValue();
        }
        return out;
     }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
    
    private int addTreasureCoins() {
        int c = 0;
        for (Card card : getTreasureCards(PlayerPlace.PLACE_HAND)) {
            c += ((TreasureCard)card).getValue();
        }
        return c;
    }

    public PlayerPhase getPhase() {
        return phase;
    }

    public ArrayList<Card> getActionCards(PlayerPlace source) {
        ArrayList<Card> cards = resolvePlayerPlace(source);
        ArrayList<Card> out = new ArrayList<>();
        for (Card card : cards) {
            if (card instanceof ActionCard) {
                out.add(card);
            }
        }
        return out;
        
    }

    public ArrayList<Card> getTreasureCards(PlayerPlace source) {
        ArrayList<Card> cards = resolvePlayerPlace(source);
        ArrayList<Card> out = new ArrayList<>();
        for (Card card : cards) {
            if (card instanceof TreasureCard) {
                out.add((TreasureCard) card);
            }
        }
        return out;
    }

    public ArrayList<Card> getVictoryCards(PlayerPlace source) {
        ArrayList<Card> cards = resolvePlayerPlace(source);
        ArrayList<Card> out = new ArrayList<>();
        for (Card card : cards) {
            if (card instanceof VictoryCard) {
                out.add((VictoryCard) card);
            }
        }
        return out;
    }

    protected void drawFromDeck(int amount) {
        if (amount > deck.size() && discard.size() >= amount) {
            Collections.shuffle(discard);
            int size = amount - deck.size();
            for (int i = 0; i < size; i++) {
                Card.transferCard(discard.get(0), discard, deck, true, false);
            }
        }
        else if (discard.size() < amount && discard.size() > 0) {
            Collections.shuffle(discard);
            Card.transferCards(discard, deck, true);
        }
        for (int i = 0; i < amount; i++) {
                hand.add(deck.get(0));
                deck.remove(0);
        }
    }

    protected ArrayList<Card> resolvePlayerPlace(PlayerPlace place) {
        switch (place) {
            case PLACE_HAND:
                return hand;
            case PLACE_DECK:
                return deck;
            default:
                return discard;
        }
    }

    public void nextPhase() {
        switch (phase) {
            case PHASE_ACTION:
                if (buys > 0 && addTreasureCoins() > 0) {
                    phase = PlayerPhase.PHASE_BUY;
                }
                else {
                    phase = PlayerPhase.PHASE_CLEANUP;
                }
                break;
            case PHASE_BUY:
                phase = PlayerPhase.PHASE_CLEANUP;
                break;
            case PHASE_CLEANUP:
                if (actions > 0 && hasActionCards(PlayerPlace.PLACE_HAND)) {
                    phase = PlayerPhase.PHASE_ACTION;
                }
                else {
                    phase = PlayerPhase.PHASE_BUY;
                }
                break;
        }
    }

    public boolean hasActionCards(PlayerPlace place) {
        for (Card card : resolvePlayerPlace(place)) {
            if (card instanceof ActionCard) {
                return true;
            }
        }
        return false;
    }

    protected Player(int id, String name) {
        this.id = id;
        this.name = name;

        for (Card card : CardRepository.getInstance().getStartingCards()) {
            for (int i = 0; i < card.getStartingAmount(); i++) {
                deck.add(card);
            }
        }

        Collections.shuffle(deck);
        drawFromDeck(5);
    }
    
    protected void resetParameters() {
        buys = 1;
        actions = 1;
        coins = 0;
    }
}