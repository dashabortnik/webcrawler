package com.softeq.input.handler;

import com.softeq.input.ParametersResolver;
import com.softeq.input.SearchInput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.shekhargulati.urlcleaner.UrlCleaner;

/**
 * Handler class for user input from console. Accepts a single search query.
 */
public class ConsoleInputHandler implements InputHandler {

    final Logger logger = LogManager.getLogger(ConsoleInputHandler.class);

    /**
     * GetCrawlingParameters method requests user input and processes it into a SearchInput object.
     *
     * @return list of SearchInput objects
     * @see SearchInput
     */
    @Override
    public List<SearchInput> getCrawlingParameters() {

        String seed = null;
        int userLinkDepth = 0;
        int userMaxPagesLimit = 0;
        String searchTermsLine = null;
        ParametersResolver parametersResolver = new ParametersResolver();

        try (Scanner in = new Scanner(System.in)) {

            //For seed: user input is requested until URL validity check in method <b>isValidUrl</b> returns true.
            do {
                System.out.println("Please provide a starting URL (seed) for web crawling.");
                seed = in.nextLine();
                logger.debug("User entered seed: " + seed);
//                seed = UrlCleaner.normalizeUrl(seed);
//                logger.debug("Normalized seed: " + seed);
            } while (parametersResolver.isInvalidUrl(seed));

            //For searchTerms: user input is requested in form of a string of comma-separated values.
            do {
                System.out.println("Please provide search terms separated by commas.");
                searchTermsLine = in.nextLine();
                logger.debug("User entered search terms: " + searchTermsLine);
            } while (parametersResolver.isNullOrEmptyString(searchTermsLine));

            //For linkDepth: user input is requested in form of a positive integer.
            System.out.println("Please provide a link depth as a positive integer.");
            userLinkDepth = in.nextInt();
            logger.debug("User entered link depth: " + userLinkDepth);

            //For maxPagesLimit: user input is requested in form of a positive integer.
            System.out.println("Please provide a max pages limit as a positive integer.");
            userMaxPagesLimit = in.nextInt();
            logger.debug("User entered link depth: " + userMaxPagesLimit);

        } catch (NoSuchElementException | IllegalStateException e) {
            logger.warn("Expected input element wasn't found or scanner was closed. " + e);
        } catch (Exception e) {
            logger.warn("Exception occurred during scanner input. " + e);
        }

        List<SearchInput> searchInputList = new ArrayList<>();
        SearchInput searchInput = parametersResolver.resolveParams(seed, searchTermsLine, userLinkDepth, userMaxPagesLimit);
        if (searchInput != null) {
            searchInputList.add(searchInput);
        }
        return searchInputList;
    }
}
