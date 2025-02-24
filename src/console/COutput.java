package console;

import exception.ExceptionObj;
import matrix.Matrix;

public class COutput {
    public static void printMessage(String s) {
        System.out.print("[+] " + s);
    }

    public static void printMatrix(Matrix matrix, String s) {
        System.out.println("\n[+] " + s);

        for (int i = 0; i < matrix.getSizeY(); i++) {
            System.out.print("    ");

            for (int j = 0; j < matrix.getSizeX(); j++) {
                System.out.print(matrix.getElement(j, i) + " ");
            }

            System.out.println();
        }

        System.out.print("\n\n");
    }

    public static void printMatrix(Matrix matrix) {
        printMatrix(matrix, "Matrix: ");
    }

    public static void report(ExceptionObj e) {
        System.out.println("[-] Type: " + e.getType() + ", Message: " + e);
    }
}
