package parser;

import lexer.token.TokenType;
import lexer.Lexer;
import lexer.token.Token;
import lexer.token.TokenType;

public class Parser {

    private Lexer lex;
    private Token current;
    private Token previous;

    public Parser(Lexer lex) throws Exception {
        this.lex = lex;
        this.current = lex.scan();
        this.previous = null;
    }

    private void advance() throws Exception {
        System.out.println("Found " + current);
        previous = current;
        current = lex.scan();
    }

    private void eat(TokenType type) throws Exception {
        if (type == current.getType()) {
            advance();
        } else {
            // Implementar recuperação de erro...
            System.out.println("Expected (..., " + type + ", ..., ...), found " + current);
            // reportError();
        }
    }

    // Sinceramente não vejo uso para esses dois metodos... mas tinha no do andrei
    // vamos deixar...

    private boolean check(TokenType... types) {
        for (TokenType type : types) {
            if (current.getType() == type)
                return true;
        }

        return false;
    }

    private boolean match(TokenType... types) throws Exception {
        if (check(types)) {
            advance();
            return true;
        } else {
            return false;
        }
    }

    // Regra inicial da gramatica...

    public void procProcess() throws Exception {
        procProgram();
        eat(TokenType.EOF);
    }

    public void procProgram() throws Exception {
        eat(TokenType.APP);
        procBody();
    }

    public void procBody() throws Exception {
        // Implementação em andamento...
    }

    public void procDeclList() throws Exception {
        // Implementação em andamento...
    }

    public void procDecl() throws Exception {
        // Implementação em andamento...
    }

    public void procIdentList() throws Exception {
        // Implementação em andamento...
    }

    public void procType() throws Exception {
        // Implementação em andamento...
    }

    public void procStmtList() throws Exception {
        // Implementação em andamento...
    }

    public void procStmt() throws Exception {
        // Implementação em andamento...
    }

    public void procAssignStmt() throws Exception {
        // Implementação em andamento...
    }

    public void procIfStmt() throws Exception {
        // Implementação em andamento...
    }

    public void procDoWhileStmt() throws Exception {
        // Implementação em andamento...
    }

    public void procWhileStmt() throws Exception {
        // Implementação em andamento...
    }

    public void procReadStmt() throws Exception {
        // Implementação em andamento...
    }

    public void procWriteStmt() throws Exception {
        // Implementação em andamento...
    }

    public void procExpression() throws Exception {
        // Implementação em andamento...
    }

    public void procSimpleExpr() throws Exception {
        // Implementação em andamento...
    }

    public void procTerm() throws Exception {
        // Implementação em andamento...
    }

    public void procFactorA() throws Exception {
        // Implementação em andamento...
    }

    public void procFactor() throws Exception {
        // Implementação em andamento...
    }

    public void procRelop() throws Exception {
        // Implementação em andamento...
    }

    public void procAddOp() throws Exception {
        // Implementação em andamento...
    }

    public void procMulOp() throws Exception {
        // Implementação em andamento...
    }

    public void procConstant() throws Exception {
        // Implementação em andamento...
    }

    public void procIntegerConst() throws Exception {
        // Implementação em andamento...
    }

    public void procRealConst() throws Exception {
        // Implementação em andamento...
    }

    public void procCharonst() throws Exception {
        // Implementação em andamento...
    }

    public void procLiteral() throws Exception {
        // Implementação em andamento...
    }
}