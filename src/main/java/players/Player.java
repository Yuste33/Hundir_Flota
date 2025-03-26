package players;
import ships.Ship;

public class Player {
    private String name;
    private Ship[] fleet;

    public Player(String name) {
        this.name = name;
        this.fleet = new Ship[3];
    }

    public void setFleet(Ship[] ships) {
        this.fleet = ships;
    }

    public boolean hasShipsRemaining() {
        for (Ship ship : fleet) {
            if (!ship.isSunk()) return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }
}
