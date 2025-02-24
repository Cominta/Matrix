package exception;

import console.COutput;

import java.util.LinkedList;

public class ExceptionHandler {
    private static LinkedList<ExceptionObj> exceptions = new LinkedList<ExceptionObj>();

    public static void report(ExceptionObj exception) {
        exceptions.add(exception);
    }

    public static boolean isEmpty() {
        return exceptions.isEmpty();
    }

    public static void printExceptions() {
        while (!exceptions.isEmpty()) {
            COutput.report(exceptions.pop());
        }
    }

    public static void clear() {
        exceptions.clear();
    }
}
