package com.softeq;

import java.util.ArrayList;

/**
 * SearchInput class is an entity for handling search input from user.
 * Has fields <b>seed</b>, <b>linkDepth</b>, <b>maxVisitedPagesLimit</b> and <b>searchTermsList</b>.
 */
public class SearchInput {
    /**
     * Field seed contains start page for web crawling.
     */
    private final String seed;
    /**
     * Field linkDepth contains the length of chain of hits if a web crawler follows 1 link from each page.
     */
    private final int linkDepth;
    /**
     * Field maxVisitedPagesLimit contains a number of visited pages after which web crawler should stop.
     */
    private final int maxVisitedPagesLimit;
    /**
     * Field searchTermsList contains search terms specified by user which need to be counted on every page.
     */
    private final ArrayList <String> searchTermsList;

    public SearchInput(String seed, int linkDepth, int maxVisitedPagesLimit, ArrayList<String> searchTermsList) {
        this.seed = seed;
        this.linkDepth = linkDepth;
        this.maxVisitedPagesLimit = maxVisitedPagesLimit;
        this.searchTermsList = searchTermsList;
    }

    public String getSeed() {
        return seed;
    }

    public int getLinkDepth() {
        return linkDepth;
    }

    public int getMaxVisitedPagesLimit() {
        return maxVisitedPagesLimit;
    }

    public ArrayList<String> getSearchTermsList() {
        return searchTermsList;
    }
}