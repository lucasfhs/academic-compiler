package lexer;

import java.io.*;
import lexer.token.Token;
import lexer.token.TokenType;
import utils.LexerFileReader;

public class Lexer implements AutoCloseable {

    private LexerFileReader reader;

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

    public SourcePosition getCurrentPosition() {
        return reader.getCurrentPosition();
    }

    public Token scan() throws Exception {
        // - BASE-
        SourcePosition startPosition = reader.getCurrentPosition();
        StringBuilder lexeme = new StringBuilder();
        Token token = new Token(TokenType.EOF, null);

        int state = 1;
        while (state != 14 && state != 15) {
            int c = getc();
            // UTILIZACAO DO AFD

            switch (state) {
                case 1:
                    if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
                        state = 1;
                    } else if (c == -1) {
                        token.setType(TokenType.EOF);
                        state = 101;
                    } else if (c == '"') {
                        state = 2; // literal string
                    } else if (c == '_' || Character.isLetter(c)) {
                        lexeme.append((char) c);
                        state = 3; // identificador ou palavra-chave
                    } else if (Character.isDigit(c) || c == '-') {
                        lexeme.append((char) c);
                        state = 4; // número inteiro ou float
                    } else if (c == '!' || c == '=' || c == '>' || c == '<' || c == '&' || c == '|') {
                        lexeme.append((char) c);
                        state = 5; // operadores compostos
                    } else if (c == '+' || c == '*' || c == '/' ||
                               c == '(' || c == ')' || c == '{' ||
                               c == '}' || c == ',' || c == ';') {
                        lexeme.append((char) c);
                        state = 100; // delimitador simples
                    } else {
                        lexeme.append((char) c);
                        token.setType(TokenType.ERROR);
                        state = 101;
                    }
                    break;

                case 2: // literal string
                    if (c == '"') {
                        token.setType(TokenType.STRING);
                        state = 101;
                    } else if (c == -1) {
                        token.setType(TokenType.EOF);
                        state = 101;
                    } else {
                        lexeme.append((char) c);
                        state = 2;
                    }
                    break;

                case 3: // identificador ou palavra-chave
                    if (Character.isLetter(c) || Character.isDigit(c) || c == '_') {
                        lexeme.append((char) c);
                        state = 3;
                    } else {
                        ungetc(c);
                        String word = lexeme.toString();
                        switch (word) {
                            case "app": token.setType(TokenType.APP); break;
                            case "int": token.setType(TokenType.INT); break;
                            case "real": token.setType(TokenType.REAL); break;
                            case "string": token.setType(TokenType.STRING); break;
                            case "char": token.setType(TokenType.CHAR); break;
                            case "if": token.setType(TokenType.IF); break;
                            case "else": token.setType(TokenType.ELSE); break;
                            case "while": token.setType(TokenType.WHILE); break;
                            case "do": token.setType(TokenType.DO); break;
                            case "scan": token.setType(TokenType.SCAN); break;
                            case "print": token.setType(TokenType.PRINT); break;
                            default: token.setType(TokenType.IDENTIFIER);
                        }
                        state = 101;
                    }
                    break;

                case 4: // número inteiro ou float
                    if (Character.isDigit(c)) {
                        lexeme.append((char) c);
                        state = 4;
                    } else if (c == '.') {
                        lexeme.append((char) c);
                        state = 6; // número real
                    } else {
                        ungetc(c);
                        token.setType(TokenType.NUMBER); // TODO: Especificar tipo inteiro
                        state = 101;
                    }
                    break;

                case 5: // operadores compostos (==, !=, >=, <=, &&, ||)
                    if (c == '=' || c == '&' || c == '|') {
                        lexeme.append((char) c);
                    } else {
                        ungetc(c);
                    }

                    String op = lexeme.toString();
                    switch (op) {
                        case "==": token.setType(TokenType.EQUALS); break;
                        case "!=": token.setType(TokenType.NOT_EQUAL); break;
                        case ">": token.setType(TokenType.GREATER); break;
                        case ">=": token.setType(TokenType.GREATER_EQUAL); break;
                        case "<": token.setType(TokenType.LESS); break;
                        case "<=": token.setType(TokenType.LESS_EQUAL); break;
                        case "&&": token.setType(TokenType.AND); break;
                        case "||": token.setType(TokenType.OR); break;
                        case "=": token.setType(TokenType.ASSIGN); break;
                        default: token.setType(TokenType.ERROR);
                    }
                    state = 101;
                    break;

                case 6: // número real
                    if (Character.isDigit(c)) {
                        lexeme.append((char) c);
                        state = 6;
                    } else {
                        ungetc(c);
                        token.setType(TokenType.NUMBER); // TODO: Especificar tipo float
                        state = 101;
                    }
                    break;

                default:
                    throw new Exception("Unreachable state: " + state);
            }
        }

        // Define a posição do token

        // SourcePosition endPosition = reader.getCurrentPosition();
        // token.position = new SourcePosition(
        // reader.getFileName(),
        // startPosition.getStartLine(),
        // startPosition.getStartColumn(),
        // endPosition.getEndLine(),
        // endPosition.getEndColumn(),
        // startPosition.getAbsoluteStart(),
        // endPosition.getAbsoluteEnd());

        // Retorna o Token...

        return new Token(TokenType.ERROR, null);
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