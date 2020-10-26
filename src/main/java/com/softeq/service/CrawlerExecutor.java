package com.softeq.service;

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

    public void crawl(String url, Queue<String> pagesToVisit) {
        try {
            this.document = Jsoup.connect(url).get();
            System.out.println("Received web page at " + url);

            Elements linksOnPage = document.select(CSS_LINK_SELECTOR);

            for(Element link : linksOnPage) {
                pagesToVisit.add(link.absUrl("href"));
            }
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