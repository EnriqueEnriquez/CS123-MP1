package ph.edu;

import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What would like to do?");
        System.out.println("[A] Convert an infix expression to postfix expression");
        System.out.println("[B] Evaluate a postfix expression");
        System.out.print("Input choice: ");
        String choice = scanner.nextLine();

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

    }

    private static String convertToPostfix(String infixExpression) {
        StringBuilder postfixExpression = new StringBuilder();
        Stack<String> stack = new Stack<String>();
        String[] tokens = infixExpression.split(" ");

        for (String expression : tokens) {
            if (expression.equals("+") || expression.equals("-") || expression.equals("*") || expression.equals("/")) {
                while(!stack.empty() && (equivalentValue(expression) <= equivalentValue(stack.peek()))) {
                    postfixExpression.append(stack.pop());
                    postfixExpression.append(" ");
                }
                stack.push(expression);
            }
            else if (expression.equals("(")) {
                stack.push(expression);
            }
            else if (expression.equals(")")) {
                while(!stack.peek().equals("(")) {
                    postfixExpression.append(stack.pop());
                    postfixExpression.append(" ");
                }
                stack.pop(); // pops the ( from the stack
            }
            else {
                postfixExpression.append(expression);
                postfixExpression.append(" ");
            }
        }

        while (!stack.empty()) {
            postfixExpression.append(stack.pop());
        }

    return postfixExpression.toString();
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