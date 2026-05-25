// ...existing code...
package bluidInicialEmJava.telaDeTitulo;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Arrays;

public class VetorPossibleMovesInAChessGame {

    private List<String> possibleMoves;

    public VetorPossibleMovesInAChessGame() {
        this.possibleMoves = new ArrayList<>();
    }

    public void addMove(String move) {
        possibleMoves.add(move);
    }

    public List<String> getMoves() {
        return possibleMoves;
    }

    // novo: adiciona várias jogadas de uma vez
    public void addMoves(Collection<String> moves) {
        if (moves == null) return;
        possibleMoves.addAll(moves);
    }

    // novo: limpa a lista de jogadas
    public void clearMoves() {
        possibleMoves.clear();
    }

    // novo: formata UCI "c4f3" ou "c4f3" (4 chars) para "c4 - f3"
    public static String formatMove(String uciMove) {
        if (uciMove == null) return null;
        String s = uciMove.trim();
        if (s.length() >= 4) {
            return s.substring(0, 2) + " - " + s.substring(2, 4);
        }
        return s;
    }
}

class DemoAddMoves {
    public static void main(String[] args) {
        VetorPossibleMovesInAChessGame moves = new VetorPossibleMovesInAChessGame();

        // gera todas as combinações de casas (ex: a1a1 ... h8h8) e adiciona
        moves.addMoves(generateAllSquarePairs());

        // escolher aleatório com Bot1AI
        Bot1AI ai = new Bot1AI();
        String escolha = ai.chooseRandom(moves.getMoves());
        
        //se quiser:
        // System.out.println("Total de movimentos gerados: " + moves.getMoves().size());

        // imprime no formato "c4 - f3"
        System.out.println("AI escolheu: " + VetorPossibleMovesInAChessGame.formatMove(escolha));
    }

    // gera todas as 64*64 = 4096 combinações de pares de casas em notação UCI (origem+destino)
    private static List<String> generateAllSquarePairs() {
        List<String> list = new ArrayList<>(64 * 64);
        String files = "abcdefgh";
        String ranks = "12345678";
        for (int f1 = 0; f1 < 8; f1++) {
            for (int r1 = 0; r1 < 8; r1++) {
                for (int f2 = 0; f2 < 8; f2++) {
                    for (int r2 = 0; r2 < 8; r2++) {
                        String move = new StringBuilder(4)
                            .append(files.charAt(f1))
                            .append(ranks.charAt(r1))
                            .append(files.charAt(f2))
                            .append(ranks.charAt(r2))
                            .toString();
                        list.add(move);
                    }
                }
            }
        }
        return list;
    }
}
// ...existing code...