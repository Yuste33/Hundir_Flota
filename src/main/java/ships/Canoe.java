package ships;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("CANOE") // Valor discriminador para la herencia
public class Canoe extends Ship {

    // Constructor vacío requerido por JPA
    public Canoe() {
        super();
    }
    int number;
    String name;
    int level;
    public Canoe(int number, String name, int level) {
        this.name=name;
        this.level=level;
        this.number=number;

    }

    // Constructor con parámetros
    public Canoe(String name) {
        super(name, 1); // Tamaño fijo de 1 para Canoe
    }


    @Override
    public String getType() {
        return "CANOE";
    }

    // Método específico para Canoe
    public boolean isSmall() {
        return true;
    }
}