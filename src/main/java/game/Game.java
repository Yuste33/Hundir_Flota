package game;

import hashMap.ShipHashTable;
import players.Player;
import ships.Battleship;
import ships.Canoe;
import ships.Frigate;
import ships.Ship;
import java.util.InputMismatchException;
import java.util.Optional;
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

        // Configuración de los jugadores
        this.player1 = createPlayer("Jugador 1");
        this.player2 = createPlayer("Jugador 2");

        this.currentPlayer = player1; // El jugador 1 comienza
    }

    private Player createPlayer(String playerName) {
        System.out.println("\n" + playerName + ":");
        Player player = new Player();
        configurePlayerFleet(player);
        return player;
    }

    private void configurePlayerFleet(Player player) {
        System.out.println("Configura tus barcos (máximo 3):");

        int shipCount = 0;
        while (shipCount < 3) {
            try {
                System.out.println("\nBarco #" + (shipCount + 1));
                displayShipOptions();

                int choice = getIntInput("Selecciona el tipo de barco (1-3): ", 1, 3);
                scanner.nextLine(); // Limpiar buffer

                System.out.print("Nombre del barco: ");
                String name = scanner.nextLine().trim();

                int level = getIntInput("Nivel del barco: ", 1, 10);

                Ship ship = createShip(choice, shipCount + 1, name, level);
                if (ship != null) {
                    if (placeShipOnBoard(player, ship)) {
                        player.getFleet().addShip(ship);
                        shipHashTable.addShip(ship);
                        shipCount++;
                    } else {
                        System.out.println("No se pudo colocar el barco en el tablero. Intenta nuevamente.");
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor ingresa un número.");
                scanner.nextLine(); // Limpiar buffer
            }
        }
    }

    private Ship createShip(int choice, int number, String name, int level) {
        // Validación básica de parámetros
        if (name == null || name.trim().isEmpty()) {
            name = "Barco" + number; // Nombre por defecto
        }

        // Asegurar que el nivel esté en rango válido
        level = Math.max(1, Math.min(level, 10)); // Nivel entre 1 y 10

        switch (choice) {
            case 1: // Acorazado (tamaño 5)
                return new Battleship(number, name, level);
            case 2: // Fragata (tamaño 3)
                return new Frigate(number, name, level);
            case 3: // Canoa (tamaño 1)
                return new Canoe(number, name, level);
            default:
                System.out.println("Tipo inválido. Creando Canoe por defecto.");
                return new Canoe(number, name, 1);
        }
    }


    private void displayShipOptions() {
        System.out.println("Tipos disponibles:");
        System.out.println("1. Acorazado (tamaño 5)");
        System.out.println("2. Fragata (tamaño 3)");
        System.out.println("3. Canoa (tamaño 1)");
    }

    private boolean placeShipOnBoard(Player player, Ship ship) {
        System.out.println("Colocando " + ship.getName() + " (tamaño: " + ship.getSize() + ")");

        while (true) {
            try {
                int x = getIntInput("Coordenada X inicial (0-9): ", 0, Board.WIDTH - 1);
                int y = getIntInput("Coordenada Y inicial (0-9): ", 0, Board.HEIGHT - 1);

                System.out.print("¿Horizontal? (s/n): ");
                boolean isHorizontal = scanner.nextLine().trim().equalsIgnoreCase("s");

                if (player.getBoard().placeShip(ship, x, y, isHorizontal)) {
                    return true;
                } else {
                    System.out.println("Posición inválida. Intenta nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor ingresa un número.");
                scanner.nextLine(); // Limpiar buffer
            }
        }
    }

    private int getIntInput(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = scanner.nextInt();
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println("Por favor ingresa un número entre " + min + " y " + max);
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor ingresa un número.");
                scanner.nextLine(); // Limpiar buffer
            }
        }
    }

    private Ship createShip(int type) {
        switch (type) {
            case 1: return new Battleship();
            case 2: return new Frigate();
            case 3: return new Canoe();
            default: return null;
        }
    }

    public void startGame() {
        System.out.println("\n=== COMIENZA EL JUEGO ===");

        while (!gameOver) {
            playTurn();
            checkGameOver();
            if (!gameOver) {
                switchPlayer();
            }
        }

        endGame();
    }

    private void playTurn() {
        System.out.println("\nTurno de " + currentPlayer.getName());

        Player opponent = (currentPlayer == player1) ? player2 : player1;

        // Mostrar tablero del oponente
        opponent.getBoard().displayOpponentView();

        // Mostrar tu propio tablero
        currentPlayer.getBoard().displayOwnBoard();

        // Realizar ataque
        boolean validAttack = false;
        while (!validAttack) {
            try {
                int x = getIntInput("Coordenada X para atacar (0-9): ", 0, Board.WIDTH - 1);
                int y = getIntInput("Coordenada Y para atacar (0-9): ", 0, Board.HEIGHT - 1);

                boolean hit = opponent.getBoard().receiveAttack(x, y);
                if (hit) {
                    System.out.println("¡Impacto en el barco " + opponent.getBoard().getCell(x, y)
                            .flatMap(cell -> Optional.ofNullable(cell.getShip()))
                            .map(Ship::getName)
                            .orElse("desconocido") + "!");
                } else {
                    System.out.println("¡Agua!");
                }
                validAttack = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void checkGameOver() {
        if (!player1.getFleet().hasActiveShips()) {
            gameOver = true;
            System.out.println("\n¡" + player2.getName() + " ha ganado el juego!");
        } else if (!player2.getFleet().hasActiveShips()) {
            gameOver = true;
            System.out.println("\n¡" + player1.getName() + " ha ganado el juego!");
        }
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        System.out.println("\nPresiona Enter para cambiar al siguiente jugador...");
        scanner.nextLine();
        scanner.nextLine(); // Dos nextLine() para asegurar que se espere
    }

    private void endGame() {
        System.out.println("\n=== FIN DEL JUEGO ===");
        displayFinalBoards();
        scanner.close();
    }

    private void displayFinalBoards() {
        System.out.println("\nTablero final de " + player1.getName() + ":");
        player1.getBoard().displayOwnBoard();

        System.out.println("\nTablero final de " + player2.getName() + ":");
        player2.getBoard().displayOwnBoard();
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