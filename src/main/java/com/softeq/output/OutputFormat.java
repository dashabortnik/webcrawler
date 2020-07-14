package com.softeq.output;

public class OutputFormat {

    private String type;
    private int entriesNumber = 0;
    private String sort;
    private String filepath;

    public OutputFormat(String type, Integer entriesNumber, String sort, String filepath) {
        this.type = type;
        if(entriesNumber!=null){
            this.entriesNumber = entriesNumber;
        }
        this.sort = sort;
        this.filepath = filepath;
    }

    public String getType() {
        return type;
    }

    public int getEntriesNumber() {
        return entriesNumber;
    }

    public String getSort() {
        return sort;
    }

    public String getFilepath() {
        return filepath;
    }
}
