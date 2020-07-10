package com.softeq;

public class SearchInput {
    private String seed;
    private int linkDepth;
    private int maxVisitedPagesLimit;

    public SearchInput(String seed, int linkDepth, int maxVisitedPagesLimit) {
        this.seed = seed;
        this.linkDepth = linkDepth;
        this.maxVisitedPagesLimit = maxVisitedPagesLimit;
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
}
