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
        // Token token = new Token("", Token.Type.END_OF_FILE, null);
        SourcePosition startPosition = reader.getCurrentPosition();

        int state = 1;
        while (state != 14 && state != 15) {
            // UTILIZACAO DO AFD

            switch (state) {
                default:
                    throw new Exception("Unreachable");
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