
public class firstPartCell {
    public static boolean isNumber(String text) {
        if (text == null || text.isEmpty())
            return false;
        try {
            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isText(String text) {
        if (text == null || text.isEmpty())
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

                if (Character.isDigit(current)) { //check if it's a number
                    while (i + 1 < formula.length() && Character.isDigit(formula.charAt(i + 1))) {//kip over the full number
                        i++;
                    }
                    expectOperand = false;
                } else if (Character.isLetter(current)) {//check if it's a cell reference
                    while (i + 1 < formula.length() && Character.isLetterOrDigit(formula.charAt(i + 1))) { //check if followed by numbers and the letter is not the last char
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
        while (form.contains("(")) {
            int openIndex = form.lastIndexOf("(");
            int closeIndex = form.indexOf(")", openIndex);
            String innerExpression = form.substring(openIndex + 1, closeIndex);
            double innerResult = calculateRemaining(innerExpression);
            form = String.valueOf(form.substring(0, openIndex)) + Double.toString(innerResult) + String.valueOf(form.substring(closeIndex + 1));
        }
        return calculateRemaining(form);
    }
}
