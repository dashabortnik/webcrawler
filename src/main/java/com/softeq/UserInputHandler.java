package com.softeq;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class for handling of user input for web crawling.
 */
public class UserInputHandler {

    final Logger logger = LogManager.getLogger(UserInputHandler.class);

    /**
     * GetCrawlingParameters method requests user input and processes it into a SearchInput object.
     * @return returns SearchInput object
     * @see SearchInput
     */
    SearchInput getCrawlingParameters(){

        String seed = null;
        int userLinkDepth = Constant.DEF_LINK_DEPTH.getValue();
        int userMaxPagesLimit = Constant.DEF_VISITED_PAGES_LIMIT.getValue();
        String searchTermsLine = null;
        SearchInput searchInput = null;

        try (Scanner in = new Scanner(System.in)){

            /*
              For seed: user input is requested until URL validity check in method <b>isValidUrl</b> returns true.
             */
            do{
                System.out.println("Please provide a starting URL (seed) for web crawling.");
                seed = in.nextLine();
                logger.debug("User entered seed: " + seed);
            } while (!isValidUrl(seed));

            /*
              For searchTerms: user input is requested in form of a string of comma-separated values.
             */
            System.out.println("Please provide search terms separated by commas.");
            searchTermsLine = in.nextLine();
            logger.debug("User entered search terms: " + searchTermsLine);

            /*
              For linkDepth: user input is requested in form of a positive integer.
              Initial Constant value is rewritten only if the provided number is positive.
             */
            System.out.println("Please provide a link depth as a positive integer.");
            int inputLinkDepth = in.nextInt();
            if (inputLinkDepth>0){
                userLinkDepth = inputLinkDepth;
            }
            logger.debug("User entered link depth: " + userLinkDepth);

            /*
              For maxPagesLimit: user input is requested in form of a positive integer.
              Initial Constant value is rewritten only if the provided number is positive.
             */
            System.out.println("Please provide a max pages limit as a positive integer.");
            int inputMaxPagesLimit = in.nextInt();
            if (inputMaxPagesLimit>0){
                userMaxPagesLimit = inputMaxPagesLimit;
            }
            logger.debug("User entered link depth: " + userMaxPagesLimit);

        } catch (NoSuchElementException | IllegalStateException e) {
            logger.warn("Expected input element wasn't found or scanner was closed. " + e);
        } catch (Exception e) {
            logger.warn("Exception occurred during scanner input. " + e);
        }

        /*
          If input data is valid, a string of search terms is parsed into an array trimming extra spaces
          and converted into a list. All input data is used to create a SearchInput object.
         */
        if (!StringUtils.isEmpty(seed) && !StringUtils.isEmpty(searchTermsLine)){
            ArrayList<String> searchTermsList = new ArrayList<>(Arrays.asList(StringUtils.stripAll(searchTermsLine.split(","))));
            searchInput = new SearchInput(seed, userLinkDepth, userMaxPagesLimit, searchTermsList);
        } else {
            logger.warn("Further operation is impossible: not enough data was provided.");
        }
        return searchInput;
    }

    boolean isValidUrl(String seed){
        try {
            new URL(seed).toURI();
            return true;
        } catch (MalformedURLException e){
            logger.warn("Provided string " + seed + "is not a valid URL. " + e);
            return false;
        } catch (Exception e) {
            logger.warn("Validity check on " + seed + " failed. " + e);
            return false;
        }
    }
}
