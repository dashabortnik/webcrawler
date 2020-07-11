package com.softeq;

public enum Constant {

    /**
     * DEF_LINK_DEPTH - default value of link depth which equals the length of chain of hits
     * if a web crawler follows 1 link from each page.
     *
     * DEF_VISITED_PAGES_LIMIT - default value of maximum visited pages limit which means
     * web crawler should stop after hitting this number of links.
     */

    DEF_LINK_DEPTH(1), DEF_VISITED_PAGES_LIMIT (10000), NUMBER_OF_TOP_ENTRIES_TO_PRINT(10);

    private int value;

    Constant(int i) {
        this.value=i;
    }

    public int getValue(){ return value;}
}
