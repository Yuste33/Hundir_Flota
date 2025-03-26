package players;

import jakarta.persistence.*;
import ships.Ship;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class Fleet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "fleet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ship> ships= new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "player_id")
    private Player player;

    public Fleet() {
    }

    // Método para añadir un barco a la flota
    public boolean addShip(Ship ship) {
        if (ship == null) {
            throw new IllegalArgumentException("El barco no puede ser nulo");
        }

        if (ships.size() >= 3) {
            System.out.println("No se pueden añadir más barcos. Límite alcanzado.");
            return false;
        }

        if (ships.stream().anyMatch(s -> s.getNumber() == ship.getNumber())) {
            System.out.println("Ya existe un barco con este número.");
            return false;
        }

        ships.add(ship);
        return true;
    }

    // Método para eliminar un barco de la flota
    public boolean removeShip(Ship ship) {
        if (ship == null) return false;
        return ships.removeIf(s -> s.getId().equals(ship.getId()));
    }

    // Método para obtener un barco por su número
    public Optional<Ship> getShipByNumber(int number) {
        return ships.stream()
                .filter(ship -> ship.getNumber() == number)
                .findFirst();
    }

    // Método para verificar si hay barcos activos
    public boolean hasActiveShips() {
        return ships.stream().anyMatch(ship -> !ship.isSunk());
    }

    // Método para obtener todos los barcos no hundidos
    public List<Ship> getActiveShips() {
        return ships.stream()
                .filter(ship -> !ship.isSunk())
                .toList();
    }

    // Método para obtener todos los barcos hundidos
    public List<Ship> getSunkShips() {
        return ships.stream()
                .filter(Ship::isSunk)
                .toList();
    }

    // Método para obtener el número total de barcos
    public int getTotalShips() {
        return ships.size();
    }

    // Método para obtener el número de barcos activos
    public int getActiveShipsCount() {
        return (int) ships.stream()
                .filter(ship -> !ship.isSunk())
                .count();
    }

    // Método para obtener el número de barcos hundidos
    public int getSunkShipsCount() {
        return (int) ships.stream()
                .filter(Ship::isSunk)
                .count();
    }

    // Método para verificar si la flota está completamente hundida
    public boolean isFleetDestroyed() {
        return ships.stream().allMatch(Ship::isSunk);
    }

    // Método para mostrar el estado de la flota
    public void displayFleetStatus() {
        System.out.println("\n=== ESTADO DE LA FLOTA ===");
        System.out.println("Total de barcos: " + getTotalShips());
        System.out.println("Barcos activos: " + getActiveShipsCount());
        System.out.println("Barcos hundidos: " + getSunkShipsCount());

        System.out.println("\nDetalles de los barcos:");
        ships.forEach(ship -> {
            String status = ship.isSunk() ? "HUNDIDO" : "ACTIVO";
            System.out.printf("- %s (Nivel %d, Tipo: %s, Salud: %d/%d) - %s%n",
                    ship.getName(),
                    ship.getLevel(),
                    ship.getType(),
                    status);
        });
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}