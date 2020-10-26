package com.softeq.service;

import com.softeq.input.Link;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class CrawlerExecutor {

    final Logger logger = LogManager.getLogger(CrawlerExecutor.class);

    public static final String CSS_LINK_SELECTOR = "a[href]";

    private Document document;

    private List<Integer> hitsByWord = new ArrayList<>();

    public void crawl(Link url, Queue<Link> pagesToVisit) {
        try {
            this.document = Jsoup.connect(url.getUrl()).get();
            System.out.println("Received web page at " + url.getUrl() + " with depth " + url.getUrlDepth());

            Elements linksOnPage = document.select(CSS_LINK_SELECTOR);

            for(Element link : linksOnPage) {
                int newDepth = url.getUrlDepth()+1;
                Link tempLink = new Link(link.absUrl("href"), newDepth);
                pagesToVisit.add(tempLink);
            }

//            for (Link element : pagesToVisit) {
//                System.out.println(element.toString());
//            }

        } catch(IOException ioe) {
            System.out.println("Error in out HTTP request " + ioe);
        }
    }

    public int countWordMatches(ArrayList<String> searchTermsList){
        int totalHitsNumber = 0;

        String parsedText = this.document.body().text().toLowerCase();

        //count occurrences of given search words in the text
        for (String searchWord : searchTermsList) {
            int number = StringUtils.countMatches(parsedText, searchWord.toLowerCase());
            logger.debug("Count of phrase <" + searchWord + "> is: " + number);

            //add to list of hits for this link
            this.hitsByWord.add(number);

            //add to sum of all hits for this link
            totalHitsNumber = totalHitsNumber + number;
            logger.debug("Total hits: " + totalHitsNumber);
        }
        return totalHitsNumber;
    }

    public List<Integer> getHitsByWord() {
        return hitsByWord;
    }
}
