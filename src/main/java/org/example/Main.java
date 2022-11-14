
package org.example;
import java.util.Scanner;

public class Main {

    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter your expression: ");
        String expression = in.nextLine();
        Calc mather = new Calc(expression);
        System.out.print("Ввод: " + mather.infixExpr + '\n');
        System.out.print("Постфиксная форма: " + mather.postfixExpr + '\n');
        System.out.print("Итого: " + mather.calculate());
    }
}