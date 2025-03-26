import builders.BattleshipBuilder;
import builders.CanoeBuilder;
import builders.FrigateBuilder;
import directors.ShipDirector;
import ships.Ship;

public class Main {
    public static void main(String[] args) {
        ShipDirector battleshipDirector = new ShipDirector(new BattleshipBuilder());
        ShipDirector frigateDirector = new ShipDirector(new FrigateBuilder());
        ShipDirector canoeDirector = new ShipDirector(new CanoeBuilder());

        Ship battleship = battleshipDirector.constructShip();
        Ship frigate = frigateDirector.constructShip();
        Ship canoe = canoeDirector.constructShip();

        System.out.println("ships.Battleship creado con tamaño: " + battleship.size);
        System.out.println("ships.Frigate creado con tamaño: " + frigate.size);
        System.out.println("ships.Canoe creado con tamaño: " + canoe.size);
    }
}
