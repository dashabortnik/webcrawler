package com.softeq;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class SearchResult {
    private String link;
    private int totalHits;
    private ArrayList <Integer> hitsByWord;

    public SearchResult(String link, int totalHits, ArrayList<Integer> hitsByWord) {
        this.link = link;
        this.totalHits = totalHits;
        this.hitsByWord = hitsByWord;
    }

    String toCSVStringHitsByWord(){
       StringBuilder sb = new StringBuilder(this.link).append(",");
       ArrayList <Integer> hitsByWord = this.getHitsByWord();
       String hitsByWordString = hitsByWord.stream().map(Object::toString)
               .collect(Collectors.joining(", "));
       sb.append(hitsByWordString);
       return sb.toString();
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(int totalHits) {
        this.totalHits = totalHits;
    }

    public ArrayList<Integer> getHitsByWord() {
        return hitsByWord;
    }

    public void setHitsByWord(ArrayList<Integer> hitsByWord) {
        this.hitsByWord = hitsByWord;
    }
}
