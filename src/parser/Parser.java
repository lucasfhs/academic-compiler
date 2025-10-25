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

    // process :: program "EOF"
    public void procProcess() throws Exception {
        // program
        procProgram();
        // "EOF"
        eat(TokenType.EOF);
    }

    // program ::= app body
    public void procProgram() throws Exception {
        // "app"
        eat(TokenType.APP);
        // body
        procBody();
    }

    // body ::= [decl-list] "{" stmt-list "}"
    public void procBody() throws Exception {
        // [decl-list]
        if (check(TokenType.INT, TokenType.REAL, TokenType.CHAR, TokenType.STRING)) {
            procDeclList();
        }
        // "{"
        eat(TokenType.OPEN_BRACE);
        // stmt-list
        procStmtList();
        // "}"
        eat(TokenType.CLOSE_BRACE);
    }

    // decl-list ::= decl {decl}
    public void procDeclList() throws Exception {
        // decl
        procDecl();
        // {decl}
        while (check(TokenType.INT, TokenType.REAL, TokenType.CHAR, TokenType.STRING)) {
            procDecl();
        }
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

    // stmt-list ::= stmt {stmt}
    public void procStmtList() throws Exception {
        // stmt
        procStmt();
        // {stmt}
        while (check(TokenType.IDENTIFIER, TokenType.IF, TokenType.DO, TokenType.WHILE,
                TokenType.SCAN, TokenType.PRINT)) {
            procStmt();
        }
    }

    // stmt ::= assign-stmt | if-stmt | while-stmt | dowhile-stmt | read-stmt |
    // write-stmt
    public void procStmt() throws Exception {
        // assign-stmt | if-stmt | while-stmt | dowhile-stmt | read-stmt | write-stmt
        if (check(TokenType.IDENTIFIER)) {
            // assign-stmt
            procAssignStmt();
        } else if (check(TokenType.IF)) {
            // if-stmt
            procIfStmt();
        } else if (check(TokenType.WHILE)) {
            // while-stmt
            procWhileStmt();
        } else if (check(TokenType.DO)) {
            // dowhile-stmt
            procDoWhileStmt();
        } else if (check(TokenType.SCAN)) {
            // read-stmt
            procReadStmt();
        } else if (check(TokenType.PRINT)) {
            // write-stmt
            procWriteStmt();
        } else {
            // Recuperacao de erro...
        }
    }

    // assign-stmt ::= identifier "=" simple_expr ";"
    public void procAssignStmt() throws Exception {
        // identifier
        eat(TokenType.IDENTIFIER);
        // "="
        eat(TokenType.ASSIGN);
        // simple_expr
        procSimpleExpr();
        // ";"
        eat(TokenType.SEMICOLON);
    }

    // if-stmt ::= if "(" expression ")" "{" stmt-list "}" if-stmtZ
    public void procIfStmt() throws Exception {
        // if
        eat(TokenType.IF);
        // "("
        eat(TokenType.OPEN_PAREN);
        // expression
        procExpression();
        // ")"
        eat(TokenType.CLOSE_PAREN);
        // "{"
        eat(TokenType.OPEN_BRACE);
        // stmt-list
        procStmtList();
        // "}"
        eat(TokenType.CLOSE_BRACE);
        // if-stmtZ
        procIfStmtZ();
    }

    // if-stmtZ ::= lambda | else "{" stmt-list "}"
    public void procIfStmtZ() throws Exception {
        // else "{" stmt-list "}"
        if (check(TokenType.ELSE)) {
            // else
            eat(TokenType.ELSE);
            // "{"
            eat(TokenType.OPEN_BRACE);
            // stmt-list
            procStmtList();
            // "}"
            eat(TokenType.CLOSE_BRACE);
        }
        // lambda
    }

    // dowhile-stmt ::= do "{" stmt-list "}" while "(" expression ")" ";"
    public void procDoWhileStmt() throws Exception {
        // do
        eat(TokenType.DO);
        // "{"
        eat(TokenType.OPEN_BRACE);
        // stmt-list
        procStmtList();
        // "}"
        eat(TokenType.CLOSE_BRACE);
        // while
        eat(TokenType.WHILE);
        // "("
        eat(TokenType.OPEN_PAREN);
        // expression
        procExpression();
        // ")"
        eat(TokenType.CLOSE_PAREN);
        // ";"
        eat(TokenType.SEMICOLON);
    }

    // while-stmt ::= while "(" expression ")" do "{" stmt-list "}"
    public void procWhileStmt() throws Exception {
        // while
        eat(TokenType.WHILE);
        // "("
        eat(TokenType.OPEN_PAREN);
        // expression
        procExpression();
        // ")"
        eat(TokenType.CLOSE_PAREN);
        // do
        eat(TokenType.DO);
        // "{"
        eat(TokenType.OPEN_BRACE);
        // stmt-list
        procStmtList();
        // "}"
        eat(TokenType.CLOSE_BRACE);
    }

    // read-stmt ::= scan "(" identifier ")" ";"
    public void procReadStmt() throws Exception {
        // scan
        eat(TokenType.SCAN);
        // "("
        eat(TokenType.CLOSE_PAREN);
        // identifier
        eat(TokenType.IDENTIFIER);
        // ")"
        eat(TokenType.CLOSE_PAREN);
        // ";"
        eat(TokenType.SEMICOLON);
    }

    // write-stmt ::= print "(" simple-expr ")" ";"
    public void procWriteStmt() throws Exception {
        // print
        eat(TokenType.PRINT);
        // "("
        eat(TokenType.OPEN_PAREN);
        // simple-expr
        procSimpleExpr();
        // ")"
        eat(TokenType.CLOSE_PAREN);
        // ";"
        eat(TokenType.SEMICOLON);
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