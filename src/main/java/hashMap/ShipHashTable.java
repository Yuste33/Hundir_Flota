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

    // Métodos para gestionar las tablas hash
}