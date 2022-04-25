public class Main {

    public static void main(String[] args) {
        String[] hardOperations = {"+", "-", "*", "/", "^", "(", ")", "sin(", "cos(", "tg(", "ctg(", "sqrt(", "log(", "ln(", "!"}; // Hardcore Mode (not implemented)
        String[] realisticOperations = {"+", "-", "*", "/", "%", "(", ")"};
        Logic logic = new Logic("");

        MyCalculator calculator = new MyCalculator("Calculator", realisticOperations, logic);
    }
}