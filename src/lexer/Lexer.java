package lexer;

import java.io.*;

import lexer.token.Token;
import lexer.token.TokenFactory;
import lexer.token.TokenType;
import utils.LexerFileReader;

public class Lexer implements AutoCloseable {

    private LexerFileReader reader;

    private int line = 0;

    public Lexer(String fileName) throws FileNotFoundException {
        this.reader = new LexerFileReader(fileName);
    }

    public Lexer(InputStream is) throws IOException {

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
        int ch = getc();
        StringBuilder lexeme = new StringBuilder();
        TokenType type = TokenType.EOF;
        int next;

        for (;; getc()) {
            if (ch == ' ' || ch == '\t' || ch == '\r' || ch == '\b')
                continue;
            else if (ch == '\n')
                line++;
            else
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
                // Testar... Implementação Sugerida pela IA...
                lexeme.append((char) ch);
                lexeme.append(readString());
                next = getc();
                if ((char) next == '"') {
                    type = TokenType.STR_LITERAL;
                    lexeme.append(next);
                } else {
                    throw new Exception("Invalid token. Perhaps you lost to write '\"'.");
                }
                break;
            case '\'':
                // Testar... Implementação Sugerida pela IA...
                lexeme.append((char) ch);
                if (ch != -1 && ch != '\'' && ch != '\n' && ch != '\r') {
                    lexeme.append((char) ch);
                    next = getc();
                    if ((char) next == '\'') {
                        lexeme.append(next);
                        type = TokenType.CHAR_LITERAL;
                    } else {
                        throw new Exception("Invalid token. Perhaps you lost to write '\''.");
                    }
                } else {
                    throw new Exception("Invalid token.");
                }
                break;
            case '-':
                // Testar... Implementação Sugerida pela IA...
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
                    throw new Exception("Invalid token. Perhaps you meant to write '|'.");
                }
                break;
            case '&':
                next = getc();
                if ((char) next == '&') {
                    type = TokenType.AND;
                } else {
                    throw new Exception("Invalid token. Perhaps you meant to write '&'.");
                }
                break;
            default:
                // Feito por IA conferir depois...
                if (Character.isLetter((char) ch) || ch == '_') {
                    lexeme.append((char) ch);
                    type = TokenType.IDENTIFIER;

                    // Continua lendo enquanto for letra, dígito ou _
                    while (true) {
                        next = getc();
                        if (Character.isLetterOrDigit((char) next) || next == '_') {
                            lexeme.append((char) next);
                        } else {
                            ungetc(next); // Devolve o caractere que não pertence ao identificador
                            break;
                        }
                    }
                    // installID(lexeme.toString());
                } else {
                    throw new Exception("Invalid token (Check syntax).");
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
            } else {
                ungetc(next);
            }
        }
        ungetc(c);
        return sb.toString();
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