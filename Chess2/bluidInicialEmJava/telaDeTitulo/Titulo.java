package bluidInicialEmJava.telaDeTitulo;

public class Titulo {

    public static void main(String[] args) {

        System.out.println(repeat('=', 120));
        System.out.println("  ____________         ___________          _____________       _____________      _____________    \r\n" + //
                        "  |          |        |           |         |           |       |           |      |           |\r\n" + //
                        "   |_      _|          |_       _|           |_       _|         |_       _|        |_       _|\r\n" + //
                        "     |     |             |     |               |     |             |     |            |     |\r\n" + //
                        "    |       |           |       |             |       |           |       |          |       |\r\n" + //
                        "   |         |         |         |           |         |         |         |        |         |\r\n" + //
                        "  |           |       |           |         |           |       |           |      |           |");
                        System.out.println();
                        System.out.println(repeat(' ', 8) + "C" + repeat(' ', 19) + "H" + repeat(' ', 21) + "E" + repeat(' ', 19) + "S" + repeat(' ', 19) + "S" + repeat(' ', 19));
                        System.out.println();
                        System.out.println();
                        System.out.println(repeat(' ', 50) + "2");
                        System.out.println();
                        System.out.println(repeat('=', 120));
                        System.out.println();

    }

    private static String repeat(char ch, int count) {
        StringBuilder sb = new StringBuilder(count);
        for (int i = 0; i < count; i++) {
            sb.append(ch);
        }
        return sb.toString();
    }
}