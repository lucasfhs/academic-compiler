package lexer.token;

public enum TokenType {
    // Constantes
    IDENTIFIER("identifier"),
    STR_LITERAL("string literal"),
    INT_LITERAL("integer literal"),
    REAL_LITERAL("real literal"),
    CHAR_LITERAL("character literal"),

    // Palavras-chave
    APP("application keyword"),
    STRING("string keyword"),
    INT("integer keyword"),
    REAL("real keyword"),
    CHAR("character keyword"),
    SCAN("input operation keyword"),
    PRINT("output operation keyword"),
    IF("conditional statement keyword"),
    ELSE("alternative branch keyword"),
    WHILE("while loop keyword"),
    DO("do-while loop keyword"),

    // Operadores
    ASSIGN("assignment relop"),
    PLUS("plus add op"),
    MINUS("minus add op"),
    MULTIPLY("multiply mulop"),
    DIVIDE("divide mulop"),
    EQUAL("equals relop"),
    GREATER("greater relop"),
    LESS("less relop"),
    NOT("Exclamation symbol"),
    // Operadores compostos
    NOT_EQUAL("not equal relop"),
    GREATER_EQUAL("greater or equal relop"),
    LESS_EQUAL("less or equal relop"),
    AND("logical and mulop"),
    OR("logical or addop"),

    // Pontuação
    OPEN_PAREN("left parenthesis "),
    CLOSE_PAREN("right parenthesis"),
    OPEN_BRACE("left brace"),
    CLOSE_BRACE("right brace"),
    SEMICOLON("semicolon"),
    COMMA("comma"),

    // Outros
    PROGRAM_NAME("Name of file."),
    EOF("end of file"),
    ERROR("error occorrence");

    private final String description;

    TokenType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name();
    }

    public static TokenType fromDescription(String description) {
        for (TokenType tt : TokenType.values()) {
            if (tt.getDescription().equals(description)) {
                return tt;
            }
        }
        return null;
    }
}