package com.softeq.service;

import com.softeq.input.LinkNormalizer;
import com.softeq.input.SearchInput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Class for web crawling according to specified parameters.
 */
public class WebCrawler {

    final Logger logger = LogManager.getLogger(WebCrawler.class);

    public static final String PAGE_ATTRIBUTE_LINK_SELECTOR = "abs:href";

    /**
     * Field links contains a set of all links visited by web crawler.
     */
    private final HashSet<String> links;
    /**
     * Field searchData contains a list of SearchResult objects for all visited pages.
     */
    private final ArrayList<SearchResult> searchData;
    /**
     * Field pagesToVisit contains a queue of links that haven't been crawled yet.
     */
    private final Queue<String> pagesToVisit = new ConcurrentLinkedQueue<>();

    public WebCrawler() {
        links = new HashSet<>();
        searchData = new ArrayList<>();
    }

    public ArrayList<SearchResult> getSearchData() {
        return searchData;
    }

    /**
     * Method getPageLinks fetches HTML code of the page, parses it into a String, counts matches for all search terms
     * in searchTermsList, extracts all links from the page and follows them recursively.
     *
     * @param searchInput  contains all search parameters specified by user
     * @param depthCounter counts the depth of web crawling
     *                     which is the length of chain of hits if a web crawler follows 1 link from each page.
     */
    public void getPageLinks(SearchInput searchInput, int depthCounter) {

        //use LinkNormalizer which adds http, if missing, and normalizes the URL
        LinkNormalizer ln = new LinkNormalizer();
        String seed = ln.normalizeUrl(searchInput.getSeed());

        final int maxPagesNumber = searchInput.getMaxVisitedPagesLimit();
        ArrayList<String> searchTermsList = searchInput.getSearchTermsList();

        while(this.links.size()< maxPagesNumber) {
            String currentUrl;
            if (this.links.isEmpty()) {
                currentUrl = seed;
            } else {
                currentUrl = this.getNextUrl();
            }

            if(currentUrl!=null) {
                CrawlerExecutor crawlerExecutor = new CrawlerExecutor();
                crawlerExecutor.crawl(currentUrl, pagesToVisit);
                links.add(currentUrl);
                int totalHitsNumber = crawlerExecutor.countWordMatches(searchTermsList);
                List<Integer> hitsByWord = crawlerExecutor.getHitsByWord();
            } else {
                System.out.println("There are no other links to follow.");
                System.exit(0);
            }
        }
    }

    /**
     * Returns the next URL to visit with a check that we haven't visited it before.      *
     * @return String value of the next URl
     */
    private String getNextUrl() {
        String nextUrl;
        LinkNormalizer linkNormalizer = new LinkNormalizer();
        do {
            nextUrl = this.pagesToVisit.poll();

            if(nextUrl!=null){
                nextUrl = linkNormalizer.normalizeUrl(nextUrl);
            }
        } while(this.links.contains(nextUrl));
        return nextUrl;
    }




}
