package com.softeq;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

public class WebCrawler {
    private HashSet<String> links;

    public WebCrawler() {
        links = new HashSet<String>();
    }

    public void getPageLinks(SearchInput searchInput, int depthCounter) {

        String seed = searchInput.getSeed();
        final int linkDepth = searchInput.getLinkDepth();
        final int maxPagesNumber = searchInput.getMaxVisitedPagesLimit();
        //add max number logic

        // Check if you have already crawled the URLs
        if (!links.contains(seed) && (depthCounter < linkDepth)) {
            System.out.println("Depth: " + depthCounter + " [" + seed + "]");
            try {

                if (links.add(seed)) {
                    System.out.println(seed);
                }

                // Fetch the HTML code
                Document document = Jsoup.connect(seed).get();

                //extract the whole document body
                String html = document.body().toString();
                String parsedText = Jsoup.parse(html).text().toLowerCase();


                //change seq into user input!!!


                //count occurrences of given string in the text
                String seq = "Java";
                int number = org.apache.commons.lang3.StringUtils.countMatches (parsedText, seq.toLowerCase());
                System.out.println("Count of phrase " + seq + " is: " + number);

                // need to try loading the whole page
                // as current solution doesn't catch info loaded by javascript
                // maybe need to use htmlunit

                // Parse the HTML to extract links to other URLs
                Elements linksOnPage = document.select("a[href]");
                depthCounter++;
                // For each extracted URL invoke the method getPageLinks recursively again
                for (Element page : linksOnPage) {
                    getPageLinks(new SearchInput(page.attr("abs:href"), linkDepth, maxPagesNumber), depthCounter);
                }
            } catch (IOException e) {
                System.err.println("For '" + seed + "': " + e.getMessage());
            }
        }
    }
}
