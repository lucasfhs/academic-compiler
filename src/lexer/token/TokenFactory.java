package lexer.token;

import lexer.value.*;

public class TokenFactory {
    private TokenFactory() {
    }

    public static Token createToken(TokenType type, String lexeme) {
        Value value = createValueForToken(type, lexeme);
        return new Token(type, value);
    }

    private static Value createValueForToken(TokenType type, String lexeme) {
        switch (type) {
            case TokenType.NUMBER:
                return new NumberValue(Integer.parseInt(lexeme));
            case TokenType.STRING:
                return new StringValue(lexeme);
            case TokenType.TRUE:
                return new BooleanValue(true);
            case TokenType.FALSE:
                return new BooleanValue(false);
            case TokenType.IDENTIFIER:
                return new IdentifierValue(lexeme);
            case TokenType.NULL:
                return new NullValue();
            default:
                return null;
        }
    }

}