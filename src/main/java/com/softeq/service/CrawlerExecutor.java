package com.softeq.service;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.softeq.input.Link;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;

public class CrawlerExecutor {

    final Logger logger = LogManager.getLogger(CrawlerExecutor.class);

    private String pageAsText;
    private final List<Integer> hitsByWord = new ArrayList<>();

    public void crawl(Link url, Queue<Link> pagesToVisit) {
        disableLogging();
        String urlValue = url.getUrl();
        System.out.println("Loading page: " + urlValue);

        try (WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
            // HtmlUnit emulation browser
            setWebClientOptions(webClient);
            HtmlPage page = webClient.getPage(urlValue);
            webClient.waitForBackgroundJavaScript(10 * 1000); // Wait for js to execute in the background
            pageAsText = page.asText();
            getPageAnchors(url, pagesToVisit, page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int countWordMatches(ArrayList<String> searchTermsList) {
        int totalHitsNumber = 0;
        String parsedText = pageAsText.toLowerCase();
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

    private void setWebClientOptions(WebClient webClient) {
        webClient.getOptions().setJavaScriptEnabled(true); // Enable JS interpreter, default is true
        webClient.getOptions().setCssEnabled(false); // Disable css support
        webClient.getOptions().setThrowExceptionOnScriptError(false); // Whether to throw an exception when js runs incorrectly
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setTimeout(10 * 1000); // Set the connection timeout
    }

    private void disableLogging() {
        // Block HtmlUnit and other system log
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        org.eclipse.jetty.util.log.Log.getProperties().setProperty("org.eclipse.jetty.util.log.announce", "false");
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(Level.OFF);
    }

    private void getPageAnchors(Link url, Queue<Link> pagesToVisit, HtmlPage page) {
        List<HtmlAnchor> anchorsOnPage = page.getAnchors();
        for (HtmlAnchor anchor : anchorsOnPage) {
            try {
                String temp = anchor.getHrefAttribute();
                final URL absUrl;
                absUrl = ((HtmlPage) anchor.getPage()).getFullyQualifiedUrl(temp);
                int newDepth = url.getUrlDepth() + 1;
                Link tempLink = new Link(absUrl.toString(), newDepth);
                pagesToVisit.add(tempLink);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Integer> getHitsByWord() {
        return hitsByWord;
    }
}
