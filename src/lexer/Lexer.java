package lexer;

import java.io.*;

import lexer.token.Token;
import lexer.token.TokenFactory;
import lexer.token.TokenType;
import symboltable.Environment;
import utils.LexerFileReader;

public class Lexer implements AutoCloseable {

    private LexerFileReader reader;
    private int line = 0;
    private Environment symbolTable;

    public Lexer(String fileName) throws FileNotFoundException {
        this.reader = new LexerFileReader(fileName);
    }

    public Lexer(InputStream is, Environment symbolTable) throws IOException {
        this.symbolTable = symbolTable;
        File tempFile = File.createTempFile("lexer_input", ".tmp");
        tempFile.deleteOnExit();

        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) != -1) {
                fos.write(buffer, 0, length);
            }
        }

        this.reader = new LexerFileReader(tempFile.getAbsolutePath());
    }

    @Override
    public void close() throws Exception {
        try {
            reader.close();
        } catch (Exception e) {
            throw new Exception("Unable to close file");
        }
    }

    public int getLine() {
        return this.reader.getCurrentLine();
    }

    public Token scan() throws Exception {
        int ch;
        StringBuilder lexeme = new StringBuilder();
        TokenType type = TokenType.ERROR;
        int next;

        while (true) {
            ch = getc();

            if (ch == -1) {
                return TokenFactory.createToken(TokenType.EOF, "");
            }

            if (ch == '/') {
                next = getc();
                if (next == '/') {
                    while ((ch = getc()) != -1 && ch != '\n') {
                    }
                    if (ch == '\n')
                        line++;
                    continue;
                } else {
                    ungetc(next);
                    break;
                }
            }
            if (ch == ' ' || ch == '\t' || ch == '\r' || ch == '\b') {
                continue;
            }

            if (ch == '\n') {
                line++;
                continue;
            }
            break;
        }
        switch ((char) ch) {
            case ',':
                type = TokenType.COMMA;
                break;
            case ';':
                type = TokenType.SEMICOLON;
                break;
            case '{':
                type = TokenType.OPEN_BRACE;
                break;
            case '}':
                type = TokenType.CLOSE_BRACE;
                break;
            case '(':
                type = TokenType.OPEN_PAREN;
                break;
            case ')':
                type = TokenType.CLOSE_PAREN;
                break;
            case '+':
                type = TokenType.PLUS;
                break;
            case '"':
                lexeme.append(readString());
                next = getc();
                if ((char) next == '"') {
                    type = TokenType.STR_LITERAL;
                } else {
                    ungetc(next);
                    throw new Exception("Invalid token '\"" + lexeme.toString() + "' at line " + getLine());
                }
                break;
            case '\'':
                ch = getc();
                if ((ch != -1 && ch != '\'' && ch != '\n' && ch != '\r')) {
                    lexeme.append((char) ch);
                    next = getc();
                    if ((char) next == '\'') {
                        type = TokenType.CHAR_LITERAL;
                    } else {
                        ungetc(next);
                        String nextCh = (next == -1) ? "END OF FILE" : Character.toString((char) next);
                        throw new Exception("Invalid token '" + nextCh + "' at line " + getLine());
                    }
                } else {
                    throw new Exception("Invalid token '" + lexeme.toString() + "' at line " + getLine());
                }
                break;
            case '-':
                lexeme.append((char) ch);
                next = getc();
                if (Character.isDigit((char) next)) {
                    lexeme.append(readNumber(next));
                    type = lexeme.toString().contains(".") ? TokenType.REAL_LITERAL : TokenType.INT_LITERAL;
                } else {
                    ungetc(next);
                    lexeme.append('-');
                    type = TokenType.MINUS;
                }
                break;
            case '*':
                type = TokenType.MULTIPLY;
                break;
            case '/':
                type = TokenType.DIVIDE;
                break;
            case '!':
                next = getc();
                if ((char) next == '=') {
                    type = TokenType.NOT_EQUAL;
                } else {
                    ungetc(next);
                    type = TokenType.NOT;
                }
                break;
            case '=':
                next = getc();
                if ((char) next == '=') {
                    type = TokenType.EQUAL;
                } else {
                    ungetc(next);
                    type = TokenType.ASSIGN;
                }
                break;
            case '>':
                next = getc();
                if ((char) next == '=') {
                    type = TokenType.GREATER_EQUAL;
                } else {
                    ungetc(next);
                    type = TokenType.GREATER;
                }
                break;
            case '<':
                next = getc();
                if ((char) next == '=') {
                    type = TokenType.LESS_EQUAL;
                } else {
                    ungetc(next);
                    type = TokenType.LESS;
                }
                break;
            case '|':
                next = getc();
                if ((char) next == '|') {
                    type = TokenType.OR;
                } else {
                    ungetc(next);
                    String nextCh = (next == -1) ? "END OF FILE" : Character.toString((char) next);
                    throw new Exception("Invalid token '" + nextCh + "' at line " + getLine());
                }
                break;
            case '&':
                next = getc();
                if ((char) next == '&') {
                    type = TokenType.AND;
                } else {
                    ungetc(next);
                    String nextCh = (next == -1) ? "END OF FILE" : Character.toString((char) next);
                    throw new Exception("Invalid token '" + nextCh + "' at line " + getLine());
                }
                break;
            default:
                if (Character.isDigit((char) ch)) {
                    lexeme.append((char) ch);
                    lexeme.append(readNumberWithoutFirstChar());
                    type = lexeme.toString().contains(".") ? TokenType.REAL_LITERAL : TokenType.INT_LITERAL;
                } else if (Character.isLetter((char) ch) || ch == '_') {
                    lexeme.append((char) ch);
                    while (true) {
                        next = getc();
                        if (Character.isLetterOrDigit((char) next) || next == '_') {
                            lexeme.append((char) next);
                        } else {
                            ungetc(next);
                            break;
                        }
                    }
                    type = checkIfKeyword(lexeme.toString());
                    // Install ID
                    if (type == TokenType.IDENTIFIER) {
                        symbolTable.installID(lexeme.toString(), TokenFactory.createToken(type, lexeme.toString()));
                    }
                } else {
                    lexeme.append((char) ch);
                    throw new Exception("Invalid token '" + lexeme.toString() + "' at line " + getLine());
                }
        }

        return TokenFactory.createToken(type, lexeme.toString());
    }

    private String readString() throws Exception {
        StringBuilder sb = new StringBuilder();
        int c = getc();

        while (c != -1 && c != '"' && c != '\n' && c != '\r') {
            sb.append((char) c);
            c = getc();
        }
        ungetc(c);

        return sb.toString();
    }

    private String readNumber(int firstChar) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append((char) firstChar);
        int c;
        while (Character.isDigit((char) (c = getc()))) {
            sb.append((char) c);
        }
        if ((char) c == '.') {
            int next = getc();
            if (Character.isDigit((char) next)) {
                sb.append('.').append((char) next);
                while (Character.isDigit((char) (next = getc()))) {
                    sb.append((char) next);
                }
                ungetc(next);
            } else {
                ungetc(next);
            }
        } else { // Descarta o último caractere lido que não é parte do número
            ungetc(c);
        }

        return sb.toString();
    }

    private String readNumberWithoutFirstChar() throws Exception {
        StringBuilder sb = new StringBuilder();
        int c;
        while (Character.isDigit((char) (c = getc()))) {
            sb.append((char) c);
        }
        if ((char) c == '.') {
            int next = getc();
            if (Character.isDigit((char) next)) {
                sb.append('.').append((char) next);
                while (Character.isDigit((char) (next = getc()))) {
                    sb.append((char) next);
                }
                ungetc(next);
            } else {
                ungetc(next);
            }
        } else {
            ungetc(c);
        }
        return sb.toString();
    }

    private TokenType checkIfKeyword(String lexeme) {
        switch (lexeme) {
            case "app":
                return TokenType.APP;
            case "string":
                return TokenType.STRING;
            case "int":
                return TokenType.INT;
            case "real":
                return TokenType.REAL;
            case "char":
                return TokenType.CHAR;
            case "scan":
                return TokenType.SCAN;
            case "print":
                return TokenType.PRINT;
            case "if":
                return TokenType.IF;
            case "else":
                return TokenType.ELSE;
            case "while":
                return TokenType.WHILE;
            case "do":
                return TokenType.DO;
            default:
                return TokenType.IDENTIFIER;
        }
    }

    private int getc() throws Exception {
        try {
            return reader.readChar();
        } catch (Exception e) {
            throw new Exception("Unable to read file: " + e.getMessage());
        }
    }

    private void ungetc(int c) throws Exception {
        if (c != -1) {
            try {
                reader.unreadChar((char) c);
            } catch (Exception e) {
                throw new Exception("Unable to ungetc: " + e.getMessage());
            }
        }
    }

}