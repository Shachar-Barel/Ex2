// Add your documentation below:

import java.util.List;

public class SCell implements Cell {
    private String line;
    private int type;
    private int order;

    public SCell(String s) {
        setData(s);
    }

    @Override
    public String getData() {
        return line;
    }

    @Override
    public void setData(String s) {
        line = s == null ? Ex2Utils.EMPTY_CELL : s.trim();
        if (isNumber(line)) {
            setType(Ex2Utils.NUMBER);
        } else if (isText(line)) {
            setType(Ex2Utils.TEXT);
        } else if (isForm(line)) {
            setType(Ex2Utils.FORM);
        } else if (!s.isEmpty())
            setType(Ex2Utils.ERR_FORM_FORMAT);
    }


    public static int computeOrder(String formula) {
        int count = 0;
        for (int i = 0; i < formula.length(); i++) {
            char c = formula.charAt(i);
            if (Character.isLetter(c)) {
                while (i < formula.length() && (Character.isLetterOrDigit(formula.charAt(i)))) {
                    i++;
                }
                count++;
            }
        }
        return 1 + count;
    }

    @Override
    public int getOrder() {
        if (type == Ex2Utils.NUMBER || type == Ex2Utils.TEXT)  {
            return 0;
        }
        if (type == Ex2Utils.FORM) {
            return computeOrder(line);
        }
        return -1; // Error for invalid types
    }

    @Override
    public String toString() {
        return getData();
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public void setType(int t) {
        type = t;
    }

    @Override
    public void setOrder(int t) {
        order = t;

    }

    public static boolean isNumber(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        // Check if the string matches the pattern of a valid number
        boolean hasDecimalPoint = false;
        boolean hasDigits = false;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (i == 0 && (c == '-' || c == '+')) {
                continue;
            } else if (c == '.') {
                if (hasDecimalPoint) {
                    return false;
                }
                hasDecimalPoint = true;
            } else if (Character.isDigit(c)) {
                hasDigits = true;
            } else {
                return false;
            }
        }
        return hasDigits;
    }

    ////////////////////////////////////////////////////////////
    public static boolean isText(String text) {
        if (text == null || text.isEmpty() || text.charAt(0) == '=' || isForm(text) || isNumber(text))
            return false;
        return true;
    }

    /////////////////////////////////////////////////////////////
    public static boolean areParenthesesBalanced(String formula) { // a function to check if the parentheses balanced returns true or false
        int balance = 0;
        for (char c : formula.toCharArray()) {
            if (c == '(') balance++;
            if (c == ')') balance--;
            if (balance < 0) return false;
        }
        return balance == 0;
    }

    /////////////////////////////////////////////////////////////////////
    public static boolean isOperator(char c) { // a function to check if a char is operator
        return c == '/' || c == '*' || c == '-' || c == '+';
    }

    /////////////////////////////////////////////////////////////////
    public static boolean isForm(String text) {
        if (text == null || text.isEmpty() || text.charAt(0) != '=') { // Ensure it starts with '='
            return false;
        }

        String formula = text.substring(1).trim(); // Remove '=' at the start

        if (!areParenthesesBalanced(formula)) { // Check balanced parentheses
            return false;
        }

        boolean expectOperand = true; // Whether an operand is expected
        for (int i = 0; i < formula.length(); i++) {
            char current = formula.charAt(i);

            if (Character.isWhitespace(current)) { // Skip spaces
                continue;
            }

            if (expectOperand) {
                if (current == '+' || current == '-') { // Allow unary operators
                    continue;
                }
                if (Character.isDigit(current)) { // Handle numbers
                    while (i + 1 < formula.length() && (Character.isDigit(formula.charAt(i + 1)) || formula.charAt(i + 1) == '.')) {
                        i++;
                    }
                    expectOperand = false;
                } else if (Character.isLetter(current)) { // Handle cell references
                    while (i + 1 < formula.length() && Character.isLetterOrDigit(formula.charAt(i + 1))) {
                        i++;
                    }
                    expectOperand = false;
                } else if (current == '(') { // Handle opening parenthesis
                    expectOperand = true;
                } else {
                    return false; // Invalid character for an operand
                }
            } else { // Expect operator or closing parenthesis
                if (isOperator(current)) { // Handle operators
                    expectOperand = true;
                } else if (current == ')') { // Handle closing parenthesis
                    expectOperand = false;
                } else {
                    return false; // Invalid character for this position
                }
            }
        }

        return !expectOperand; // Ensure the formula ends with an operand
    }

}