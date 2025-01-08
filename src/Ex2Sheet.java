import java.io.IOException;
// Add your documentation below:

public class Ex2Sheet implements Sheet {
    private Cell[][] table;
    // Add your code here

    // ///////////////////
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
            int y = Integer.parseInt(cords.substring(1)) - 1; // Convert row to index
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



    @Override
    public int[][] depth() {
        int[][] ans = new int[width()][height()];
        // Add your code here

        // ///////////////////
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
    public String getCellReference(int x, int y) {
        // Validate indices
        if (!isIn(x, y)) {
            return Ex2Utils.ERR_FORM; // Out of bounds
        }

        // Create a CellEntry and return its string representation
        CellEntry entry = new CellEntry(x, y);
        return entry.toString();
    }
    @Override
    public String eval(int x, int y) {
        if (!isIn(x, y))
            return Ex2Utils.ERR_FORM;
        String ans= null;
        String cell = get(x,y).getData();
        if(get(x,y)!= null){
            ans = get(x,y).toString();
        }
        if(SCell.isForm(cell)){
            double result = computeForm(cell);
            return String.valueOf(result);
        }
        if(SCell.isNumber(cell)){
            double n = Double.parseDouble(cell);
            return String.valueOf(n);

        }
        if(SCell.isText(cell)) {
            return cell;
        }
        return ans;
    }

    public static double cellRef(SCell s) {
        if (s.getType() != Ex2Utils.NUMBER) {
            return Ex2Utils.ERR;
        }
        return Double.parseDouble(s.getData());
    }
    /////////////////////////////////////////////////////////////////
    public static double calculateRemaining(String s) {
        double result = 0;
        double current = 0;
        char lastOperator = '+'; //default operator
        int i = 0;
        while (i < s.length()) {
            if (Character.isWhitespace(s.charAt(i))) {//skip spaces
                continue;
            }
            String number = "";
            while (i < s.length() && (Character.isDigit(s.charAt(i)) || s.charAt(i) == '.')) {
                number += s.charAt(i);
                i++;
            }
            double value = Double.parseDouble(number);
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
            if (i < s.length() && isOperator(s.charAt(i))) {
                lastOperator = s.charAt(i);
                i++;
            }
        }
        result += current;
        return result;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    public static double computeForm(String form) {
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