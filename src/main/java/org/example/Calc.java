package org.example;
import java.util.*;

/**
 * Calculating class
 */
public  class Calc
{
    String infixExpr ;
    String postfixExpr ;
    Map<Character, Integer> operationPriority = new HashMap<>();

    /**
     * Constructor - creating a new object
     * @param expression line with expression
     */
    public Calc(String expression) {
        this.infixExpr = expression;
        this.postfixExpr = ToPostfix(infixExpr );
        //operationPriority.put('(', 0);
        operationPriority.put('+', 1);
        operationPriority.put('-', 1);
        operationPriority.put('*', 2);
        operationPriority.put('/', 2);
        operationPriority.put('^', 3);
        operationPriority.put('~', 4);
    }

    /**
     *
     * @param expr line with expression
     * @param pos sequence number in the string
     * @return returns a number with multiple digits
     */
    public String GetStringNumber(String expr, int pos) {
        String strNumber = "";
        for (; pos < expr.length(); pos++)
        {
            char num = expr.charAt(pos);
            if (Character.isDigit(num))
                strNumber += num;
            else
            {
                pos--;
                break;
            }
        }
        return strNumber;
    }

    /**
     * Perform the conversion
     *
     * @param infixExpr Arithmetic infix format
     */
    public String ToPostfix(String infixExpr) {
        String postfixExpr = "";
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < infixExpr.length(); i++) {
            char c = infixExpr.charAt(i);

            if (Character.isDigit(c)) {
                postfixExpr += GetStringNumber(infixExpr,  i) + " ";
            }
            else if (c == '(') {
                stack.push(c);
            }
            else if (c == ')') {
                while (stack.size() > 0 && stack.peek() != '(')
                    postfixExpr += stack.pop();
                stack.pop();
            }

            else if (c=='*' || c== '+' || c=='-'|| c=='/' || c=='^') {
                char op = c;
                if (op == '-' && (i == 0 || (i > 1 && operationPriority.containsKey(infixExpr.charAt(i - 1)))))
                    op = '~';
                int cur,cur_s;

                if(op=='+' || op=='-'){cur=1;}
                else if(op=='*'|| op=='/'){cur=2;}
                else if(op=='^'){cur=3;}
                else cur=4;
                for(int j=0;stack.size() > 0 && j>0;){
                    char token=stack.peek();
                    if(token =='+' || token =='-'){cur_s=1;}
                    else if(token=='*'|| token=='/'){cur_s=2;}
                    else if(token=='^'){cur_s=3;}
                    else cur_s=4;
                    if(cur<=cur_s)stack.pop();
                    else j++;
                }
                stack.push(op);
            }
        }
        while(stack.size() > 0)
            postfixExpr += stack.pop();
        return postfixExpr;
    }

    /**
     *
     * @param op operation method
     * @param first number
     * @param second number
     * @return calculation result
     */
    public double Execute(char op,double first,double second){
        switch(op){
            case('+'):{
                return first+second;
                //break;
            }
            case('-'):{
                return first-second;
                //break;
            }
            case('*'): {
                return first*second;
                //break;
            }
            case('/'):{
                return first/second;
                // break;
            }
            case('^'):{
                return Math.pow(first,second);
                // break;
            }
            default:{
                throw new RuntimeException("Unexpected token");
            }
        }
    }

    /**
     *
     * @return the result of evaluating the entire expression
     */
    public double calculate()
    {
        Stack<Double> locals = new Stack<>();
        int counter = 0;
        for (int i = 0; i < postfixExpr.length(); i++)
        {
            char c = postfixExpr.charAt(i);
            if (Character.isDigit(c))
            {
                String number = GetStringNumber(postfixExpr,  i);
                locals.push(Double.valueOf(number));
            }
            else if (operationPriority.containsKey(c))
            {
                counter += 1;
                if (c == '~')
                {
                    double last = locals.size() > 0 ? locals.pop() : 0;
                    locals.push(Execute('-', 0, last));
                    continue;
                }
                double second = locals.size() > 0 ? locals.pop() : 0,
                        first = locals.size() > 0 ? locals.pop() : 0;
                locals.push(Execute(c, first, second));
            }
        }
        return locals.pop();
    }
}
