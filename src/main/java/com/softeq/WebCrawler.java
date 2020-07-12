package com.softeq;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Class for web crawling according to specified parameters.
 */

public class WebCrawler {

    final Logger logger = LogManager.getLogger(WebCrawler.class);

    public static final String CSS_LINK_SELECTOR = "a[href]";
    public static final String PAGE_ATTRIBUTE_LINK_SELECTOR = "abs:href";

    /**
     * Field links contains a set of all links visited by web crawler.
     */
    private HashSet<String> links;
    /**
     * Field searchData contains a list of SearchResult objects for all visited pages.
     */
    private List<SearchResult> searchData;
    /**
     * Field visitedPagesCounter counts total number of pages visited by web crawler.
     */
    private int visitedPagesCounter = 0;

    public WebCrawler() {
        links = new HashSet<>();
        searchData = new ArrayList<>();
    }

    public List<SearchResult> getSearchData() {
        return searchData;
    }

    /**
     * Method getPageLinks carries out web crawling, which means that it fetches HTML code of the page,
     * parses it into a String, counts matches for all search terms in searchTermsList,
     * extracts all links from the page and follows them recursively.
     * @param searchInput contains all search parameters specified by user
     * @param depthCounter counts the depth of web crawling
     * which is the length of chain of hits if a web crawler follows 1 link from each page.
     */
    public void getPageLinks(SearchInput searchInput, int depthCounter) {

        /*
          Fields <b>seed</b>, <b>linkDepth</b>, <b>maxPagesNumber</b>,
          and <b>searchTermsList</b> are extracted from SearchInput object
          and contain search parameters provided by user.
         */
        String seed = searchInput.getSeed();
        final int linkDepth = searchInput.getLinkDepth();
        final int maxPagesNumber = searchInput.getMaxVisitedPagesLimit();
        ArrayList <String> searchTermsList = searchInput.getSearchTermsList();

        /*
          Field totalHits counts a sum of hits for all search terms on this page.
         */
        int totalHits = 0;
        /*
          Field hitsByWord contains a list of individual appearances of
           every search word on this page.
         */
        ArrayList <Integer> hitsByWord = new ArrayList<>();

        // Check if you have already crawled the URLs
        if (!links.contains(seed) && (depthCounter < linkDepth) && visitedPagesCounter < maxPagesNumber) {
            logger.debug("Current depth: " + depthCounter + " [" + seed + "]");
            try {

                if (links.add(seed)) {
                    System.out.println(seed);
                }

                // Fetch the HTML code
                Document document = Jsoup.connect(seed).get();

                visitedPagesCounter++;
                logger.debug("Current page count: " + visitedPagesCounter);


                //extract the whole document body
                String html = document.body().toString();
                String parsedText = Jsoup.parse(html, seed).text().toLowerCase();

                //System.out.println("TEXT: " + parsedText);

                //count occurrences of given search words in the text
                for (String searchWord : searchTermsList){
                    int number = StringUtils.countMatches (parsedText, searchWord.toLowerCase());
                    logger.debug("Count of phrase <" + searchWord + "> is: " + number);

                    //add to list of hits for this link
                    hitsByWord.add(number);

                    //add to sum of all hits for this link
                    totalHits = totalHits + number;
                    logger.debug("Total hits: " + totalHits);
                }

                // Parse the HTML to extract links to other URLs
                Elements linksOnPage = document.select(CSS_LINK_SELECTOR);
                depthCounter++;

                searchData.add(new SearchResult(seed, totalHits, hitsByWord));

                // For each extracted URL invoke the method getPageLinks recursively again
                for (Element page : linksOnPage) {
                    getPageLinks(new SearchInput(page.attr(PAGE_ATTRIBUTE_LINK_SELECTOR), linkDepth, maxPagesNumber, searchTermsList), depthCounter);
                }
            } catch (IOException e) {
                logger.warn("Exception for '" + seed + "': " + e);
            }
        }
    }
}
