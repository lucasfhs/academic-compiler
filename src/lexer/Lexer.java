package lexer;

import java.io.*;
import java.util.Map;

import lexer.token.Token;
import lexer.token.TokenFactory;
import lexer.token.TokenType;
import utils.ErrorHandler;
import utils.LexerFileReader;

public class Lexer implements AutoCloseable {

    private static final Map<String, TokenType> KEYWORDS = Map.ofEntries(
        Map.entry("app", TokenType.APP),
        Map.entry("int", TokenType.INT),
        Map.entry("real", TokenType.REAL),
        Map.entry("string", TokenType.STRING),
        Map.entry("char", TokenType.CHAR),
        Map.entry("if", TokenType.IF),
        Map.entry("else", TokenType.ELSE),
        Map.entry("while", TokenType.WHILE),
        Map.entry("do", TokenType.DO),
        Map.entry("scan", TokenType.SCAN),
        Map.entry("print", TokenType.PRINT)
    );

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

    public Token scan() throws Exception {
        // - BASE-
        StringBuilder lexeme = new StringBuilder();
        String errorMsg = "";
        TokenType type = TokenType.EOF;

        int state = 1;
        while (state != 101 && state != 7) {
            int c = getc();
            // System.out.printf("  [%02d, %03d ('%c')]\n", state, c, (char) c);
            // UTILIZACAO DO AFD
            
            switch (state) {
                case 1:
                    if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
                        state = 1;
                    } else if (c == -1) {
                        type = TokenType.EOF;
                        state = 101;
                    } else if (c == '"') {
                        state = 2; // literal string
                    } else if (c == '_' || Character.isLetter(c)) {
                        lexeme.append((char) c);
                        state = 3; // identificador ou palavra-chave
                    } else if (Character.isDigit(c)) {
                        lexeme.append((char) c);
                        state = 4; // número inteiro ou float
                    } else if (c == '!' || c == '=' || c == '>' || c == '<' || 
                               c == '&' || c == '|') {
                        lexeme.append((char) c);
                        state = 5; // símbolos compostos
                    } else if (c == '+' || c == '-' || c == '*' || c == '/' ||
                               c == '{' || c == '(' || c == ')' || c == '}' ||
                               c == ';' || c == ',') {
                        lexeme.append((char) c);
                        state = 7; // simbolos simples
                    } else {
                        lexeme.append((char) c);
                        type = TokenType.ERROR;
                        state = 101;
                    }
                    break;

                case 2: // literal string
                    if (c == '"') {
                        type = TokenType.STR_LITERAL;
                        state = 101;
                    } else if (c == -1) {
                        type = TokenType.ERROR;
                        errorMsg = "Literal String não terminada";
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
                        type = KEYWORDS.getOrDefault(word, TokenType.IDENTIFIER);
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
                        type = TokenType.INT_LITERAL;
                        state = 101;
                    }
                    break;

                case 5: // símbolos compostos (==, !=, >=, <=, &&, ||)
                    if( c == '=' || c == '&' || c == '|') {
                        lexeme.append((char) c);
                    } else {
                        ungetc(c); // símbolo simples
                    }
                    state = 7;
                    break;

                case 6: // número real
                    if (Character.isDigit(c)) {
                        lexeme.append((char) c);
                        state = 6;
                    } else {
                        ungetc(c);
                        type = TokenType.REAL_LITERAL;
                        state = 101;
                    }
                    break;
                default:
                    throw new Exception("Unreachable state: " + state);
            }
        }

        if (state == 7) {
            type = KEYWORDS.getOrDefault(lexeme.toString(), TokenType.ERROR);
        }

        // Define a posição do token
        SourcePosition endPosition = reader.getCurrentPosition();
        Token token = TokenFactory.createToken(type, lexeme.toString(),
                new SourcePosition(
                        reader.getFileName(),
                        startPosition.getStartLine(),
                        startPosition.getStartColumn(),
                        endPosition.getEndLine(),
                        endPosition.getEndColumn(),
                        startPosition.getAbsoluteStart(),
                        endPosition.getAbsoluteEnd()));

        // Se for um token de erro, reporta o erro e termina a compilação
        if (type == TokenType.ERROR) {
            ErrorHandler.fatalError(
                errorMsg, token.getPosition(), lexeme.toString()
            );
        }
        // Retorna o Token...
        return token;
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