package bluidInicialEmJava.telaDeTitulo;

public class LoadingScreen4 {

    public static void main(String[] args) {
        System.out.println("Looking for match...");
        // Simulate loading process
        try {
            Thread.sleep(8000); // 8 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Match found!");
    }

}
