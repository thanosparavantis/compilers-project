import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;

public class LL1_Parser {

    private String input = "", leftAlignFormat;
    private int indexOfInput = -1;

    private Stack<String> parserStack;

    // Syntax array
    String[][] syntaxArray = {
            {"[A]", null, null, null, null, null, null},
            {"BE" , null, null, null, "BE", "BE", null},
            {"S"  , null, null, null, "x" , "y" , null},
            {null , ""  , ":A", "+A", null, null, null}
    };

    // Terminal symbols
    private String[] termSymbols = {"[", "]", ":", "+", "x", "y", "$"};

    // Non terminal symbols
    private String[] nonTermSymbols = {"S", "A", "B", "E"};

    public LL1_Parser(String input) {
        this.input = input;

        // Initialize the stack
        parserStack = new Stack<String>();

        leftAlignFormat = "| %-29s | %-24s | %-32s | %-29s |%n";

    }

    public void parsingAlgorithm() {
        this.pushInStack("$");
        this.pushInStack("S");

        String token = this.read();
        String top = null, rule = null;

        System.out.format("+-------------------------------+--------------------------+----------------------------------+-------------------------------+%n");
        System.out.format("| Stack                         | Input                    | Array Element                    | Production                    |%n");
        System.out.format("+-------------------------------+--------------------------+----------------------------------+-------------------------------+%n");

        do {

            top = this.peekOfStack();

            if (this.isTerminal(top)) {

                if (top.equals(token) && !token.equals("$")) {

                    this.printTableRow();
                    this.popOutOfStack();
                    token = this.read();

                } else if (top.equals(token) && top.equals("$")) {

                    this.printTableRow();
                    System.out.println("The string has been recognized!");
                    break;

                }
            } else {
                rule = this.getRuleFromArray(top, token);
                if (!rule.equals("")) {

                    this.printTableRow(top, rule, token);
                    this.popOutOfStack();
                    this.pushRuleInStack(rule);

                } else {

                    this.printTableRow(top, rule, token);
                    this.popOutOfStack();

                }
            }

        } while(true);
    }

    // Pushes rule in stack
    private void pushRuleInStack(String rule) {
        for (int i = rule.length()-1; i >= 0; i--) {
            char character = rule.charAt(i);
            this.parserStack.push(String.valueOf(character));
        }
    }

    // Checks if given parameter is a terminal symbol
    private boolean isTerminal(String inputString) {
        for (String symbol : this.termSymbols) {
            if (inputString.equals(symbol))
                return true;
        }
        return false;
    }

    // Reads, each time, the next symbol of the given input
    // and return it as string
    private String read() {
        this.indexOfInput++;

        char symbol = this.input.charAt(this.indexOfInput);

        return String.valueOf(symbol);
    }

    // Logs error in console
    private void errorLogger(String errorMsg) {
        System.out.println(errorMsg);
        throw new RuntimeException(errorMsg);
    }

    // Retrieve rule
    private String getRuleFromArray(String nonTerminal, String terminal) {
        int arrayRow = this.getNonTermSymbolIndex(nonTerminal);
        int arrayCol = this.getTermSymbolIndex(terminal);

        String rule = this.syntaxArray[arrayRow][arrayCol];

        if (rule == null)
            errorLogger("There is no matching rule for non terminal symbol: " + nonTerminal + " and terminal symbol: " + terminal);

        return rule;
    }

    // Get terminal symbol index
    private int getTermSymbolIndex(String inputString) {
        int i = 0;

        for (String symbol : this.termSymbols) {
            if (inputString.equals(symbol))
                return i;

            i++;
        }

        this.errorLogger(inputString + " is not terminal symbol");
        return -1;
    }

    // Get non terminal symbol index
    private int getNonTermSymbolIndex(String inputString) {
        int i = 0;

        for (String symbol : this.nonTermSymbols) {
            if (inputString.equals(symbol))
                return i;

            i++;
        }

        this.errorLogger(inputString + " is terminal symbol");
        return -1;
    }

    // Print table row
    private void printTableRow() {
        String stack = this.parserStack.toString()
                .substring(0, this.parserStack.toString().length() - 1)
                .substring(1)
                .replace(",", "")
                .replace(" ","");

        System.out.format(leftAlignFormat, stack, this.input.substring(this.indexOfInput), "", "");
        System.out.format("+-------------------------------+--------------------------+----------------------------------+-------------------------------+%n");
    }

    // Print table row
    private void printTableRow(String top, String rule, String token){
        String  productionStr =  top + "-> " + (rule.equals("") ? "ε" : rule),
                arrayElement = "syntaxArray("+ top +", " + token + ") ",
                stack = this.parserStack.toString()
                        .substring(0, this.parserStack.toString().length() - 1)
                        .substring(1)
                        .replace(",", "")
                        .replace(" ","");

        System.out.format(this.leftAlignFormat, stack, this.input.substring(this.indexOfInput), arrayElement, productionStr);
        System.out.format("+-------------------------------+--------------------------+----------------------------------+-------------------------------+%n");
    }

    // Returns the element at the peek of the stack
    private String peekOfStack() {
        return this.parserStack.peek();
    }

    // Removes and returns the element at the the peek of the stack
    private String popOutOfStack() { return this.parserStack.pop(); }

    // Push an element at the top of the stack
    private void pushInStack(String string) {
        this.parserStack.push(string);
    }

    public static void main(String[] args) {

        Scanner sc;
        int menuSelection;
        LL1_Parser parser;
        System.out.println("================ MENU ================");
        System.out.println("1. Execute the string [[y:x]+[x:y]]");
        System.out.println("2. Type a string of your choice");
        System.out.println("======================================");

        do {
            System.out.println("Select 1 or 2");
            sc = new Scanner(System.in);
            try {
                menuSelection = sc.nextInt();
            }catch (InputMismatchException e) {
                menuSelection = 0;
            }
        } while ((menuSelection < 1) || (menuSelection > 2));
        if (menuSelection == 1) {
            // Test following expression: [[y:x]+[x:y]]
            parser = new LL1_Parser("[[y:x]+[x:y]]$");
        } else {
            // [[y+x]:y]
            System.out.println("Type a string");
            String userInput = sc.next();
            parser = new LL1_Parser(userInput + "$");
        }

        try {
            parser.parsingAlgorithm();
        } catch (RuntimeException e) {
            System.out.println("The string has not been recognized!");
        }

    }
}
