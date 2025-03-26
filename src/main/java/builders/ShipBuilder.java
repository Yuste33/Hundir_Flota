package builders;

import ships.Ship;

public interface ShipBuilder {
    void buildName(String name);
    Ship getShip();
}