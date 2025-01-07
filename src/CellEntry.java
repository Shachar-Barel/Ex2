// Add your documentation below:
public class CellEntry  implements Index2D {
private int x;
private int y;

   //constructor
    public CellEntry(int x, int y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public String toString() {
        if (!isValid()) {
            return "Invalid Cell";
        }
        return Ex2Utils.ABC[x] + (y + 1);
    }
    @Override
    public boolean isValid() {
        return x >= 0 && x < Ex2Utils.ABC.length && y >= 0 && y < 100;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}