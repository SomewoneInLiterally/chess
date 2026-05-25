package bluidInicialEmJava.telaDeTitulo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CardSystem {

    private static final Random RANDOM = new Random();
    private static final List<Card> ALL_CARDS = createCardPool();

    private static List<Card> createCardPool() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card("Re-roll", "Re-rolar as cartas da mão (uma vez por turno)."));
        cards.add(new Card("Dois Movimentos", "Faça 2 movimentos em um turno."));
        cards.add(new Card("Três Movimentos", "Faça 3 movimentos em um turno."));
        cards.add(new Card("Quatro Movimentos", "Faça 4 movimentos em um turno."));
        cards.add(new Card("Cinco Movimentos", "Faça 5 movimentos em um turno."));
        cards.add(new Card("Seis Movimentos", "Faça 6 movimentos em um turno."));
        cards.add(new Card("Reverse", "Troque a posição de uma peça já movida no tabuleiro."));
        cards.add(new Card("Block", "Bloqueie o oponente e impeça um movimento no próximo turno."));
        cards.add(new Card("Time-Back", "Desfaça o último movimento."));
        cards.add(new Card("Mutação", "Mude um peão para qualquer outra peça."));
        cards.add(new Card("Túmulo", "Reviva uma peça capturada."));
        return cards;
    }

    public static List<Card> drawRandomCards(int count) {
        List<Card> copy = new ArrayList<>(ALL_CARDS);
        Collections.shuffle(copy, RANDOM);
        return new ArrayList<>(copy.subList(0, Math.min(count, copy.size())));
    }
}


