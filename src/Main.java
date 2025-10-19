import lexer.token.Token;
import lexer.token.TokenType;

import java.io.FileInputStream;

import lexer.Lexer;

public class Main {
    public static void main(String[] args) {
        try {
            run(new FileInputStream(args[0]), true);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Argumento inválido: é necessário o nome do arquivo de entrada.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void run(FileInputStream inputStream, boolean flag) {
        try (Lexer lex = new Lexer(inputStream)) {
            Token token;
            System.out.println("---|--------------|------------|-------------");
            System.out.println("Ln |  TokenType   |   Valor    | Descrição   ");
            System.out.println("---|--------------|------------|-------------");
            do {
                token = lex.scan();
                System.out.printf(
                        "%-3d| %-12s | %-10s | %-12s%n",
                        lex.getLine(),
                        token.getType().name(),
                        token.getValue(),
                        token.getType().getDescription());
                System.out.println("___|______________|____________|_____________");
            } while (token.getType() != TokenType.EOF &&
                    token.getType() != TokenType.ERROR);
        } catch (Exception e) {
            System.out.println("\n\n");
            System.out.println(e.getMessage());
            System.out.println("Please review the syntax. Ending program now.");
        }
    }
}