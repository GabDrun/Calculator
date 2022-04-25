import Operations.*;

public class Logic {
    private String expression;

    public Logic(String expression) {
        setExpression(expression);
    }

    public String getExpression() { return expression; }
    public void setExpression(String expression) { this.expression = expression; }

    public static double calculate(final String str) {
        try {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (; ; ) {
                    if (eat('+')) {
                        Addition add = new Addition(x, parseTerm());
                        x = add.calculate();
                    } else if (eat('-')) {
                        Subtraction subtract = new Subtraction(x, parseTerm());
                        x = subtract.calculate();
                    } else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (; ; ) {
                    if (eat('*')) {
                        Multiplication multiply = new Multiplication(x, parseTerm());
                        x = multiply.calculate();
                    } else if (eat('/')) {
                        Division divide = new Division(x, parseTerm());
                        x = divide.calculate();
                    } else if (eat('%')) {
                        Modulus modulus = new Modulus(x, parseTerm());
                        x = modulus.calculate();
                    } else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return +parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { //checks parentheses
                    x = parseExpression();
                    if (!eat(')')) throw new RuntimeException("Missing ')'");
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { //checks for numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // checks for functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    if (eat('(')) {
                        x = parseExpression();
                        if (!eat(')')) throw new RuntimeException("Missing ')' after argument to " + func);
                    } else {
                        x = parseFactor();
                    }
                    //if new functions added (sqrt, sin, cos...) add new if statements below
                    if (!func.equals("")) throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected character");
                }

                return x;
            }
        }.parse();

    } catch(Exception e) {
            System.out.println(e);
            return 0;
        }
    }
}
