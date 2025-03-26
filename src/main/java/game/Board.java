package game;
import ships.Ship;

public class Board {
    private char[][] grid;
    private static final int SIZE = 10;

    public Board() {
        grid = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = '-';
            }
        }
    }

    public void placeShip(int x, int y, Ship ship) {
        if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
            grid[x][y] = 'S';
        }
    }

    public boolean attack(int x, int y) {
        if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
            if (grid[x][y] == 'S') {
                grid[x][y] = 'X';
                return true;
            }
        }
        return false;
    }

    public void displayBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
}