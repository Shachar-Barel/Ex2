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
        line = s;
        if (isNumber(s)) {
            setType(Ex2Utils.NUMBER);
        } else if (isText(s)) {
            setType(Ex2Utils.TEXT);
        } else if (isForm(s)) {
            setType(Ex2Utils.FORM);
        } else {
            setType(Ex2Utils.ERR);
        }
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
        return  1+ count;
    }
    @Override
    public int getOrder() {
        if (type == Ex2Utils.NUMBER || type == Ex2Utils.TEXT) {
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
        if (text == null || text.isEmpty() || text.charAt(0)== '=')
            return false;
        String textRegex = ".*[a-zA-Z{}].*"; // regex to check if it's a text
        return text.matches(textRegex);
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

        if (text == null || text.isEmpty() || text.charAt(0) != '=') {//check if the input is null or doesn't start with =
            return false;
        }

        String formula = text.substring(1).trim();//remove the '=' at the start

        if (!areParenthesesBalanced(formula)) {//check if the parentheses are balanced
            return false;
        }
        boolean expectOperand = true;
        for (int i = 0; i < formula.length(); i++) {
            char current = formula.charAt(i);

            if (Character.isWhitespace(current)) {//skip spaces
                continue;
            }
            if (expectOperand) {

                if (Character.isDigit(current)) {
                    while (i + 1 < formula.length() && Character.isDigit(formula.charAt(i + 1))) {
                        i++;
                    }
                    expectOperand = false;
                } else if (Character.isLetter(current)) {//check if it's a cell reference
                    while (i + 1 < formula.length() && Character.isLetterOrDigit(formula.charAt(i + 1))) {
                        i++;
                    }
                    expectOperand = false;
                } else if (current == '(') {//check if it's an opening parenthesis
                    expectOperand = true;
                } else {
                    return false;
                }
            } else {
                if (isOperator(current)) {
                    expectOperand = true; //expect another operand
                } else if (current == ')') {
                    expectOperand = false; //doesn't expect an operand right after
                } else {
                    return false;
                }
            }
        }
        return !expectOperand;
    }
}