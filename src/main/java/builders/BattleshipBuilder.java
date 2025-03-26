package builders;
import ships.Battleship;
import ships.Ship;

public class BattleshipBuilder implements ShipBuilder {
    @Override
    public Ship buildShip() {
        return new Battleship();
    }
}
