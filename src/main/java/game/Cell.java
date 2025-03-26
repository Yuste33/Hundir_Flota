package game;

import jakarta.persistence.*;
import ships.Ship;

@Entity
public class Cell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int x;
    private int y;
    private boolean occupied;
    private boolean hit;

    @ManyToOne
    @JoinColumn(name = "ship_id")
    private Ship ship;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    public Cell() {}

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.occupied = false;
        this.hit = false;
    }

    // Método para obtener el carácter de visualización
    public char getDisplayChar(boolean showShips) {
        if (hit && occupied && ship != null) {
            return 'X'; // Impacto en barco
        } else if (hit) {
            return 'O'; // Ataque al agua
        } else if (showShips && occupied && ship != null) {
            return 'S'; // Barco no tocado (solo visible en tu tablero)
        }
        return '.'; // Celda vacía no atacada
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}