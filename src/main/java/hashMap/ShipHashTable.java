package hashMap;

import jakarta.persistence.*;
import ships.Ship;
import java.util.HashMap;
import java.util.Map;

@Entity
public class ShipHashTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "ship_name_mapping")
    @MapKeyColumn(name = "name_key")
    @Column(name = "ship_id")
    private Map<String, Long> nameTable = new HashMap<>();

    @Transient
    private Map<Integer, Ship> numberTable = new HashMap<>();

    @Transient
    private Map<String, Ship> typeTable = new HashMap<>();

    // Método para agregar un barco a todas las tablas hash
    public void addShip(Ship ship) {
        if (ship == null) {
            throw new IllegalArgumentException("El barco no puede ser nulo");
        }

        // Agregar a tabla por nombre
        nameTable.put(ship.getName(), ship.getId());

        // Agregar a tabla por número
        numberTable.put(ship.getNumber(), ship);

        // Agregar a tabla por tipo
        typeTable.put(ship.getType(), ship);
    }

    // Método para buscar un barco por su nombre
    public Ship getShipByName(String name) {
        Long shipId = nameTable.get(name);
        if (shipId == null) return null;

        // Buscar en las otras tablas (en este caso usamos numberTable)
        for (Ship ship : numberTable.values()) {
            if (ship.getId().equals(shipId)) {
                return ship;
            }
        }
        return null;
    }

    // Método para buscar un barco por su número
    public Ship getShipByNumber(int number) {
        return numberTable.get(number);
    }

    // Método para buscar barcos por su tipo
    public Ship getShipByType(String type) {
        return typeTable.get(type);
    }

    // Método para eliminar un barco de todas las tablas
    public boolean removeShip(Ship ship) {
        if (ship == null) return false;

        boolean removed = false;

        // Eliminar de tabla por nombre
        if (nameTable.remove(ship.getName()) != null) {
            removed = true;
        }

        // Eliminar de tabla por número
        if (numberTable.remove(ship.getNumber()) != null) {
            removed = true;
        }

        // Eliminar de tabla por tipo
        if (typeTable.remove(ship.getType()) != null) {
            removed = true;
        }

        return removed;
    }

    // Método para verificar si existe un barco con cierto nombre
    public boolean containsShipByName(String name) {
        return nameTable.containsKey(name);
    }

    // Método para verificar si existe un barco con cierto número
    public boolean containsShipByNumber(int number) {
        return numberTable.containsKey(number);
    }

    // Método para verificar si existe un barco de cierto tipo
    public boolean containsShipByType(String type) {
        return typeTable.containsKey(type);
    }

    // Método para obtener todos los barcos (de la tabla por número)
    public Map<Integer, Ship> getAllShips() {
        return new HashMap<>(numberTable);
    }

    // Método para limpiar todas las tablas
    public void clear() {
        nameTable.clear();
        numberTable.clear();
        typeTable.clear();
    }

    // Método para obtener el tamaño de las tablas (deberían ser iguales)
    public int size() {
        return numberTable.size();
    }

    // Getters y setters necesarios para JPA
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, Long> getNameTable() {
        return nameTable;
    }

    public void setNameTable(Map<String, Long> nameTable) {
        this.nameTable = nameTable;
    }

    public Ship getByNumber(int number) {
        // Verificación rápida de números negativos
        if (number < 0) {
            throw new IllegalArgumentException("El número de barco no puede ser negativo");
        }

        // Acceso directo a la tabla hash por número
        return numberTable.get(number);
    }

    public Ship getByName(String name) {
        // Validación del parámetro de entrada
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del barco no puede ser nulo o vacío");
        }

        // Normalización del nombre (elimina espacios y convierte a minúsculas)
        String normalizedName = name.trim().toLowerCase();

        // Buscar el ID en la tabla hash persistente
        Long shipId = nameTable.get(normalizedName);

        if (shipId == null) {
            return null; // No encontrado
        }

        // Buscar el barco completo en las otras tablas
        for (Ship ship : numberTable.values()) {
            if (ship.getId().equals(shipId)) {
                return ship;
            }
        }

        return null; // Caso donde el ID existe pero no el barco (inconsistencia)
    }
}