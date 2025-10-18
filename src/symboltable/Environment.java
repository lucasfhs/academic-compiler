package symboltable;

import java.util.Hashtable;

import lexer.token.Token;

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