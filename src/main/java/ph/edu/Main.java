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
                postfixExpression.append(" ");
                postfixExpression.append(operand); // Append any operand before operator
                operand = new StringBuilder(); // Reset operand

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
                postfixExpression.append(" ");
                postfixExpression.append(operand); // Append any operand before the ")"
                operand = new StringBuilder(); // Reset operand

                while (!stack.peek().equals("(")) { // Pop all operators until opening parenthesis is found
                    postfixExpression.append(stack.pop());
                }
                stack.pop(); // Remove the '(' from stack
            }

            else if (currentChar == ' ') {
                postfixExpression.append(operand); // Append operand
                operand = new StringBuilder(); // Reset
            }

            else {
                operand.append(currentChar); // Assume it's part of an operand (e.g., digit or variable)
            }
        }
        postfixExpression.append(" ");
        postfixExpression.append(operand); // Append the last operand to the result


        while (!stack.empty()) { // Pop all remaining operators from the stack
            postfixExpression.append(" ");
            postfixExpression.append(stack.pop());
        }

        return postfixExpression.toString(); // Corrected return statement
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
        String[] tokens = postfixExpression.split(" ");
        Stack<Integer> numbers = new Stack<Integer>();
        int num1;
        int num2;

        for (String expression : tokens) {
            if (expression.equals("+")) {
                num1 = numbers.pop();
                num2 = numbers.pop();
                numbers.push(num2 + num1);
            }
            else if (expression.equals("-")) {
                num1 = numbers.pop();
                num2 = numbers.pop();
                numbers.push(num2 - num1);
            }
            else if (expression.equals("*")) {
                num1 = numbers.pop();
                num2 = numbers.pop();
                numbers.push(num2 * num1);
            }
            else if (expression.equals("/")) {
                num1 = numbers.pop();
                num2 = numbers.pop();
                numbers.push(num2 / num1);
            }
            else {
                numbers.push(Integer.parseInt(expression));
            }
        }

        return numbers.pop();
    }

}