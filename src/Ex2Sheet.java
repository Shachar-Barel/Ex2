import java.io.*;
// Add your documentation below:

public class Ex2Sheet implements Sheet {
    private Cell[][] table;

    public Ex2Sheet(int x, int y) {
        table = new SCell[x][y];
        for(int i=0;i<x;i=i+1) {
            for(int j=0;j<y;j=j+1) {
                table[i][j] = new SCell(Ex2Utils.EMPTY_CELL);
            }
        }
        eval();
    }
    public Ex2Sheet() {
        this(Ex2Utils.WIDTH, Ex2Utils.HEIGHT);
    }

    @Override
    public String value(int x, int y) {
        String ans = Ex2Utils.EMPTY_CELL;
        Cell c = get(x,y);
        if(c!=null){ans = eval(x,y);}
       return ans;
    }

    @Override
    public Cell get(int x, int y) {
        return table[x][y];
    }
    @Override
    public Cell get(String cords) {
        if (cords == null || cords.isEmpty()) {
            return null;
        }

        char col = cords.charAt(0);
        if (!Character.isLetter(col)) {
            return null;
        }

        try {
            int x = Character.toUpperCase(col) - 'A';
            int y = Integer.parseInt(cords.substring(1));
            if (!isIn(x, y)) {
                return null;
            }
            return get(x, y);
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            return null; // Invalid reference
        }
    }

    @Override
    public int width() {
        return table.length;
    }
    @Override
    public int height() {
        return table[0].length;
    }
    @Override
    public void set(int x, int y, String s) {
        if (isIn(x, y)) {
            table[x][y] = new SCell(s);
        }
    }
    @Override
    public boolean isIn(int xx, int yy) {
        return xx >= 0 && xx < table.length && yy >= 0 && yy < table[0].length;

    }
    @Override
    public void eval() {
        int[][] dd = depth();

    }


    public int[][] depth() {
        int w = width();
        int h = height();
        int[][] ans = new int[w][h];
        boolean[][] visiting = new boolean[w][h]; // Tracks cells being visited for cycle detection

        // Initialize the depth array to -1 (unprocessed)
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                ans[x][y] = -1;
            }
        }

        // Compute depth for each cell
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                if (ans[x][y] == -1) { // Skip already processed cells
                    computeDepth(x, y, ans, visiting);
                }
            }
        }

        return ans;
    }
    private int computeDepth(int x, int y, int[][] ans, boolean[][] visiting) {
        if (!isIn(x, y)) return 0; // Out-of-bounds cells have depth 0

        if (visiting[x][y]) { // Cycle detected
            markCycle(visiting, ans); // Mark all cells in the cycle
            return -1;
        }

        if (ans[x][y] != -1) { // Depth already computed
            return ans[x][y];
        }

        Cell cell = get(x, y);
        if (cell == null || cell.getData() == null || cell.getData().isEmpty() || cell.getType() == Ex2Utils.NUMBER || cell.getType() == Ex2Utils.TEXT) {
            ans[x][y] = 0; // Numbers and text have depth 0
            return 0;
        }

        String data = cell.getData();
        if (!SCell.isForm(data) || data.length() <= 1) { // Invalid formula
            ans[x][y] = -1;
            return -1;
        }

        visiting[x][y] = true; // Mark cell as being visited
        String formula = data.substring(1).trim(); // Remove '='
        String[] references = formula.split("[^A-Za-z0-9]+"); // Split by non-alphanumeric characters
        int maxDepth = 0;

        for (String ref : references) {
            if (!ref.isEmpty() && Character.isLetter(ref.charAt(0))) { // Only process valid cell references
                try {
                    int depX = Character.toUpperCase(ref.charAt(0)) - 'A'; // Column index
                    int depY = Integer.parseInt(ref.substring(1)); // Row index

                    if (!isIn(depX, depY)) { // Invalid reference
                        ans[x][y] = -1;
                        visiting[x][y] = false;
                        return -1;
                    }

                    int dependencyDepth = computeDepth(depX, depY, ans, visiting);
                    if (dependencyDepth == -1) { // Cycle detected in dependency
                        markCycle(visiting, ans);
                        visiting[x][y] = false;
                        return -1;
                    }
                    maxDepth = Math.max(maxDepth, dependencyDepth);
                } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                    ans[x][y] = -1; // Invalid reference format
                    visiting[x][y] = false;
                    return -1;
                }
            }
        }

        visiting[x][y] = false; // Unmark cell as being visited
        ans[x][y] = maxDepth + 1; // Formula depth is 1 + max depth of dependencies
        return ans[x][y];
    }

    private void markCycle(boolean[][] visiting, int[][] ans) {
        for (int i = 0; i < visiting.length; i++) {
            for (int j = 0; j < visiting[i].length; j++) {
                if (visiting[i][j]) {
                    ans[i][j] = -1; // Mark all cells currently being visited as part of a cycle
                }
            }
        }
    }



    @Override
    public void load(String fileName) throws IOException {
        // Clear the current spreadsheet
        table = new SCell[Ex2Utils.WIDTH][Ex2Utils.HEIGHT];
        for (int x = 0; x < Ex2Utils.WIDTH; x++) {
            for (int y = 0; y < Ex2Utils.HEIGHT; y++) {
                table[x][y] = new SCell(Ex2Utils.EMPTY_CELL);
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Skip the first header line
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                // Ignore empty or malformed lines
                if (line.isEmpty()) {
                    continue;
                }

                // Split the line into parts: x, y, and cell data
                String[] parts = line.split(",", 3);
                if (parts.length < 3) {
                    System.out.println("Skipping malformed line: " + line);
                    continue;
                }

                try {
                    int x = Integer.parseInt(parts[0].trim());
                    int y = Integer.parseInt(parts[1].trim());
                    String cellData = parts[2].trim();

                    // Only set the cell if it's within bounds
                    if (isIn(x, y)) {
                        set(x, y, cellData);
                    } else {
                        System.out.println("Skipping out-of-bounds cell: " + line);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Skipping invalid line (NumberFormatException): " + line);
                }
            }
        } catch (FileNotFoundException e) {
            throw new IOException("File not found: " + fileName, e);
        } catch (IOException e) {
            throw new IOException("Error reading file: " + fileName, e);
        }
    }

    @Override
    public void save(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Write the header
            writer.write("I2CS ArielU: SpreadSheet (Ex2) assignment - this line should be ignored in the load method");
            writer.newLine();

            // Save non-empty cells
            for (int x = 0; x < width(); x++) {
                for (int y = 0; y < height(); y++) {
                    Cell cell = get(x, y);
                    if (cell != null && cell.getData() != null && !cell.getData().isEmpty()) {
                        writer.write(x + "," + y + "," + cell.getData());
                        writer.newLine();
                    }
                }
            }
        }
    }
    @Override
    public String eval(int x, int y) {
        if (!isIn(x, y))
            return Ex2Utils.ERR_FORM;
        String cell = get(x,y).getData();
        int[][] depth = depth();
        if(SCell.isForm(cell)){
            this.table[x][y].setType(Ex2Utils.FORM);
            if(depth[x][y]==-1)
                return Ex2Utils.ERR_CYCLE;
            double result = computeForm(cell);
            return String.valueOf(result);
        }
        if(SCell.isNumber(cell)) {
            this.table[x][y].setType(Ex2Utils.NUMBER);
            double n = Double.parseDouble(cell);
            return String.valueOf(n);
        }
            this.table[x][y].setType(Ex2Utils.TEXT);
            return cell;

    }
    public double calculateRemaining(String s) {
        double result = 0; // Holds the final result
        double current = 0; // Temporary value for the current number being processed
        char lastOperator = '+'; // Keeps track of the last operator used
        int i = 0;

        while (i < s.length()) {
            if (Character.isWhitespace(s.charAt(i))) { // If there is a space, skip it
                i++;
                continue;
            }

            String number = ""; // Stores the current number or reference
            boolean isNegative = false; // Flag to check if the number is negative

            // Check if the current part starts with a minus sign
            if (s.charAt(i) == '-') {
                isNegative = true;
                i++;
            }

            // Check if the part is a cell reference (starts with a letter)
            if (Character.isLetter(s.charAt(i))) {
                String cellRef = ""; // To store the cell reference (like A1)
                // Read the full cell reference (e.g., A1, B2)
                while (i < s.length() && !isOperator(s.charAt(i)) && s.charAt(i) != ' ') {
                    cellRef += s.charAt(i);
                    i++;
                }
                // Get the cell's data
                Cell referencedCell = get(cellRef);
                if (referencedCell == null || referencedCell.getData() == null || referencedCell.getData().isEmpty()) {
                    throw new IllegalArgumentException(Ex2Utils.EMPTY_CELL); // Error if the cell is empty
                }
                // Evaluate the cell and assign its value to `number`
                String cellValue = eval(Character.toUpperCase(cellRef.charAt(0)) - 'A', Integer.parseInt(cellRef.substring(1)));
                number = cellValue;
            } else {
                // If it's not a reference, it's a number
                // Read the full number (may include decimals)
                while (i < s.length() && (Character.isDigit(s.charAt(i)) || s.charAt(i) == '.')) {
                    number += s.charAt(i);
                    i++;
                }
            }

            if (number.isEmpty()) { // If no number or reference was found, throw an error
                throw new IllegalArgumentException(Ex2Utils.EMPTY_CELL);
            }

            double value = Double.parseDouble(number); // Convert the string number to a double
            if (isNegative) { // Apply the negative sign if needed
                value = -value;
            }

            // Perform the operation based on the last operator
            if (lastOperator == '*') {
                current *= value;
            } else if (lastOperator == '/') {
                current /= value;
            } else if (lastOperator == '+') {
                result += current; // Add the current to the result
                current = value; // Start a new current value
            } else if (lastOperator == '-') {
                result += current; // Add the current to the result
                current = -value; // Start a new negative value
            }

            // Update the operator for the next calculation
            if (i < s.length() && isOperator(s.charAt(i))) {
                lastOperator = s.charAt(i);
                i++;
            }
        }

        result += current; // Add the last processed value to the result
        return result; // Return the final result
    }


    ///////////////////////////////////////////////////////////////////////////////////////////
    public double computeForm(String form) {
        form = form.substring(1); // Remove the '=' at the start of the formula

        // Process all parentheses in the formula
        while (form.contains("(")) {
            int openIndex = form.lastIndexOf("("); // Find the last '('
            int closeIndex = form.indexOf(")", openIndex); // Find the matching ')'
            String innerExpression = form.substring(openIndex + 1, closeIndex); // Extract the part inside parentheses
            double innerResult = calculateRemaining(innerExpression); // Evaluate the inner expression
            // Replace the parentheses with the calculated result
            form = form.substring(0, openIndex) + Double.toString(innerResult) + form.substring(closeIndex + 1);
        }

        return calculateRemaining(form); // Evaluate the remaining formula
    }

    public static boolean isOperator(char c) { // a function to check if a char is operator
        return c == '/' || c == '*' || c == '-' || c == '+';
    }
}