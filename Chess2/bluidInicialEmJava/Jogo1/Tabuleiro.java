package bluidInicialEmJava.telaDeTitulo;

import java.util.Scanner;

public class Tabuleiro {

    private String[][] tabuleiro;
    private boolean whiteToMove = true; // controla o turno: true = branco

    public Tabuleiro() {
        tabuleiro = new String[8][8];
        inicializarTabuleiro();
    }

    private void inicializarTabuleiro() {
        // preencher com espaços
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tabuleiro[i][j] = " ";
            }
        }

        // peças pretas (rank 8 e 7)
        String[] blackBack = {"r", "n", "b", "q", "k", "b", "n", "r"};
        for (int j = 0; j < 8; j++) {
            tabuleiro[0][j] = blackBack[j];
            tabuleiro[1][j] = "p";
        }

        // peças brancas (rank 1 e 2)
        String[] whiteBack = {"R", "N", "B", "Q", "K", "B", "N", "R"};
        for (int j = 0; j < 8; j++) {
            tabuleiro[6][j] = "P";
            tabuleiro[7][j] = whiteBack[j];
        }
    }

    public void exibirTabuleiro() {
        // imprime com ranks à esquerda e files abaixo
        for (int i = 0; i < 8; i++) {
            int rank = 8 - i;
            System.out.print(rank + " ");
            for (int j = 0; j < 8; j++) {
                System.out.print("[" + tabuleiro[i][j] + "]");
            }
            System.out.println();
        }

        System.out.print("  ");
        for (char f = 'a'; f <= 'h'; f++) {
            System.out.print(" " + f + " ");
        }
        System.out.println();

        // mostrar legenda automaticamente ao exibir o tabuleiro
        mostrarLegenda();
    }

    public void mostrarLegenda() {

        System.out.println();
        System.out.println(new String(new char[63]).replace('\0', '='));
        System.out.println();
        System.out.println("nome das peças: ");
        System.out.println("Peças pretas: r, n, b, q, k, b, n, r");
        System.out.println("Peças brancas: R, N, B, Q, K, B, N, R");
        System.out.println("");
        System.out.println(new String(new char[63]).replace('\0', '-'));
        System.out.println("p = pawn, r = rook, n = knight, b = bishop, q = queen, k = king");
        System.out.println(new String(new char[63]).replace('\0', '-'));
        System.out.println("p = peão, r = torre, n = cavalo, b = bispo, q = rainha, k = rei");
        System.out.println(new String(new char[63]).replace('\0', '-'));

    }

    // helpers
    private boolean isWhite(String p) {
        return p != null && p.length() > 0 && Character.isUpperCase(p.charAt(0));
    }

    private boolean isBlack(String p) {
        return p != null && p.length() > 0 && Character.isLowerCase(p.charAt(0));
    }

    private boolean sameColor(String a, String b) {
        if (a == null || b == null) return false;
        if (a.equals(" ") || b.equals(" ")) return false;
        return (isWhite(a) && isWhite(b)) || (isBlack(a) && isBlack(b));
    }

    private boolean clearPathOnBoard(String[][] b, int r1, int c1, int r2, int c2) {
        int dr = Integer.compare(r2, r1);
        int dc = Integer.compare(c2, c1);
        int r = r1 + dr;
        int c = c1 + dc;
        while (r != r2 || c != c2) {
            if (!b[r][c].equals(" ")) return false;
            r += dr;
            c += dc;
        }
        return true;
    }

    private boolean isSquareAttacked(String[][] b, int row, int col, boolean byWhite) {
        // percorre todas as peças do atacante e verifica se alguma pode alcançar (row,col)
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                String p = b[r][c];
                if (p == null || p.equals(" ")) continue;
                if (byWhite && !isWhite(p)) continue;
                if (!byWhite && !isBlack(p)) continue;

                char pc = Character.toLowerCase(p.charAt(0));
                int dr = row - r;
                int dc = col - c;

                switch (pc) {
                    case 'p': {
                        int dir = isWhite(p) ? -1 : +1;
                        // pawn attacks diagonal
                        if (dr == dir && Math.abs(dc) == 1) return true;
                        break;
                    }
                    case 'n': {
                        if ((Math.abs(dr) == 2 && Math.abs(dc) == 1) || (Math.abs(dr) == 1 && Math.abs(dc) == 2)) return true;
                        break;
                    }
                    case 'b': {
                        if (Math.abs(dr) == Math.abs(dc) && Math.abs(dr) > 0) {
                            if (clearPathOnBoard(b, r, c, row, col)) return true;
                        }
                        break;
                    }
                    case 'r': {
                        if ((dr == 0 && dc != 0) || (dc == 0 && dr != 0)) {
                            if (clearPathOnBoard(b, r, c, row, col)) return true;
                        }
                        break;
                    }
                    case 'q': {
                        if ((Math.abs(dr) == Math.abs(dc) && Math.abs(dr) > 0) || (dr == 0 && dc != 0) || (dc == 0 && dr != 0)) {
                            if (clearPathOnBoard(b, r, c, row, col)) return true;
                        }
                        break;
                    }
                    case 'k': {
                        if (Math.max(Math.abs(dr), Math.abs(dc)) == 1) return true;
                        break;
                    }
                }
            }
        }
        return false;
    }

    private int[] findKing(String[][] b, boolean white) {
        String k = white ? "K" : "k";
        for (int r = 0; r < 8; r++) for (int c = 0; c < 8; c++) if (k.equals(b[r][c])) return new int[] { r, c };
        return null;
    }

    private boolean isKingInCheck(String[][] b, boolean white) {
        int[] pos = findKing(b, white);
        if (pos == null) return false; // sem rei (teórico), considerar não em cheque
        return isSquareAttacked(b, pos[0], pos[1], !white);
    }

    private String[][] copyBoard(String[][] src) {
        String[][] dst = new String[8][8];
        for (int r = 0; r < 8; r++) System.arraycopy(src[r], 0, dst[r], 0, 8);
        return dst;
    }

    private boolean isMovePseudoLegalOnBoard(String[][] b, int rFrom, int cFrom, int rTo, int cTo) {
        String piece = b[rFrom][cFrom];
        if (piece == null || piece.equals(" ")) return false;
        String dest = b[rTo][cTo];
        if (sameColor(piece, dest)) return false;

        int dr = rTo - rFrom;
        int dc = cTo - cFrom;
        char p = Character.toLowerCase(piece.charAt(0));

        switch (p) {
            case 'p': {
                int dir = isWhite(piece) ? -1 : +1;
                // avanço simples
                if (dc == 0 && dr == dir && dest.equals(" ")) return true;
                // avanço duplo
                int startRow = isWhite(piece) ? 6 : 1;
                if (dc == 0 && dr == 2 * dir && rFrom == startRow && b[rFrom + dir][cFrom].equals(" ") && dest.equals(" ")) return true;
                // captura diagonal
                if (Math.abs(dc) == 1 && dr == dir && !dest.equals(" ") && !sameColor(piece, dest)) return true;
                return false;
            }
            case 'n': return (Math.abs(dr) == 2 && Math.abs(dc) == 1) || (Math.abs(dr) == 1 && Math.abs(dc) == 2);
            case 'b': return (Math.abs(dr) == Math.abs(dc) && Math.abs(dr) > 0) && clearPathOnBoard(b, rFrom, cFrom, rTo, cTo);
            case 'r': return ((dr == 0 && dc != 0) || (dc == 0 && dr != 0)) && clearPathOnBoard(b, rFrom, cFrom, rTo, cTo);
            case 'q': return ((Math.abs(dr) == Math.abs(dc) && Math.abs(dr) > 0) || (dr == 0 && dc != 0) || (dc == 0 && dr != 0)) && clearPathOnBoard(b, rFrom, cFrom, rTo, cTo);
            case 'k': return Math.max(Math.abs(dr), Math.abs(dc)) == 1;
            default: return false;
        }
    }

    private boolean hasAnyLegalMove(boolean white) {
        // percorre todas as peças da cor e tenta todos os destinos; simula e verifica se rei fica em cheque
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                String p = tabuleiro[r][c];
                if (p == null || p.equals(" ")) continue;
                if (white && !isWhite(p)) continue;
                if (!white && !isBlack(p)) continue;

                for (int rt = 0; rt < 8; rt++) {
                    for (int ct = 0; ct < 8; ct++) {
                        if (!isMovePseudoLegalOnBoard(tabuleiro, r, c, rt, ct)) continue;
                        String[][] copy = copyBoard(tabuleiro);
                        copy[rt][ct] = copy[r][c];
                        copy[r][c] = " ";
                        // se o movimento deixar o rei não em cheque, é legal
                        if (!isKingInCheck(copy, white)) return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Verifica movimento legal básico e aplica.
     * Suporta: movimentos básicos de peões (1,2,captura e promoção automática para dama),
     * torres, bispos, rainhas, cavalos e rei (sem roque), captura legal, troca de turno.
     * Não implementa: roque, en passant, regras de xeque/xequemate avançadas.
     */
    public boolean aplicarMovimentoUCI(String uci) {
        if (uci == null) return false;
        String s = uci.trim().toLowerCase();
        if (s.length() < 4) return false;

        char f1 = s.charAt(0);
        char r1c = s.charAt(1);
        char f2 = s.charAt(2);
        char r2c = s.charAt(3);

        if (f1 < 'a' || f1 > 'h' || f2 < 'a' || f2 > 'h') return false;
        if (r1c < '1' || r1c > '8' || r2c < '1' || r2c > '8') return false;

        int colFrom = f1 - 'a';
        int rowFrom = 8 - (r1c - '0'); // row 0 = rank 8
        int colTo = f2 - 'a';
        int rowTo = 8 - (r2c - '0');

        if (rowFrom < 0 || rowFrom > 7 || rowTo < 0 || rowTo > 7) return false;

        String piece = tabuleiro[rowFrom][colFrom];
        if (piece == null || piece.equals(" ")) return false; // origem vazia

        // controla turno
        if (whiteToMove && !isWhite(piece)) return false;
        if (!whiteToMove && !isBlack(piece)) return false;

        String dest = tabuleiro[rowTo][colTo];
        // não pode capturar peça própria
        if (sameColor(piece, dest)) return false;

        // verifica pseudo-legalidade
        if (!isMovePseudoLegalOnBoard(tabuleiro, rowFrom, colFrom, rowTo, colTo)) return false;

        // simula movimento para ver se deixa o próprio rei em cheque
        String[][] copy = copyBoard(tabuleiro);
        copy[rowTo][colTo] = copy[rowFrom][colFrom];
        copy[rowFrom][colFrom] = " ";
        boolean movingWhite = isWhite(piece);
        if (isKingInCheck(copy, movingWhite)) return false; // movimento ilegal pois deixa rei em cheque

        // aplica movimento real (inclui promoção tratada antes)
        char p = Character.toLowerCase(piece.charAt(0));
        if (p == 'p') {
            int promoteRow = isWhite(piece) ? 0 : 7;
            tabuleiro[rowTo][colTo] = tabuleiro[rowFrom][colFrom];
            tabuleiro[rowFrom][colFrom] = " ";
            if (rowTo == promoteRow) {
                tabuleiro[rowTo][colTo] = isWhite(piece) ? "Q" : "q";
            }
        } else {
            tabuleiro[rowTo][colTo] = tabuleiro[rowFrom][colFrom];
            tabuleiro[rowFrom][colFrom] = " ";
        }

        // troca de turno
        whiteToMove = !whiteToMove;
        return true;
    }

    public static void main(String[] args) {
        Tabuleiro t = new Tabuleiro();
        Player whitePlayer = new Player("Branco");
        Player blackPlayer = new Player("Preto");
        PlayerMoves pm = new PlayerMoves();
        Scanner sc = new Scanner(System.in);

        System.out.println(new String(new char[63]).replace('\0', '-'));
        System.out.println("Digite movimentos no formato UCI (ex: e2e4). Digite 'sair' para encerrar.");
        System.out.println("Digite 'card 1', 'card 2' ou 'card 3' para lançar uma carta.");
        System.out.println(new String(new char[63]).replace('\0', '-'));

        boolean exitRequested = false;
        boolean gameEnded = false;

        while (!exitRequested && !gameEnded) {
            Player current = t.whiteToMove ? whitePlayer : blackPlayer;
            current.drawHand();
            boolean turnEnded = false;

            while (!turnEnded && !exitRequested && !gameEnded) {
                t.exibirTabuleiro();
                System.out.println("Turno: " + current.getName());
                current.showHand();
                System.out.println();
                current.showPlayedCards();
                System.out.println();
                System.out.print("Seu comando: ");
                String line = sc.nextLine();
                System.out.println(new String(new char[63]).replace('\0', '='));

                if (line == null) {
                    exitRequested = true;
                    break;
                }

                line = line.trim();

                if (line.equalsIgnoreCase("sair") || line.equalsIgnoreCase("exit")) {
                    exitRequested = true;
                    break;
                }
                if (line.isEmpty()) {
                    continue;
                }

                String lower = line.toLowerCase();
                if (lower.startsWith("card")) {
                    String[] parts = lower.split("\\s+");
                    if (parts.length >= 2) {
                        try {
                            int cardIndex = Integer.parseInt(parts[1]);
                            if (!current.playCard(cardIndex - 1)) {
                                System.out.println("Índice de carta inválido. Use 1, 2 ou 3.");
                            }
                        } catch (NumberFormatException ex) {
                            System.out.println("Formato de carta inválido. Use 'card 1', 'card 2' ou 'card 3'.");
                        }
                    } else {
                        System.out.println("Informe o número da carta: 'card 1', 'card 2' ou 'card 3'.");
                    }
                    System.out.println(new String(new char[63]).replace('\0', '-'));
                    continue;
                }

                boolean ok = t.aplicarMovimentoUCI(line);
                if (ok) {
                    pm.addMove(line);
                    System.out.println("Movimento aplicado: " + line);
                    System.out.println(new String(new char[63]).replace('\0', '-'));
                    turnEnded = true;

                    boolean nextIsWhite = t.whiteToMove;
                    boolean opponentHasMove = t.hasAnyLegalMove(nextIsWhite);
                    boolean opponentInCheck = t.isKingInCheck(t.tabuleiro, nextIsWhite);
                    if (!opponentHasMove) {
                        t.exibirTabuleiro();
                        if (opponentInCheck) {
                            boolean previousWhite = !t.whiteToMove;
                            String winner = previousWhite ? "Branco" : "Preto";
                            System.out.println("Xeque-mate! Vencedor: " + winner);
                        } else {
                            System.out.println("Empate por afogamento (stalemate).");
                        }
                        gameEnded = true;
                    } else {
                        if (opponentInCheck) {
                            System.out.println((nextIsWhite ? "Branco" : "Preto") + " está em xeque.");
                        }
                    }
                } else {
                    System.out.println("Movimento inválido, peça errada ou bloqueado: " + line);
                    System.out.println(new String(new char[63]).replace('\0', '-'));
                }
            }
        }

        sc.close();
        System.out.println("\nMovimentos do jogador salvos:");
        pm.displayMoves();
        System.out.println();
        whitePlayer.showPlayedCards();
        blackPlayer.showPlayedCards();
        System.out.println("Fim.");
    }
}