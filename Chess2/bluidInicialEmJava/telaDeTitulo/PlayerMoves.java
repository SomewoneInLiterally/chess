package bluidInicialEmJava.telaDeTitulo;

import java.util.List;
import java.util.ArrayList;

public class PlayerMoves {

    private List<String> moves;

    public PlayerMoves() {
        moves = new ArrayList<>();
    }

    public void addMove(String move) {
        moves.add(move);
    }

    public List<String> getMoves() {
        return moves;
    }

    public void displayMoves() {
        System.out.println("Jogadas do jogador:");
        for (String move : moves) {
            System.out.println(move);
        }
    }

}
