package ships;

public abstract class Ship {
    public int size;
    protected boolean[] positions;

    public Ship(int size) {
        this.size = size;
        this.positions = new boolean[size];
    }

    public abstract boolean hit(int position);

    public boolean isSunk() {
        for (boolean pos : positions) {
            if (!pos) return false;
        }
        return true;
    }
}
