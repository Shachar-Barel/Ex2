public class CellEntry implements Index2D {
    private int x; // Column index (0-based)
    private int y; // Row index (1-based)

    // Constructor
    public CellEntry(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        if (!isValid()) {
            return Ex2Utils.ERR_FORM; // Return error format if invalid
        }
        return toSpreadsheetNotation(x, y);
    }

    @Override
    public boolean isValid() {
        // Validate that x and y are within appropriate bounds
        return x >= 0 && x < 26 && y >= 1 && y <= 100; // Rows are 1-based
    }

    @Override
    public int getX() {
        return isValid() ? x : Ex2Utils.ERR; // Return error if invalid
    }

    @Override
    public int getY() {
        return isValid() ? y : Ex2Utils.ERR; // Return error if invalid
    }

    private String toSpreadsheetNotation(int x, int y) {
        char column = (char) ('A' + x); // Convert x to a column letter
        return column + String.valueOf(y); // Keep y as 1-based
    }
}
