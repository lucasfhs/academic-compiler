import lexer.token.Token;
import lexer.token.TokenFactory;
import lexer.token.TokenType;

public class Main {
    public static void main(String[] args) {
        Token teste = TokenFactory.createToken(TokenType.NUMBER, "300");
        System.out.println(teste.getValue());
    }
}
