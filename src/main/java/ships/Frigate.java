package ships;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("FRIGATE") // Valor discriminador para la herencia
public class Frigate extends Ship {

    // Atributo específico de Frigate
    private int cannonCount = 2; // Fragatas tienen 2 cañones por defecto

    // Constructor vacío requerido por JPA
    public Frigate() {
        super();
    }

    // Constructor con parámetros
    public Frigate(String name) {
        super(name, 3); // Tamaño fijo de 3 para Frigate
    }

    @Override
    public String getType() {
        return "FRIGATE";
    }

    // Métodos específicos para Frigate
    public int getCannonCount() {
        return cannonCount;
    }

    public void setCannonCount(int cannonCount) {
        this.cannonCount = cannonCount;
    }
}