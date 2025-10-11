import lexer.token.Token;
import lexer.token.TokenFactory;
import lexer.token.TokenType;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import lexer.Lexer;

public class Main {
    public static void main(String[] args) {
        try {
            run(new FileInputStream(args[0]), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void run(FileInputStream inputStream, boolean flag) {
        try (Lexer lex = new Lexer(inputStream)) {
        Token token;
        System.out.println("---|--------------|--------------|------------|-------------");
        System.out.println("Ln |    Lexema    |  TokenType   |   Valor    | Descrição   ");
        System.out.println("---|--------------|--------------|------------|-------------");
            do {
                token = lex.scan();
                // Cabeçalho da tabela
                System.out.printf(
                    "%-2d | %-12s | %-12s | %-10s | %-12s%n",
                    lex.getLine(), 
                    token.getLexeme(), 
                    token.getType().name(),
                    token.getValue(), 
                    token.getType().getDescription()
                    );
                    System.out.println("___|______________|______________|____________|_____________");
                    // System.out.println("---|--------------|--------------|------------|-------------");
                // System.out.println("-----------------------------------");
            } while (token.getType() != TokenType.EOF &&
                     token.getType() != TokenType.ERROR);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String> splitIntoLines(String text, int width) {
    List<String> lines = new ArrayList<>();
    for (int i = 0; i < text.length(); i += width) {
        lines.add(text.substring(i, Math.min(i + width, text.length())));
    }
    return lines;
}
}
