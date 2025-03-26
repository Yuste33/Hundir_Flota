package directors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import builders.*;
import ships.Canoe;
import ships.Frigate;

@Service
public class ShipDirector {

    private final BattleshipBuilder battleshipBuilder;
    private final FrigateBuilder frigateBuilder;
    private final CanoeBuilder canoeBuilder;

    @Autowired
    public ShipDirector(BattleshipBuilder battleshipBuilder,
                        FrigateBuilder frigateBuilder,
                        CanoeBuilder canoeBuilder) {
        this.battleshipBuilder = battleshipBuilder;
        this.frigateBuilder = frigateBuilder;
        this.canoeBuilder = canoeBuilder;
    }

    public void constructFleet() {
        // Construir una Canoe
        canoeBuilder.buildName("Canoa Rápida");
        Canoe canoe = canoeBuilder.getShip();

        // Construir una Fragata con cañones personalizados
        frigateBuilder.buildName("Fragata Defensora");
        frigateBuilder.buildCannons(3); // Configurar 3 cañones en lugar de 2
        Frigate frigate = frigateBuilder.getShip();

        // Construir un Acorazado
        battleshipBuilder.buildName("Acorazado Victoria");
        battleshipBuilder.getShip();
    }
}