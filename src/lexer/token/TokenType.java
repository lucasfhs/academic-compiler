package lexer.token;

public enum TokenType {
    APP(""),
    IDENTIFIER("Identificador"),

    NUMBER("Número"),
    TRUE("Valor Booleano Verdadeiro"),
    FALSE("Valor Booleano Falso"),
    ASSIGN("Operador de atribuição (=)"),
    STRING("String"),
    INT(""),
    REAL(""),
    CHAR(""),

    PLUS("Operador de adição (+)"),
    MINUS("Operador de subtração (-)"),
    MULTIPLY("Operador de multiplicação (*)"),
    DIVIDE("Operador de divisão (/)"),

    EQUALS("Operador de igualdade (==)"),
    NOT_EQUAL(""),
    GREATER(""),
    GREATER_THAN(""),
    GREATER_EQUAL(""),
    LESS(""),
    LESS_EQUAL(""),

    AND(""),
    OR(""),  

    SCAN(""),
    PRINT(""),
    IF("Palavra-chave 'if'"),
    ELSE("Palavra-chave 'else'"),
    WHILE("Palavra-chave 'while'"),
    DO("Palavra-chave 'while'"),
    FOR("Palavra-chave 'for'"),
    RETURN("Palavra-chave 'return'"),

    LPAREN("Parêntese esquerdo (()"),
    RPAREN("Parêntese direito ())"),
    LBRACE("Chave esquerda ({)"),
    RBRACE("Chave direita (})"),
    SEMICOLON("Ponto e vírgula (;)"),
    COMMA("Vírgula (,)"),

    NULL("Valor nulo."),
    EOF("Fim de Arquivo"),
    ERROR("Token de erro");

    private final String description;

    TokenType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name() + ": " + description;
    }
}