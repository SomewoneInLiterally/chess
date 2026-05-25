package bluidInicialEmJava.telaDeTitulo;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private final List<Card> hand;
    private final List<Card> playedCards;
    private final int maxHandSize = 3;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.playedCards = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void drawHand() {
        hand.clear();
        hand.addAll(CardSystem.drawRandomCards(maxHandSize));
    }

    public boolean playCard(int cardIndex) {
        if (cardIndex < 0 || cardIndex >= hand.size()) {
            return false;
        }
        Card card = hand.remove(cardIndex);
        playedCards.add(card);
        System.out.println("Carta lançada: " + card.getName() + " - " + card.getDescription());
        return true;
    }

    public void showHand() {
        System.out.println("Cartas em mão de " + name + ":");
        if (hand.isEmpty()) {
            System.out.println("  (nenhuma carta disponível)");
            return;
        }
        for (int i = 0; i < hand.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + hand.get(i));
        }
    }

    public void showPlayedCards() {
        System.out.println("Cartas já lançadas de " + name + ":");
        if (playedCards.isEmpty()) {
            System.out.println("  (nenhuma carta lançada ainda)");
            return;
        }
        for (int i = 0; i < playedCards.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + playedCards.get(i));
        }
    }
}
