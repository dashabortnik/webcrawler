package com.softeq.service;

import com.softeq.input.Link;
import com.softeq.input.LinkNormalizer;
import com.softeq.input.SearchInput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class for web crawling according to specified parameters.
 */
public class WebCrawler {

    final Logger logger = LogManager.getLogger(WebCrawler.class);

    /**
     * Field links contains a set of all links visited by web crawler.
     */
    private final Map<Integer, Link> links;
    /**
     * Field searchData contains a list of SearchResult objects for all visited pages.
     */
    private final Queue<SearchResult> searchData;
    /**
     * Field pagesToVisit contains a queue of links that haven't been crawled yet.
     */
    private final Queue<Link> pagesToVisit = new ConcurrentLinkedQueue<>();
    private int maxPagesNumber;
    private int linkDepth;
    AtomicInteger pageCounter = new AtomicInteger(0);
    CountDownLatch completedThreadCounter;

    public WebCrawler() {
        links = new ConcurrentHashMap<>();
        searchData = new ConcurrentLinkedQueue<>();
    }

    public Queue<SearchResult> getSearchData() {
        return searchData;
    }

    /**
     * Method getPageLinks fetches HTML code of the page, parses it into a String, counts matches for all search terms
     * in searchTermsList, extracts all links from the page and follows them recursively.
     *
     * @param searchInput contains all search parameters specified by user
     */
    public void execute(SearchInput searchInput) throws InterruptedException {

        //use LinkNormalizer which adds http, if missing, and normalizes the URL
        LinkNormalizer ln = new LinkNormalizer();
        Link link = searchInput.getSeed();
        link.setUrl(ln.normalizeUrl(link.getUrl()));

        maxPagesNumber = searchInput.getMaxVisitedPagesLimit();
        completedThreadCounter = new CountDownLatch(maxPagesNumber);

        linkDepth = searchInput.getLinkDepth();
        ArrayList<String> searchTermsList = searchInput.getSearchTermsList();

        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new TaskInitializer(link, searchTermsList));
        completedThreadCounter.await();
        pool.shutdownNow();

    }

    private class TaskInitializer extends RecursiveAction {

        private final Link link;
        private final List<String> searchTermsList;

        public TaskInitializer(Link link, List<String> searchTermsList) {
            this.link = link;
            this.searchTermsList = searchTermsList;
        }

        @Override
        protected void compute() {
                if (pageCounter.incrementAndGet() <= maxPagesNumber) {
                    //process given link in class field
                    CrawlerExecutor crawlerExecutor = new CrawlerExecutor();
//                    boolean depthExceeded =
                    crawlerExecutor.crawl(link, pagesToVisit, linkDepth);
                    links.put(links.size() + 1, link);
                    int totalHitsNumber = crawlerExecutor.countWordMatches(searchTermsList);
                    List<Integer> hitsByWord = crawlerExecutor.getHitsByWord();
                    searchData.add(new SearchResult(link.getUrl(), totalHitsNumber, hitsByWord));
                    completedThreadCounter.countDown();
                    for (Link link : pagesToVisit) {
                        TaskInitializer taskInitializer = new TaskInitializer(pagesToVisit.poll(), searchTermsList);
                        taskInitializer.fork();
                    }
//                    if(depthExceeded){
//                        while(completedThreadCounter.getCount()>0){
//                            completedThreadCounter.countDown();
//                        }
//                    }
                }


        }
    }
}
