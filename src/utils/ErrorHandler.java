package utils;

import lexer.SourcePosition;

public class ErrorHandler {
    public static void reportError(String message) {
        System.err.println("Erro: " + message);
    }

    public static void reportError(String message, SourcePosition pos, String... args) {
        System.err.printf("Erro na linha %d, coluna %d: %s%n\"%s\"", message, pos.getStartLine(), pos.getStartColumn(), String.join("\n", args));
    }

    public static void fatalError(String message) {
        System.err.println("Erro Fatal: " + message);
        System.exit(1);
    }

    public static void fatalError(String message, SourcePosition pos, String... args) {
        System.err.printf("Erro Fatal: %s na linha %d, coluna %d: %n\"%s\"", message, pos.getStartLine(), pos.getStartColumn(), String.join(", ", args));
        System.exit(1);
    }
}