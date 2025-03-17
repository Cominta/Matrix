package console;

import exception.ExceptionObj;
import java.io.FileWriter;
import java.io.IOException;

import matrix.Matrix;
import matrix.elements.Element;
import matrix.elements.NumZ;

public class COutput {
    public static void saveToFile(String filePath, Matrix matrix) {
        try (FileWriter fw = new FileWriter(filePath)) {
            fw.write("# MATRIX\n");
            fw.write(matrix.getSizeX() + " " + matrix.getSizeY() + "\n");
            if(matrix.getMode() == Element.Types.R){
                fw.write("R\n");
            }
            else{
                fw.write("Z" + ((NumZ)matrix.getElement(0,0)).getCountZ() + "\n");
            }
            for (int i = 0; i < matrix.getSizeY(); i++) {
                for (int j = 0; j < matrix.getSizeX(); j++) {
                    fw.write(matrix.getElement(j, i) + " ");
                }
                fw.write("\n");
            }
        } catch (IOException e) {
            System.err.println("Cannot save to file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error(xz 4o za error): " + e.getMessage());
        }
    }

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

        System.out.print("\n");
    }

    public static void printMatrix(Matrix matrix) {
        printMatrix(matrix, "Matrix: ");
    }

    public static void report(ExceptionObj e) {
        System.out.println("[-] Type: " + e.getType() + ", Message: " + e);
    }
}
