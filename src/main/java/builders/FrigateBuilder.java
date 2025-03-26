package builders;
import ships.Frigate;
import ships.Ship;

public class FrigateBuilder implements ShipBuilder {
    @Override
    public Ship buildShip() {
        return new Frigate();
    }
}

