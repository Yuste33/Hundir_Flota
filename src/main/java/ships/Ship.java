package ships;

import jakarta.persistence.*;
import players.Fleet;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SHIPS")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "SHIP_TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class Ship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SHIP_ID")
    private Long id;

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Column(name = "SIZE", nullable = false)
    private int size;

    @Column(name = "HEALTH", nullable = false)
    private int health;

    @Column(name = "NUMBER", unique = true)
    private int number;

    @Column(name = "LEVEL")
    private int level;

    @ManyToMany(mappedBy = "ships", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Fleet> fleets = new HashSet<>();

    @OneToMany(mappedBy = "ship", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<game.Cell> positions = new HashSet<>();

    // Constructores
    protected Ship() {
        // Requerido por JPA
    }

    public Ship(String name, int size) {
        this.name = name;
        this.size = size;
        this.health = size;
    }

    // Métodos de negocio
    public abstract String getType();

    public boolean isSunk() {
        return health <= 0;
    }

    public void hit() {
        if (health > 0) {
            health--;
        }
    }

    public void addToFleet(Fleet fleet) {
        this.fleets.add(fleet);
        fleet.getShips().add(this);
    }

    public void removeFromFleet(Fleet fleet) {
        this.fleets.remove(fleet);
        fleet.getShips().remove(this);
    }

    public void addPosition(game.Cell cell) {
        this.positions.add(cell);
        cell.setShip(this);
    }

    public void removePosition(game.Cell cell) {
        this.positions.remove(cell);
        cell.setShip(null);
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    protected void setSize(int size) {
        this.size = size;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Set<Fleet> getFleets() {
        return fleets;
    }

    public Set<game.Cell> getPositions() {
        return positions;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + getType() +
                ", size=" + size +
                ", health=" + health +
                ", number=" + number +
                ", level=" + level +
                '}';
    }
}