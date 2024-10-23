package ph.edu;

import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String choice = "";

        while (!choice.equals("C")) {
            System.out.println("\nWhat would like to do?");
            System.out.println("[A] Convert an infix expression to postfix expression");
            System.out.println("[B] Evaluate a postfix expression");
            System.out.println("[C] Exit");
            System.out.print("Input choice: ");
            choice = scanner.nextLine();

            if (choice.equals("A")) {
                System.out.print("Input infix expression: ");
                String infixExpression  = scanner.nextLine();
                String postfixExpression = convertToPostfix(infixExpression);
                System.out.println("Postfix expression: " + postfixExpression);
            }
            else if (choice.equals("B")) {
                System.out.print("Input postfix expression: ");
                String postfixExpression = scanner.nextLine();
                int result = evaluatePostfix(postfixExpression);
                System.out.println("Value: " + result);
            }

            else if (choice.equals("C")) {
                System.out.println("You have exited the program.");
            }
            else {
                System.out.println("Invalid input. Try again.");
            }
        }
    }

    private static String convertToPostfix(String infixExpression) {
        StringBuilder postfixExpression  = new StringBuilder();
        StringBuilder operand = new StringBuilder();

        Stack<String> stack = new Stack<String>();

        for (int index = 0; index < infixExpression.length(); index++) {
            char currentChar = infixExpression.charAt(index);

            if (currentChar == '+' || currentChar == '-' || currentChar == '*' || currentChar == '/') {
                appendOperand(operand, postfixExpression);

                // Pop operators from the stack with higher or equal precedence
                while (!stack.empty() && equivalentValue(String.valueOf(currentChar)) <= equivalentValue(stack.peek())) {
                    postfixExpression.append(" ");
                    postfixExpression.append(stack.pop());
                }
                stack.push(String.valueOf(currentChar)); // Push current operator
            }

            else if (currentChar == '(') {
                stack.push(String.valueOf(currentChar)); // Push open parenthesis
            }

            else if (currentChar == ')') {
                appendOperand(operand, postfixExpression);

                while (!stack.peek().equals("(")) { // Pop all operators until opening parenthesis is found
                    postfixExpression.append(" ");
                    postfixExpression.append(stack.pop());
                }
                stack.pop(); // Remove the '(' from stack
            }

            else if (currentChar == ' ') {
                appendOperand(operand, postfixExpression);
            }

            else {
                operand.append(currentChar); // Assume it's part of an operand (e.g., digit or variable)
            }
        }
        appendOperand(operand, postfixExpression); // append last operand if any


        while (!stack.empty()) { // Pop all remaining operators from the stack
            postfixExpression.append(" ");
            postfixExpression.append(stack.pop());
        }

        return postfixExpression.toString().trim();
    }

    private static void appendOperand(StringBuilder operand, StringBuilder postfixExpression) {
        if (!operand.isEmpty()) {
            postfixExpression.append(" ");
            postfixExpression.append(operand);
            operand.setLength(0); // Clear operand without changing the reference
        }
    }

    private static int equivalentValue(String expression) {
        if (expression.equals("+") || expression.equals("-")) {
            return 1;
        }
        else if (expression.equals("*") || expression.equals("/")) {
            return 2;
        }
        else {
            return 0;
        }
    }

    private static int evaluatePostfix(String postfixExpression) {
        Stack<Integer> stack = new Stack<Integer>();
        int num1;
        int num2;

        for (int index = 0; index < postfixExpression.length(); index++) {
            char currentChar = postfixExpression.charAt(index);

            if (currentChar == ' ') {
                continue;
            }

            else if (Character.isDigit(currentChar)) {
                int startingIndex = index;
                index++;
                while (Character.isDigit(postfixExpression.charAt(index))) {
                    index++;
                }

                int num = Integer.parseInt(postfixExpression.substring(startingIndex, index));
                stack.push(num);
            }

            else {
                num1 = stack.pop();
                num2 = stack.pop();

                switch (currentChar) {
                    case '+':
                        stack.push(num2 + num1);
                        break;
                    case '-':
                        stack.push(num2 - num1);
                        break;
                    case '*':
                        stack.push(num2 * num1);
                        break;
                    case '/':
                        stack.push(num2 / num1);
                        break;

                }

            }
        }
        return stack.pop();
    }

}