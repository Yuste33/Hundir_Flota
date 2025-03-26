import hashMap.ShipHashTable;
import ships.*;
import jakarta.persistence.*;
import java.util.Scanner;

public class Main {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static ShipHashTable shipHashTable;

    public static void main(String[] args) {
        // 1. Inicialización de JPA y tablas hash
        initializeJPA();

        // 2. Menú principal
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("\n=== SISTEMA DE GESTIÓN DE BARCOS ===");
            System.out.println("1. Agregar nuevo barco");
            System.out.println("2. Buscar barco por nombre");
            System.out.println("3. Buscar barco por número");
            System.out.println("4. Buscar barcos por tipo");
            System.out.println("5. Mostrar todos los barcos");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            option = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (option) {
                case 1:
                    addNewShip(scanner);
                    break;
                case 2:
                    searchByName(scanner);
                    break;
                case 3:
                    searchByNumber(scanner);
                    break;
                case 4:
                    searchByType(scanner);
                    break;
                case 5:
                    displayAllShips();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        } while (option != 0);

        // 3. Cierre de recursos
        scanner.close();
        closeJPA();
    }

    private static void initializeJPA() {
        emf = Persistence.createEntityManagerFactory("battleshipPU");
        em = emf.createEntityManager();
        shipHashTable = new ShipHashTable();

        // Cargar datos existentes al iniciar
        loadExistingShips();
    }

    private static void loadExistingShips() {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            TypedQuery<Ship> query = em.createQuery("SELECT s FROM Ship s", Ship.class);
            query.getResultList().forEach(ship -> {
                shipHashTable.addShip(ship);
                System.out.println("Cargado: " + ship.getName() + " (" + ship.getType() + ")");
            });

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }

    private static void addNewShip(Scanner scanner) {
        System.out.println("\n--- AGREGAR NUEVO BARCO ---");
        System.out.println("Tipos disponibles:");
        System.out.println("1. Acorazado (Battleship)");
        System.out.println("2. Fragata (Frigate)");
        System.out.println("3. Canoa (Canoe)");
        System.out.print("Seleccione el tipo: ");
        int type = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        System.out.print("Nombre del barco: ");
        String name = scanner.nextLine();

        System.out.print("Número de identificación: ");
        int number = scanner.nextInt();

        System.out.print("Nivel: ");
        int level = scanner.nextInt();

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Ship newShip;
            switch (type) {
                case 1:
                    newShip = new Battleship(name);
                    break;
                case 2:
                    newShip = new Frigate(name);
                    break;
                case 3:
                    newShip = new Canoe(name);
                    break;
                default:
                    throw new IllegalArgumentException("Tipo no válido");
            }

            newShip.setNumber(number);
            newShip.setLevel(level);

            em.persist(newShip);
            shipHashTable.addShip(newShip);

            tx.commit();
            System.out.println("Barco agregado exitosamente!");
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.err.println("Error al agregar barco: " + e.getMessage());
        }
    }

    private static void searchByName(Scanner scanner) {
        System.out.print("\nIngrese el nombre del barco a buscar: ");
        String name = scanner.nextLine();

        Ship ship = shipHashTable.getByName(name);
        if (ship != null) {
            displayShipDetails(ship);
        } else {
            System.out.println("No se encontró ningún barco con ese nombre");
        }
    }

    private static void searchByNumber(Scanner scanner) {
        System.out.print("\nIngrese el número del barco a buscar: ");
        int number = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        Ship ship = shipHashTable.getByNumber(number);
        if (ship != null) {
            displayShipDetails(ship);
        } else {
            System.out.println("No se encontró ningún barco con ese número");
        }
    }

    private static void searchByType(Scanner scanner) {
        System.out.println("\nTipos disponibles:");
        System.out.println("1. Acorazado (Battleship)");
        System.out.println("2. Fragata (Frigate)");
        System.out.println("3. Canoa (Canoe)");
        System.out.print("Seleccione el tipo a buscar: ");
        int type = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        String typeStr;
        switch (type) {
            case 1: typeStr = "BATTLESHIP"; break;
            case 2: typeStr = "FRIGATE"; break;
            case 3: typeStr = "CANOE"; break;
            default:
                System.out.println("Tipo no válido");
                return;
        }

        var ships = shipHashTable.getByType(typeStr);
        if (ships.isEmpty()) {
            System.out.println("No se encontraron barcos de este tipo");
        } else {
            System.out.println("\nBarcos encontrados (" + ships.size() + "):");
            ships.forEach(Main::displayShipDetails);
        }
    }

    private static void displayAllShips() {
        System.out.println("\n--- LISTA COMPLETA DE BARCOS ---");
        var allShips = em.createQuery("SELECT s FROM Ship s", Ship.class).getResultList();

        if (allShips.isEmpty()) {
            System.out.println("No hay barcos registrados");
        } else {
            allShips.forEach(Main::displayShipDetails);
        }
    }

    private static void displayShipDetails(Ship ship) {
        System.out.println("\n=== " + ship.getName() + " ===");
        System.out.println("Tipo: " + ship.getType());
        System.out.println("Número: " + ship.getNumber());
        System.out.println("Nivel: " + ship.getLevel());
        System.out.println("Tamaño: " + ship.getSize() + " posiciones");
        System.out.println("Estado: " + (ship.isSunk() ? "Hundido" : "A flote"));

        if (ship instanceof Frigate) {
            System.out.println("Cañones: " + ((Frigate) ship).getCannonCount());
        }
    }

    private static void closeJPA() {
        if (em != null) em.close();
        if (emf != null) emf.close();
    }
}