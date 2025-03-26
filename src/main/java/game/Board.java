package game;

import jakarta.persistence.*;
import players.Player;
import ships.Ship;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class Board {
    // Dimensiones estándar del tablero
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cell> cells = new ArrayList<>();

    @OneToOne(mappedBy = "board")
    private Player player;

    // Constructor que inicializa el tablero con celdas vacías
    public Board() {
        initializeCells();
    }

    // Inicializa todas las celdas del tablero
    private void initializeCells() {
        cells.clear();
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                Cell cell = new Cell(x, y);
                cell.setBoard(this);
                cells.add(cell);
            }
        }
    }

    // Método para colocar un barco en el tablero
    public boolean placeShip(Ship ship, int startX, int startY, boolean isHorizontal) {
        // Verificar si la posición es válida
        if (!isValidPosition(startX, startY, ship.getSize(), isHorizontal)) {
            return false;
        }

        // Ocupar las celdas correspondientes
        for (int i = 0; i < ship.getSize(); i++) {
            int x = isHorizontal ? startX + i : startX;
            int y = isHorizontal ? startY : startY + i;

            getCell(x, y).ifPresent(cell -> {
                cell.setShip(ship);
                cell.setOccupied(true);
            });
        }

        return true;
    }

    // Verifica si una posición es válida para colocar un barco
    private boolean isValidPosition(int x, int y, int size, boolean isHorizontal) {
        // Verificar límites del tablero
        if (x < 0 || y < 0) return false;
        if (isHorizontal && (x + size > WIDTH)) return false;
        if (!isHorizontal && (y + size > HEIGHT)) return false;

        // Verificar que las celdas estén libres
        for (int i = 0; i < size; i++) {
            int checkX = isHorizontal ? x + i : x;
            int checkY = isHorizontal ? y : y + i;

            Optional<Cell> cell = getCell(checkX, checkY);
            if (cell.isEmpty() || cell.get().isOccupied()) {
                return false;
            }
        }

        return true;
    }

    // Método para recibir un ataque
    public boolean receiveAttack(int x, int y) {
        Optional<Cell> cellOptional = getCell(x, y);
        if (cellOptional.isEmpty()) {
            return false; // Coordenadas inválidas
        }

        Cell cell = cellOptional.get();
        if (cell.isHit()) {
            return false; // Ya fue atacada esta celda
        }

        cell.setHit(true);

        if (cell.isOccupied() && cell.getShip() != null) {
            cell.getShip().hit();
            return true; // Impacto en un barco
        }

        return false; // Ataque al agua
    }

    // Obtiene una celda específica
    public Optional<Cell> getCell(int x, int y) {
        return cells.stream()
                .filter(c -> c.getX() == x && c.getY() == y)
                .findFirst();
    }

    // Muestra la vista del tablero (para el propio jugador)
    public void displayOwnBoard() {
        System.out.println("\nTu tablero:");
        displayBoard(true);
    }

    // Muestra la vista del oponente (solo impactos)
    public void displayOpponentView() {
        System.out.println("\nTablero del oponente:");
        displayBoard(false);
    }

    // Método auxiliar para mostrar el tablero
    private void displayBoard(boolean showShips) {
        // Encabezado con números de columnas
        System.out.print("  ");
        for (int x = 0; x < WIDTH; x++) {
            System.out.print(x + " ");
        }
        System.out.println();

        // Filas del tablero
        for (int y = 0; y < HEIGHT; y++) {
            System.out.print(y + " ");
            for (int x = 0; x < WIDTH; x++) {
                Optional<Cell> cell = getCell(x, y);
                if (cell.isPresent()) {
                    System.out.print(cell.get().getDisplayChar(showShips) + " ");
                } else {
                    System.out.print("? ");
                }
            }
            System.out.println();
        }
    }

    // Verifica si todos los barcos han sido hundidos
    public boolean allShipsSunk() {
        return cells.stream()
                .filter(Cell::isOccupied)
                .allMatch(c -> c.getShip() != null && c.getShip().isSunk());
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}