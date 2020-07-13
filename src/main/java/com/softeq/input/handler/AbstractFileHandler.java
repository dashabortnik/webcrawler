package com.softeq.input.handler;

public class AbstractFileHandler {

    private final String fileLink;

    public AbstractFileHandler(String fileLink) {
        this.fileLink = fileLink;
    }

    public String getFileLink() {
        return fileLink;
    }
}
