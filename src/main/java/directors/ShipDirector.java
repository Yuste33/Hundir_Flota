package directors;

import builders.ShipBuilder;
import ships.Ship;

public class ShipDirector {
    private ShipBuilder builder;

    public ShipDirector(ShipBuilder builder) {
        this.builder = builder;
    }

    public Ship constructShip() {
        return builder.buildShip();
    }
}