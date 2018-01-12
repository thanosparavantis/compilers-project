import java.awt.*;
import java.util.Scanner;
import java.util.Stack;

public class LL1_Parser {

    private String input = "";
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
    }

    public void parsingAlgorithm() {
        this.pushInStack("$");
        this.pushInStack("S");

        String token = this.read();
        String top = null, rule = null, productionStr = null, arrayElement = null;

        String leftAlignFormat = "| %-29s | %-24s | %-32s | %-29s |%n";
        System.out.format("+-------------------------------+--------------------------+----------------------------------+-------------------------------+%n");
        System.out.format("| Stack                         | Input                    | Array Element                    | Production                    |%n");
        System.out.format("+-------------------------------+--------------------------+----------------------------------+-------------------------------+%n");

        do {
            top = this.peekOfStack();

            if (this.isTerminal(top)) {

                if (top.equals(token) && !token.equals("$")) {
                    this.popOutOfStack();
                    token = this.read();
                    top ="";
                } else if (top.equals(token) && top.equals("$")) {
                    System.out.println("The given expression is valid LL1");
                    break;
                }
            } else {
                rule = this.getRuleFromArray(top, token);
                if (!rule.equals("")) {
                    this.popOutOfStack();
                    this.pushRuleInStack(rule);
                } else {
                    this.popOutOfStack();
                }
            }

            if (top.equals("")) {
                productionStr = "";
                arrayElement = "";
            } else {
                productionStr =  top + "-> " + rule;
                arrayElement = "syntaxArray("+ top +", " + token + ") ";
            }

            System.out.format(leftAlignFormat, this.parserStack, this.input.substring(this.indexOfInput), arrayElement, productionStr);
            System.out.format("+-------------------------------+--------------------------+----------------------------------+-------------------------------+%n");

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

        this.errorLogger(inputString + "is not terminal symbol");
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

        this.errorLogger(inputString + "is terminal symbol");
        return -1;
    }

    private String peekOfStack() {
        return this.parserStack.peek();
    }

    private String popOutOfStack() {
        return this.parserStack.pop();
    }

    private void pushInStack(String string) {
        this.parserStack.push(string);
    }

    public static void main(String[] args) {
        // Test following expression: [[y:x]+[x:y]]
        LL1_Parser parser = new LL1_Parser("[[y:x]+[x:y]]$");
        parser.parsingAlgorithm();
    }
}
