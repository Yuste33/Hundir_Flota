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
    private boolean isHit;

    @ManyToOne
    @JoinColumn(name = "ship_id")
    private Ship ship;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    // Getters/Setters
}