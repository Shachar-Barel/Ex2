import java.io.IOException;
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
        int x = Character.toUpperCase(col) - 'A'; // Convert column to index
        try {
            int y = Integer.parseInt(cords.substring(1)); // Convert row to index
            return get(x, y);
        } catch (NumberFormatException e) {
            return null; // Invalid row
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


 public int computeDepth(int x, int y){
        int ans=0;
    Cell cell = get(x,y);
     if(cell== null || cell.getType()== 1 || cell.getType()== 2)
         return ans;
     if(cell.getType() == 3) {

     }
 return 0;
 }
    @Override
    public int[][] depth() {
        int x= width();
        int y = height();
        int[][] ans = new int[x][y];
        for(int i=0;i<x;i=i+1) {
            for(int j=0;j<y;j=j+1) {
                Cell cell = get(x,y);
                        ans[x][y]= cell.getOrder();
                }
            }

        return ans;
    }

    @Override
    public void load(String fileName) throws IOException {
        // Add your code here

        /////////////////////
    }

    @Override
    public void save(String fileName) throws IOException {
        // Add your code here

        /////////////////////
    }
    @Override
    public String eval(int x, int y) {
        if (!isIn(x, y))
            return Ex2Utils.ERR_FORM;
        String cell = get(x,y).getData();
        if(SCell.isForm(cell)){
            double result = computeForm(cell);
            return String.valueOf(result);
        }
        if(SCell.isNumber(cell)) {
            double n = Double.parseDouble(cell);
            return String.valueOf(n);
        }
            return cell;

    }
    ///////////////////////////
    public String getCellValue(String s) {
        Cell cell = get(s);
        if (cell == null) {
            return Ex2Utils.ERR_FORM;
        }
        return cell.toString();
    }

    /////////////////////////////////////////////////////////////////
    public double calculateRemaining(String s) {
        double result = 0;
        double current = 0;
        char lastOperator = '+'; // Default operator
        int i = 0;

        while (i < s.length()) {
            if (Character.isWhitespace(s.charAt(i))) { // Skip spaces
                i++;
                continue;
            }

            String number = "";
            // Check if the current character is a letter (possible cell reference)
            if (Character.isLetter(s.charAt(i))) {
                String cellRef = "";
                // Build the cell reference
                while (i < s.length() && !isOperator(s.charAt(i))) {
                    cellRef += s.charAt(i);
                    i++;
                }
                // Get the value of the cell reference
                String cellValue = getCellValue(cellRef);
                if (cellValue.equals(Ex2Utils.ERR_FORM)) {
                    throw new IllegalArgumentException(Ex2Utils.ERR_FORM);
                }
                number = cellValue; // Treat cell value as a number
            } else {
                // Parse digits or decimal numbers
                while (i < s.length() && (Character.isDigit(s.charAt(i)) || s.charAt(i) == '.')) {
                    number += s.charAt(i);
                    i++;
                }
            }

            // Parse the number into a double
            double value = Double.parseDouble(number);

            // Apply the last operator
            if (lastOperator == '*') {
                current *= value;
            } else if (lastOperator == '/') {
                current /= value;
            } else if (lastOperator == '+') {
                result += current;
                current = value;
            } else if (lastOperator == '-') {
                result += current;
                current = -value;
            }

            // Update the operator
            if (i < s.length() && isOperator(s.charAt(i))) {
                lastOperator = s.charAt(i);
                i++;
            }
        }

        // Add the last processed value
        result += current;
        return result;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////
    public double computeForm(String form) {
        form= form.substring(1);
        while (form.contains("(")) {
            int openIndex = form.lastIndexOf("(");
            int closeIndex = form.indexOf(")", openIndex);
            String innerExpression = form.substring(openIndex + 1, closeIndex);
            double innerResult = calculateRemaining(innerExpression);
            form = String.valueOf(form.substring(0, openIndex)) + Double.toString(innerResult) + String.valueOf(form.substring(closeIndex + 1));
        }
        return calculateRemaining(form);
    }

    public static boolean isOperator(char c) { // a function to check if a char is operator
        return c == '/' || c == '*' || c == '-' || c == '+';
    }
}