package game;

import hashMap.ShipHashTable;
import players.Player;
import ships.Ship;
import java.util.Scanner;

public class Game {
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private boolean gameOver;
    private Scanner scanner;
    private ShipHashTable shipHashTable;

    public Game() {
        this.scanner = new Scanner(System.in);
        this.shipHashTable = new ShipHashTable();
        this.gameOver = false;
        initializeGame();
    }

    private void initializeGame() {
        System.out.println("=== CONFIGURACIÓN DEL JUEGO ===");

        // Configuración del Jugador 1
        System.out.println("\nJugador 1:");
        this.player1 = new Player("Jugador 1");
        configurePlayerFleet(player1);

        // Configuración del Jugador 2
        System.out.println("\nJugador 2:");
        this.player2 = new Player("Jugador 2");
        configurePlayerFleet(player2);

        this.currentPlayer = player1; // El jugador 1 comienza
    }

    private void configurePlayerFleet(Player player) {
        System.out.println("Configura tus barcos (máximo 3):");

        int shipCount = 0;
        while (shipCount < 3) {
            System.out.println("\nBarco #" + (shipCount + 1));
            System.out.println("Tipos disponibles:");
            System.out.println("1. Acorazado (tamaño 5)");
            System.out.println("2. Fragata (tamaño 3)");
            System.out.println("3. Canoa (tamaño 1)");
            System.out.print("Selecciona el tipo de barco (1-3): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            System.out.print("Nombre del barco: ");
            String name = scanner.nextLine();

            System.out.print("Nivel del barco: ");
            int level = scanner.nextInt();

            Ship ship = createShip(choice, shipCount + 1, name, level);
            if (ship != null) {
                player.getFleet().addShip(ship);
                shipHashTable.addShip(ship);
                shipCount++;
            } else {
                System.out.println("Selección inválida. Intenta nuevamente.");
            }
        }
    }

    private Ship createShip(int type, int number, String name, int level) {
        switch (type) {
            case 1:
                return new Battleship(number, name, "Battleship", level);
            case 2:
                return new Frigate(number, name, "Frigate", level);
            case 3:
                return new Canoe(number, name, "Canoe", level);
            default:
                return null;
        }
    }

    public void startGame() {
        System.out.println("\n=== COMIENZA EL JUEGO ===");

        while (!gameOver) {
            playTurn();
            checkGameOver();
            switchPlayer();
        }

        endGame();
    }

    private void playTurn() {
        System.out.println("\nTurno de " + currentPlayer.getName());

        // Mostrar tablero del oponente (solo impactos)
        Player opponent = (currentPlayer == player1) ? player2 : player1;
        opponent.getBoard().displayOpponentView();

        // Ataque
        System.out.print("Coordenada X para atacar: ");
        int x = scanner.nextInt();
        System.out.print("Coordenada Y para atacar: ");
        int y = scanner.nextInt();

        boolean hit = opponent.getBoard().receiveAttack(x, y);
        if (hit) {
            System.out.println("¡Impacto!");
        } else {
            System.out.println("¡Agua!");
        }
    }

    private void checkGameOver() {
        if (!player1.getFleet().hasActiveShips()) {
            gameOver = true;
            System.out.println(player2.getName() + " ha ganado!");
        } else if (!player2.getFleet().hasActiveShips()) {
            gameOver = true;
            System.out.println(player1.getName() + " ha ganado!");
        }
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    private void endGame() {
        System.out.println("\n=== FIN DEL JUEGO ===");
        scanner.close();
    }

    // Getters para pruebas
    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}