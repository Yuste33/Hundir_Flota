package game;

import players.Player;

public class Game {
    private Player player1;
    private Player player2;
    private Board board1;
    private Board board2;

    public Game(Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;
        this.board1 = new Board();
        this.board2 = new Board();
    }

    public void start() {
        System.out.println("¡Comienza el juego!");
        while (player1.hasShipsRemaining() && player2.hasShipsRemaining()) {
            System.out.println("Turno de ataque...");
            // Lógica para turnos de ataque (puede mejorarse con entrada del usuario)
        }
        if (!player1.hasShipsRemaining()) {
            System.out.println(player2.getName() + " gana!");
        } else {
            System.out.println(player1.getName() + " gana!");
        }
    }
}