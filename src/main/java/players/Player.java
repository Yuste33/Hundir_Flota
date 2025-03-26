package players;

import jakarta.persistence.*;
import game.Board;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fleet_id")
    private Fleet fleet;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id")
    private Board board;

    // Getters/Setters
}