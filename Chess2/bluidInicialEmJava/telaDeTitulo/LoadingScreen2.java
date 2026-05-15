package bluidInicialEmJava.telaDeTitulo;

public class LoadingScreen2 {

    public static void main(String[] args) {
        System.out.println("Loading game...");
        // Simulate loading process
        try {
            Thread.sleep(2000); // 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Loading complete!");
    }

}
