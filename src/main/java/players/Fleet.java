package players;

import jakarta.persistence.*;
import ships.Ship;
import java.util.Set;

@Entity
public class Fleet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "fleet_ships",
            joinColumns = @JoinColumn(name = "fleet_id"),
            inverseJoinColumns = @JoinColumn(name = "ship_id")
    )
    private Set<Ship> ships;

    @OneToOne(mappedBy = "fleet")
    private Player player;

    // Getters/Setters
}