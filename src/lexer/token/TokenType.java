package lexer.token;

public enum TokenType {
    IDENTIFIER("Identificador"),
    NUMBER("Número"),
    TRUE("Valor Booleano Verdadeiro"),
    FALSE("Valor Booleano Falso"),
    STRING("String"),
    PLUS("Operador de adição (+)"),
    MINUS("Operador de subtração (-)"),
    MULTIPLY("Operador de multiplicação (*)"),
    DIVIDE("Operador de divisão (/)"),
    ASSIGN("Operador de atribuição (=)"),
    EQUALS("Operador de igualdade (==)"),
    IF("Palavra-chave 'if'"),
    ELSE("Palavra-chave 'else'"),
    WHILE("Palavra-chave 'while'"),
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