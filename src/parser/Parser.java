package parser;

import lexer.token.TokenType;
import lexer.Lexer;
import lexer.token.Token;

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
        System.out.println("Eat " + current);
        previous = current;
        current = lex.scan();
    }

    private void eat(TokenType type) throws Exception {
        if (type == current.getType()) {
            advance();
        } else {
            throw new Exception(
                    "Expected token of type " + type + ", found " + current.getType() + " in line " + lex.getLine()
                            + ".");
        }
    }

    private boolean check(TokenType... types) {
        for (TokenType type : types) {
            if (current.getType() == type)
                return true;
        }

        return false;
    }

    // process ::= program "EOF"
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

    // decl ::= type ident-list ";"
    public void procDecl() throws Exception {
        // type
        procType();
        // ident-list
        procIdentList();
        // ";"
        eat(TokenType.SEMICOLON);
    }

    // ident-list ::= identifier {"," identifier}
    public void procIdentList() throws Exception {
        // identifier
        eat(TokenType.IDENTIFIER);
        // {"," identifier}
        while (check(TokenType.COMMA)) {
            // ","
            eat(TokenType.COMMA);
            // identifier
            eat(TokenType.IDENTIFIER);
        }
    }

    // type ::= "int" | "real" | "string" | "char"
    public void procType() throws Exception {
        if (check(TokenType.INT)) {
            // "int"
            eat(TokenType.INT);
        } else if (check(TokenType.REAL)) {
            // "real"
            eat(TokenType.REAL);
        } else if (check(TokenType.STRING)) {
            // "string"
            eat(TokenType.STRING);
        } else if (check(TokenType.CHAR)) {
            // "char"
            eat(TokenType.CHAR);
        } else {
            throw new Exception(
                    "Expected token of types: INT | REAL | STRING | CHAR "
                            + ", found " + current.getType() + " in line " + lex.getLine() + ".");
        }
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
            throw new Exception(
                    "Expected token of types: IDENTIFIER | INT_LITERAL | REAL_LITERAL | CHAR_LITERAL | STR_LITERAL | OPEN_PAREN"
                            + ", found " + current.getType() + " in line " + lex.getLine() + ".");
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
        eat(TokenType.OPEN_PAREN);
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

    // expression ::= simple-expr expressionZ
    public void procExpression() throws Exception {
        // simple-expr
        procSimpleExpr();
        // expressionZ
        procExpressionZ();
    }

    // expressionZ ::= lambda | relop simple-expr expressionZ
    public void procExpressionZ() throws Exception {
        // relop simple-expr
        if (check(TokenType.EQUAL, TokenType.NOT_EQUAL, TokenType.LESS,
                TokenType.LESS_EQUAL, TokenType.GREATER, TokenType.GREATER_EQUAL)) {
            // relop
            procRelop();
            // simple-expr
            procSimpleExpr();
            // expressionZ
            procExpressionZ();
        }
        // lambda
    }

    // simple-expr ::= term simple-exprZ
    public void procSimpleExpr() throws Exception {
        // term
        procTerm();
        // simple-exprZ
        procSimpleExprZ();
    }

    // simple-exprZ ::= lambda | addop term simple-exprZ
    public void procSimpleExprZ() throws Exception {
        // addop term simple-exprZ
        if (check(TokenType.PLUS, TokenType.MINUS, TokenType.OR)) {
            // addop
            procAddOp();
            // term
            procTerm();
            // simple-exprZ
            procSimpleExprZ();
        }
        // lambda
    }

    // term ::= factorA termZ
    public void procTerm() throws Exception {
        // factorA
        procFactorA();
        // termZ
        procTermZ();
    }

    // termZ ::= lambda | mulop factorA termZ
    public void procTermZ() throws Exception {
        // mulop factorA termZ
        if (check(TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.AND)) {
            // mulop
            procMulOp();
            // factorA
            procFactorA();
            // termZ
            procTermZ();
        }
        // lambda
    }

    // factorA ::= factor | "!" factor | "-" factor
    public void procFactorA() throws Exception {
        // "!" factor
        if (check(TokenType.NOT)) {
            // "!"
            eat(TokenType.NOT);
            // factor
            procFactor();
        }
        // "-" factor
        else if (check(TokenType.MINUS)) {
            // "-"
            eat(TokenType.MINUS);
            // factor
            procFactor();
        }
        // factor
        else {
            procFactor();
        }
    }

    // factor ::= identifier | constant | "(" expression ")"
    public void procFactor() throws Exception {
        // identifier
        if (check(TokenType.IDENTIFIER)) {
            eat(TokenType.IDENTIFIER);
        }
        // constant
        else if (check(TokenType.INT_LITERAL, TokenType.REAL_LITERAL,
                TokenType.CHAR_LITERAL, TokenType.STR_LITERAL)) {
            procConstant();
        }
        // "(" expression ")"
        else if (check(TokenType.OPEN_PAREN)) {
            // "("
            eat(TokenType.OPEN_PAREN);
            // expression
            procExpression();
            // ")"
            eat(TokenType.CLOSE_PAREN);
        } else {
            throw new Exception(
                    "Expected token of types: IDENTIFIER | INT_LITERAL | REAL_LITERAL | CHAR_LITERAL | STR_LITERAL | OPEN_PAREN"
                            + ", found " + current.getType() + " in line " + lex.getLine() + ".");
        }
    }

    // relop ::= "==" | "!=" | "<" | "<=" | ">" | ">="
    public void procRelop() throws Exception {
        if (check(TokenType.EQUAL)) {
            // "=="
            eat(TokenType.EQUAL);
        } else if (check(TokenType.NOT_EQUAL)) {
            // "!="
            eat(TokenType.NOT_EQUAL);
        } else if (check(TokenType.LESS)) {
            // "<"
            eat(TokenType.LESS);
        } else if (check(TokenType.LESS_EQUAL)) {
            // "<="
            eat(TokenType.LESS_EQUAL);
        } else if (check(TokenType.GREATER)) {
            // ">"
            eat(TokenType.GREATER);
        } else if (check(TokenType.GREATER_EQUAL)) {
            // ">="
            eat(TokenType.GREATER_EQUAL);
        } else {
            throw new Exception(
                    "Expected token of types: EQUAL | NOT_EQUAL | LESS | LESS_EQUAL | GREATER | GREATER_EQUAL"
                            + ", found " + current.getType() + " in line " + lex.getLine() + ".");
        }
    }

    // addop ::= "+" | "-" | "||"
    public void procAddOp() throws Exception {
        if (check(TokenType.PLUS)) {
            // "+"
            eat(TokenType.PLUS);
        } else if (check(TokenType.MINUS)) {
            // "-"
            eat(TokenType.MINUS);
        } else if (check(TokenType.OR)) {
            // "||"
            eat(TokenType.OR);
        } else {
            throw new Exception("Expected token of types: PLUS | MINUS | OR"
                    + ", found " + current.getType() + " in line " +  lex.getLine() + ".");
        }
    }

    // mulop ::= "*" | "/" | "&&"
    public void procMulOp() throws Exception {
        if (check(TokenType.MULTIPLY)) {
            // "*"
            eat(TokenType.MULTIPLY);
        } else if (check(TokenType.DIVIDE)) {
            // "/"
            eat(TokenType.DIVIDE);
        } else if (check(TokenType.AND)) {
            // "&&"
            eat(TokenType.AND);
        } else {
            throw new Exception("Expected token of types: MULTIPLY | DIVIDE | AND"
                    + ", found " + current.getType() + " in line " + lex.getLine() + ".");
        }
    }

    // constant ::= integer_const | real_const | char_const | literal
    public void procConstant() throws Exception {
        if (check(TokenType.INT_LITERAL)) {
            // integer_const
            eat(TokenType.INT_LITERAL);
        } else if (check(TokenType.REAL_LITERAL)) {
            // real_const
            eat(TokenType.REAL_LITERAL);
        } else if (check(TokenType.CHAR_LITERAL)) {
            // char_const
            eat(TokenType.CHAR_LITERAL);
        } else if (check(TokenType.STR_LITERAL)) {
            // literal
            eat(TokenType.STR_LITERAL);
        } else {
            throw new Exception("Expected token of types: INT_LITERAL | REAL_LITERAL | CHAR_LITERAL | STR_LITERAL"
                    + ", found " + current.getType() + " in line " + lex.getLine() + ".");
        }
    }
}