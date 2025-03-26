import game.Game;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain = true;

        System.out.println("¡BIENVENIDO A BATALLA NAVAL!");
        System.out.println("============================\n");

        while (playAgain) {
            try {
                // Inicializar nuevo juego
                Game game = new Game();

                // Comenzar el juego
                game.startGame();

                // Preguntar si desea jugar nuevamente
                System.out.print("\n¿Quieres jugar otra vez? (s/n): ");
                String choice = scanner.nextLine().trim().toLowerCase();

                playAgain = choice.equals("s");

            } catch (InputMismatchException e) {
                System.out.println("Error: Entrada inválida. Reiniciando el juego...");
                scanner.nextLine(); // Limpiar buffer
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
                playAgain = false;
            }
        }

        System.out.println("\n¡Gracias por jugar! ¡Hasta la próxima!");
        scanner.close();
    }
}