package com.softeq.service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * SearchResult class is an entity for handling data from web crawling.
 * Has fields <b>link</b>, <b>totalHits</b>, and a list of <b>hitsByWord</b>.
 */

public class SearchResult {
    /**
     * Field link contains a link where search terms specified by user were counted.
     */
    private final String link;
    /**
     * Field totalHits contains a sum of all appearances of search words on this page.
     */
    private final int totalHits;
    /**
     * Field hitsByWord contains a list of individual appearances of every search word on this page.
     */
    private final List<Integer> hitsByWord;

    public SearchResult(String link, int totalHits, List<Integer> hitsByWord) {
        this.link = link;
        this.totalHits = totalHits;
        this.hitsByWord = hitsByWord;
    }

    /**
     * ToCSVStringHitsByWord method turns data from SearchResult object into a specified format for CSV file.
     *
     * @return returns a string of comma-separated values which contain <b>link </b>
     * and all values from <b>hitsByWord</b> list.
     * @see SearchResult
     */
    public String toCSVStringHitsByWord() {
        StringBuilder sb = new StringBuilder(this.link).append(",");
        List<Integer> hitsByWord = this.getHitsByWord();
        String hitsByWordString = hitsByWord.stream().map(Object::toString)
            .collect(Collectors.joining(","));
        sb.append(hitsByWordString);
        return sb.toString();
    }

    public String getLink() {
        return link;
    }

    public int getTotalHits() {
        return totalHits;
    }

    public List<Integer> getHitsByWord() {
        return hitsByWord;
    }
}
