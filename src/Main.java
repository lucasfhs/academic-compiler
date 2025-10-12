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
}
