package com.softeq;

import java.util.ArrayList;

public class SearchInput {
    private String seed;
    private int linkDepth;
    private int maxVisitedPagesLimit;
    ArrayList <String> searchTermsList;

    public SearchInput(String seed, int linkDepth, int maxVisitedPagesLimit, ArrayList<String> searchTermsList) {
        this.seed = seed;
        this.linkDepth = linkDepth;
        this.maxVisitedPagesLimit = maxVisitedPagesLimit;
        this.searchTermsList = searchTermsList;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public int getLinkDepth() {
        return linkDepth;
    }

    public void setLinkDepth(int linkDepth) {
        this.linkDepth = linkDepth;
    }

    public int getMaxVisitedPagesLimit() {
        return maxVisitedPagesLimit;
    }

    public void setMaxVisitedPagesLimit(int maxVisitedPagesLimit) {
        this.maxVisitedPagesLimit = maxVisitedPagesLimit;
    }

    public ArrayList<String> getSearchTermsList() {
        return searchTermsList;
    }

    public void setSearchTermsList(ArrayList<String> searchTermsList) {
        this.searchTermsList = searchTermsList;
    }
}
