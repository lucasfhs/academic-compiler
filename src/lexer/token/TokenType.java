package lexer.token;

public enum TokenType {
    // Constantes
    IDENTIFIER("identifier"),
    STR_LITERAL("string literal"),
    INT_LITERAL("integer literal"),
    REAL_LITERAL("real literal"),
    CHAR_LITERAL("character literal"),

    // Palavras-chave
    APP("application entry point"),
    STRING("string type"),
    INT("integer type"),
    REAL("real number type"),
    CHAR("character type"),
    SCAN("input operation"),
    PRINT("output operation"),
    IF("conditional statement"),
    ELSE("alternative branch"),
    WHILE("while loop"),
    DO("do-while loop"),

    // Operadores
    ASSIGN("assignment"),
    PLUS("plus"),
    MINUS("minus"),
    MULTIPLY("multiply"),
    DIVIDE("divide"),
    EQUALS("equals"),
    GREATER("greater"),
    LESS("less"),

    // Operadores compostos
    NOT_EQUAL("not equal"),
    GREATER_EQUAL("greater or equal"),
    LESS_EQUAL("less or equal"),
    AND("logical and"),
    OR("logical or"),

    // Pontuação
    LPAREN("left parenthesis "),
    RPAREN("right parenthesis"),
    LBRACE("left brace"),
    RBRACE("right brace"),
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
        return name() + ": " + this.description;
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