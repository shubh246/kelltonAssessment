import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Calculator {

    public static void main(String[] args) {
        ArrayList test = new ArrayList();
        Scanner f = new Scanner(System.in);

        System.out.println("Type and equation, then press enter: ");

        String g = f.nextLine();

        test = inputToArrayList(g);

        for (int z = 0; z < test.size(); z++) {
            System.out.println(test.get(z));
        }

        System.out.println(calculateAnswer(test));
    }

    public static class SyntaxErrorException extends Exception {

        SyntaxErrorException(String message) {
            super(message);
        }
    }
    private static final Stack<Double> operandStack = new Stack<Double>();
    private static final Stack<String> operatorStack = new Stack<String>();
    private static final String OPERATORS = "+-/*%^()u";
    private static final String NONBRACES = "+-/*%^u";
    private static final int[] PRECEDENCE = {1, 1, 2, 2, 2, 3, 3, 3, 4};

    static ArrayList<String> charList = new ArrayList<String>();

    public static ArrayList inputToArrayList(String input) {
        StringBuilder strBuild = new StringBuilder();
        String infix = input.replace(" ", "");
        try {
            for (int i = 0; i < infix.length(); i++) {
                char c = infix.charAt(i);

                boolean isNumber = (c >= '0' && c <= '9');
//------------HERE IS WHERE I HANDLE - AT BEGINNING OF INPUT, SAVED AS U INSTEAD OF -   ----------
                if (i == 0 && c == '-') {
                    isNumber = true;
                    charList.add("u");
                    continue;
                }

                if (isNumber) {
                    strBuild.append(c);
                    if (i == infix.length() - 1) {
                        charList.add(strBuild.toString());
                        strBuild.delete(0, strBuild.length());
                    }
                } else if (c == '.') {
                    for (int j = 0; j < strBuild.length(); j++) {
                        if (strBuild.charAt(j) == '.') {
                            throw new SyntaxErrorException("You can't have two decimals in a number");
                        } else if (j == strBuild.length() - 1) {
                            strBuild.append(c);
                            j = (strBuild.length() + 1);
                        }
                    }
                    if (strBuild.length() == 0) {
                        strBuild.append(c);
                    }
                    if (i == infix.length() - 1) {
                        throw new SyntaxErrorException("You can't end your equation with a decimal");
                    }
                } else if (OPERATORS.indexOf(c) != -1) {
                    if (strBuild.length() != 0) {
                        charList.add(strBuild.toString());
                        strBuild.delete(0, strBuild.length());
                    }
                    strBuild.append(c);
                    charList.add(strBuild.toString());
                    strBuild.delete(0, strBuild.length());
                } else {
                    throw new SyntaxErrorException("Make sure your input only contains numbers, operators, or parantheses");
                }
            }

            int leftParenth = 0;
            int rightParenth = 0;

            for (int p = 0; p < charList.size(); p++) {
                String checkParenth = charList.get(p);

                switch (checkParenth) {
                    case "(":
                        leftParenth++;
                        break;
                    case ")":
                        rightParenth++;
                        break;
                    default: //do nothing
                        break;
                }

            }
            if (leftParenth != rightParenth) {
                throw new SyntaxErrorException("There is not an even number of parenthesis");
            }

            int parenthesis = 0;

            for (int f = 0; f < charList.size(); f++) {
                String awesome = charList.get(f);
                switch (awesome) {
                    case "(":
                        parenthesis++;
                        break;
                    case ")":
                        parenthesis--;
                        break;
                    default:
                        break;
                }
                if (parenthesis < 0) {
                    throw new SyntaxErrorException("Order of parenthesis is off");
                }
            }
            if (NONBRACES.contains(charList.get(charList.size() - 1))) {
                throw new SyntaxErrorException("The input can't end in an operator");
            }
            return charList;
        } catch (SyntaxErrorException ex) {
            System.out.println(ex);
            return charList;
        }
    }

    private static void processOperator(String op) {
        if (operatorStack.empty() || op.equals("(")) {
            operatorStack.push(op);
        } else {
            //peek the operator stack and
            //let topOp be the top operator.
            String topOp = operatorStack.peek();
            if (precedence(op) > precedence(topOp)) {
                if (!op.equals(")")) {
                    operatorStack.push(op);
                }
            } else {
                //Pop all stacked operators with equal
                // or higher precedence than op.
                while (!operatorStack.empty() && precedence(op) >= precedence(topOp)) {
 //-------------HERE IS WHERE I TRY TO HANDLE UNARY - and "u"----------------------------                   
                    if("u".equals(operatorStack.peek())) {
                        double right = operandStack.pop();
                        String unary = operatorStack.pop();
                        operandStack.push(right * (-1));
                        break;
                    }
                    double right = operandStack.pop();
                    double left = operandStack.pop();
                    String operator = operatorStack.pop();
                    switch (operator) {
                        case "+":
                            operandStack.push(left + right);
                            break;
                        case "-":
                            operandStack.push(left - right);
                            break;
                        case "*":
                            operandStack.push(left * right);
                            break;
                        case "/":
                            operandStack.push(left / right);
                            break;
                        case "%":
                            operandStack.push(left % right);
                            break;
                        case "^":
                            operandStack.push(Math.pow(left, right));
                            break;
                        default: //do nothing, but this should never happen
                            break;
                    }

                    if (topOp.equals("(")) {
                        //matching '(' popped - exit loop.
                        operandStack.push(left);
                        operandStack.push(right);
                        break;
                    }

                    if (!operatorStack.empty()) {
                        //reset topOp
                        topOp = operatorStack.peek();
                    }
                }

                //assert: Operator stack is empty or
                // current operator precedence > top of stack operator precedence.
            }
        }
    }

    public static String calculateAnswer(ArrayList<String> infix) {
        int p;
        for (p = 0; p < infix.size(); p++) {
            if (!OPERATORS.contains(infix.get(p))) {
                double listIndex = Double.parseDouble(infix.get(p));
                operandStack.push(listIndex);
            } else {
                processOperator(infix.get(p));
            } }
        if (p == infix.size()) {
            while (!operatorStack.empty()) {
                if ("u".equals(operatorStack.peek())) {

                    double right = operandStack.pop();
                    String unary = operatorStack.pop();
                    operandStack.push(right * (-1));
                    break;
                }
                double right = operandStack.pop();
                double left = operandStack.pop();
                String current = operatorStack.pop();
                switch (current) {
                    case "+":
                        operandStack.push(left + right);
                        break;
                    case "-":
                        operandStack.push(left - right);
                        break;
                    case "*":
                        operandStack.push(left * right);
                        break;
                    case "/":
                        operandStack.push(left / right);
                        break;
                    case "%":
                        operandStack.push(left % right);
                        break;
                    case "^":
                        operandStack.push(Math.pow(left, right));
                        break;
                    default: //do nothing, but this should never happen
                        break;
                }
            }
        }
        return String.valueOf(operandStack.pop());
    }

    private static int precedence(String op) {
        return PRECEDENCE[OPERATORS.indexOf(op)];
    }}