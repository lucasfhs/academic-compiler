package symboltable;

import java.util.Hashtable;

import lexer.token.Token;
import lexer.token.TokenType;

public class Environment {

    private Hashtable<String, Token> memory;
    protected Environment prev;

    public Environment() {
        this(null);
    }

    public Environment(Environment env) {
        this.memory = new Hashtable<String, Token>();
        this.prev = env;
    }

    public void installKeywords(String programName) {
        // Type
        memory.put("int", new Token(TokenType.INT, null));
        memory.put("real", new Token(TokenType.REAL, null));
        memory.put("string", new Token(TokenType.STRING, null));
        memory.put("char", new Token(TokenType.CHAR, null));
        // Conditional
        memory.put("if", new Token(TokenType.IF, null));
        memory.put("else", new Token(TokenType.ELSE, null));
        // Loop
        memory.put("do", new Token(TokenType.DO, null));
        memory.put("while", new Token(TokenType.WHILE, null));
        // Native Function
        memory.put("scan", new Token(TokenType.SCAN, null));
        memory.put("print", new Token(TokenType.PRINT, null));
        // Specials Keywords
        memory.put("app", new Token(TokenType.APP, null));
        // Rule: No variable can have the same name as the program.
        memory.put(programName, new Token(TokenType.PROGRAM_NAME, null));
    }

    public Token get(String id) {
        for (Environment e = this; e != null; e = e.prev) {
            Token found = e.memory.get(id);
            if (found != null)
                return found;
        }
        return null;
    }

    public void set(String id, Token token) {
        if (this.memory.containsKey(id)) {
            throw new RuntimeException("Erro: identificador '" + id + "' já declarado neste escopo.");
        }
        this.memory.put(id, token);
    }

}