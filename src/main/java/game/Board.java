package game;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Cell> cells = new ArrayList<>();

    @OneToOne(mappedBy = "board")
    private players.Player player;

    // Getters/Setters
}