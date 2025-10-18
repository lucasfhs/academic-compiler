package lexer;

import java.io.*;
import java.util.Map;

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
        // - BASE-
        StringBuilder lexeme = new StringBuilder();
        int ch = getc();
        do {
            if (ch == ' ' || ch == '\t' || ch == '\r' || ch == '\b')
                continue; // ignora, apenas continua
            else if (ch == '\n')
                line++; // conta linhas
            else if (ch == -1)
                break;
            else
                break;
            getc(); // lê o próximo caractere
        } while (true);

        TokenType type = TokenType.EOF;
        switch ((char) ch) {
            case '*':
                break;

            default:
                break;
        }
        return TokenFactory.createToken(type, lexeme.toString());
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