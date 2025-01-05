
import java.util.regex.Pattern;


public class Cell {
   public boolean isNumber(String text)
    {
     if (text == null || text.isEmpty())
        return false;
        String numberRegex = "^-?\\d+(\\.\\d+)?$";
        return text.matches(numberRegex);
    }

    public boolean isText(String text)
    {
        if (text == null || text.isEmpty())
            return false;
        String textRegex = ".*[a-zA-Z{}].*";
        return text.matches(textRegex);
    }


    public boolean isForm(String text){
        if (text == null || text.isEmpty())
            return false;
        String formRegex = "^=(?:-?\\d+(\\.\\d+)?|[A-Z]{1,2}\\d+|\\([^)]+\\))([+*/-](?:-?\\d+(\\.\\d+)?|[A-Z]{1,2}\\d+|\\([^)]+\\)))*$\n"; // להסביר
        return text.matches(formRegex);

    }


    public double basicCalculator(String s) {
       double ans=0;
        String[] numbers = s.split("([+\\-*/])", 2); // פיצול המספרים
        String operator = s.replaceAll("[0-9.]", ""); // מציאת האופרטור
        double num1 = Double.parseDouble(numbers[0].trim());
        double num2 = Double.parseDouble(numbers[1].trim());
        switch (operator) {
            case "/" -> {
                ans= num1 / num2;
            }
            case "*" -> {
                ans= num1 * num2;
            }
            case "+" -> {
                ans= num1 + num2;
            }
            case "-" -> {
                ans= num1 - num2;
            }
        }
        return ans;
    }
        public double calculateRemaining(String s) {
            String[] tokens = s.split("(?=[*/])|(?<=[*/])"); // פיצול לפי כפל וחילוק
            double result = basicCalculator(tokens[0]); // מחשבים את החלק הראשון
            for (int i = 1; i < tokens.length; i += 2) {
                String operator = tokens[i];
                double num = basicCalculator(tokens[i + 1]);
                if (operator.equals("*")) {
                    result *= num;
                } else if (operator.equals("/")) {
                    result /= num;
                }
            }
            tokens = String.valueOf(result).split("(?=[+-])|(?<=[+-])"); // פיצול לפי חיבור וחיסור
            result = basicCalculator(tokens[0]); // מחשבים את החלק הראשון
            for (int i = 1; i < tokens.length; i += 2) {
                String operator = tokens[i];
                double num = basicCalculator(tokens[i + 1]);
                if (operator.equals("+")) {
                    result += num;
                } else if (operator.equals("-")) {
                    result -= num;
                }
            }
            return result;
        }
        public double computeForm(String form){
            while (form.contains("(")) {
                int openIndex = form.lastIndexOf("("); //סוגר פותח הכי פנימי
                int closeIndex = form.indexOf(")", openIndex); //סוגר סוגר המתאים
                String innerExpression = form.substring(openIndex + 1, closeIndex);//חישוב הביטוי הכי פנימי
                double innerResult = basicCalculator(innerExpression);
                form = form.substring(0, openIndex) + innerResult + form.substring(closeIndex + 1);
            }

            return calculateRemaining(form);
        }
    }
