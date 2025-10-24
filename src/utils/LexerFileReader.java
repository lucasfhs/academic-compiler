package utils;

import java.io.*;

public class LexerFileReader implements AutoCloseable {
    private final RandomAccessFile file;
    private final String fileName;
    private long currentPosition;
    private int currentLine = 1;
    private int currentColumn = 0;
    private boolean endOfFile = false;

    public LexerFileReader(String fileName) throws FileNotFoundException {
        this.fileName = fileName;
        this.file = new RandomAccessFile(fileName, "r");
        this.currentPosition = 0;
    }

    public int readChar() throws IOException {
        if (endOfFile) {
            return -1;
        }

        int character = file.read();
        if (character == -1) {
            endOfFile = true;
            return -1;
        }

        currentPosition++;

        if (character == '\n') {
            currentLine++;
            currentColumn = 0;
        } else {
            currentColumn++;
        }

        return character;
    }

    public char readCharAsChar() throws IOException {
        int character = readChar();
        return character != -1 ? (char) character : '\0';
    }

    public void unreadChar(char c) throws IOException {
        if (currentPosition > 0) {
            file.seek(file.getFilePointer() - 1);
            currentPosition--;

            if (c == '\n') {
                currentLine--;
            } else {
                currentColumn--;
            }

            endOfFile = false;
        }
    }

    public boolean isEndOfFile() {
        return endOfFile;
    }

    public long getCurrentAbsolutePosition() {
        return currentPosition;
    }

    public int getCurrentLine() {
        return currentLine;
    }

    public int getCurrentColumn() {
        return currentColumn;
    }

    public String getFileName() {
        return fileName;
    }

    public void reset() throws IOException {
        file.seek(0);
        currentPosition = 0;
        currentLine = 1;
        currentColumn = 0;
        endOfFile = false;
    }

    @Override
    public void close() throws IOException {
        if (file != null) {
            file.close();
        }
    }
}