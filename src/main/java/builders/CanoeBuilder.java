package builders;
import ships.Canoe;
import ships.Ship;

public class CanoeBuilder implements ShipBuilder {
    @Override
    public Ship buildShip() {
        return new Canoe();
    }
}
