package evaluator;

import parser.ArithParser;
import stack.LinkedStack;

public class InfixEvaluator extends Evaluator {

  private LinkedStack<String> operators = new LinkedStack<String>();
  private LinkedStack<Integer> operands  = new LinkedStack<Integer>();

  /** return stack object (for testing purpose). */
  public LinkedStack<String> getOperatorStack() { 
    return operators; 
  }
  
  public LinkedStack<Integer> getOperandStack() { 
    return operands;
  }


  /** This method performs one step of evaluation of a infix expression.
   *  The input is a token. Follow the infix evaluation algorithm
   *  to implement this method. If the expression is invalid, throw an
   *  exception with the corresponding exception message.
   */
  public void evaluate_step(String token) throws Exception {
   
    if (isOperand(token)) {
      operands.push(Integer.parseInt(token));
    } else {
      if (token.equals("(") || operators.size() == 0 || precedence(token) > precedence(operators.top()))  {
        operators.push(token);
      } else if (token.equals(")")) {
        while (!operators.top().equals("(")) {
          if (operators.size() == 1) {
            throw new Exception("missing (");
          }
          process_operator();
        }
        operators.pop();
      } else {
        while (operators.size() > 0 && !(precedence(token) > precedence(operators.top()))) {
          process_operator();
        }
        operators.push(token);
      }
    }
  }
  
  /**
   * Does operator math. Void return statement
   * @throws Exception Throws exceptions when needed
   */
  public void process_operator() throws Exception {
    
    if (operands.size() < 1 && operators.top().equals("!")) {
      throw new Exception("too few operands");
    } else if (operands.size() < 2 && !(operators.top().equals("!"))) {
      throw new Exception("too few operands");
    }
    
    switch (operators.pop()) {
      case "+":
        int a = operands.pop();
        int b = operands.pop(); 
        operands.push(a + b);
        break;
    
      case "-":
        a = operands.pop();
        b = operands.pop();
        operands.push(b - a);
        break;
    
      case "*":
        a = operands.pop();
        b = operands.pop();
        operands.push(a * b);
        break;
    
      case "/":
        a = operands.pop();
        b = operands.pop();
        if (a == 0) {
          throw new Exception("division by zero");
        }
        operands.push(b / a);
        break;
    
      case "!":
        a = operands.pop();
        operands.push(-1 * a);
        break;
    
      default:
        throw new Exception("invalid operator");
    }
  }
  
  /** This method evaluates an infix expression (defined by expr)
   *  and returns the evaluation result. It throws an Exception object
   *  if the infix expression is invalid.
   */
  public Integer evaluate(String expr) throws Exception {
    
    for (String token : ArithParser.parse(expr)) {
      evaluate_step(token);
    }

    while (operators.size() > 0) {
      process_operator();
    }

    // The operand stack should have exactly one operand after the evaluation is done
    if (operands.size() > 1) {
      throw new Exception("too many operands");
    } else if (operands.size() < 1) {
      throw new Exception("too few operands");
    }

    return operands.pop();
  }

  public static void main(String[] args) throws Exception {
    System.out.println(new InfixEvaluator().evaluate("5+(5+2*(5+9))/!8"));
  }
}