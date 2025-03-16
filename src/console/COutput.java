package console;

import exception.ExceptionObj;
import matrix.Matrix;
import matrix.elements.Element;

public class COutput {
    public static void printMessage(String s) {
        System.out.print("[+] " + s);
    }

    public static void printMatrix(Matrix matrix, String s) {
        System.out.println("\n[+] " + s);
        int maxSize = -1;

        for (int i = 0; i < matrix.getSizeY(); i++) {
            for (int j = 0; j < matrix.getSizeX(); j++) {
                int len = matrix.getElement(j, i).toString().length();

                if (len > maxSize) {
                    maxSize = len;
                }
            }
        }

        System.out.print("     ");

        for (int i = 0; i < matrix.getSizeX() * maxSize; i++) {
            System.out.print("");
        }

        System.out.println();

        for (int i = 0; i < matrix.getSizeY(); i++) {
            System.out.print("    |");

            for (int j = 0; j < matrix.getSizeX(); j++) {
                String strElement = matrix.getElement(j, i).toString();
                System.out.print(matrix.getElement(j, i));

                for (int k = strElement.length(); k < maxSize; k++) {
                    System.out.print(" ");
                }

                System.out.print("|");
            }

            System.out.println();
        }

        System.out.print("     ");

        for (int i = 0; i < matrix.getSizeX() * maxSize; i++) {
            System.out.print("‾");
        }

        System.out.print("\n");
    }

    public static void printMatrix(Matrix matrix) {
        printMatrix(matrix, "Matrix: ");
    }

    public static void report(ExceptionObj e) {
        System.out.println("[-] Type: " + e.getType() + ", Message: " + e);
    }
}
