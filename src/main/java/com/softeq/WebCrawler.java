package com.softeq;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class WebCrawler {
    private HashSet<String> links;
    private int visitedPagesCounter = 0;
    private List<SearchResult> searchData;

    public List<SearchResult> getSearchData() {
        return searchData;
    }

    public WebCrawler() {
        links = new HashSet<>();
        searchData = new ArrayList<>();
    }

    public void getPageLinks(SearchInput searchInput, int depthCounter) {

        String seed = searchInput.getSeed();
        final int linkDepth = searchInput.getLinkDepth();
        final int maxPagesNumber = searchInput.getMaxVisitedPagesLimit();
        ArrayList <String> searchTermsList = searchInput.getSearchTermsList();
        int totalHits = 0;
        ArrayList <Integer> hitsByWord = new ArrayList<>();

        // Check if you have already crawled the URLs
        if (!links.contains(seed) && (depthCounter < linkDepth) && visitedPagesCounter < maxPagesNumber) {
            System.out.println("Depth: " + depthCounter + " [" + seed + "]");
            try {

                if (links.add(seed)) {
                    System.out.println(seed);
                }

                // Fetch the HTML code
                Document document = Jsoup.connect(seed).timeout(5000).get();

                visitedPagesCounter++;
                System.out.println("Page count: " + visitedPagesCounter);

                //extract the whole document body
                String html = document.body().toString();
                String parsedText = Jsoup.parse(html, seed).text().toLowerCase();

//                InputStream inStream = new URL(seed).openStream();
//                Document document = Jsoup.parse(inStream, "utf-8", seed);
//                String parsedText = document.text().toLowerCase();
                //check again!!!

//                System.out.println("TEXT: " + parsedText);

                //count occurrences of given search words in the text
                for (String searchWord : searchTermsList){
                    int number = org.apache.commons.lang3.StringUtils.countMatches (parsedText, searchWord.toLowerCase());
                    System.out.println("Count of phrase <" + searchWord + "> is: " + number);

                    //add to list of hits for this link
                    hitsByWord.add(number);

                    //add to sum of all hits for this link
                    totalHits = totalHits + number;
                    System.out.println("Total hits: " + totalHits);
                }

                // need to try loading the whole page
                // as current solution doesn't catch info loaded by javascript
                // maybe need to use htmlunit

                // Parse the HTML to extract links to other URLs
                Elements linksOnPage = document.select("a[href]");
                depthCounter++;

                searchData.add(new SearchResult(seed, totalHits, hitsByWord));
                System.out.println("SEARCH DATA: " + searchData.toString());

                // For each extracted URL invoke the method getPageLinks recursively again
                for (Element page : linksOnPage) {
                    getPageLinks(new SearchInput(page.attr("abs:href"), linkDepth, maxPagesNumber, searchTermsList), depthCounter);
                }


            } catch (IOException e) {
                System.err.println("For '" + seed + "': " + e.getMessage());
            }
        }
    }
}
