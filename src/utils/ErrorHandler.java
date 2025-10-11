package utils;

public class ErrorHandler {
    public static void reportError(String message) {
        System.err.println("Error: " + message);
    }

    public static void reportError(String message, int line, int column) {
        System.err.printf("Error at line %d, column %d: %s%n", line, column, message);
    }

    public static void fatalError(String message) {
        System.err.println("Fatal Error: " + message);
        System.exit(1);
    }

    public static void fatalError(String message, int line, int column) {
        System.err.printf("Fatal Error at line %d, column %d: %s%n", line, column, message);
        System.exit(1);
    }
}