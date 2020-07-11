package com.softeq;

import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class UserInputHandler {

    SearchInput getCrawlingParameters(){

        String seed = null;
        int userLinkDepth = Constant.DEF_LINK_DEPTH.getValue();
        int userMaxPagesLimit = Constant.DEF_VISITED_PAGES_LIMIT.getValue();
        String searchTermsLine = null;
        SearchInput searchInput = null;

        try (Scanner in = new Scanner(System.in)){
            // ask user for seed and check validity of url
            do{
                System.out.println("Please provide a starting URL (seed) for web crawling.");
                seed = in.nextLine();
                System.out.println("You entered seed: " + seed);
            } while (!validateURL(seed));

            //ask user for search terms separated by commas
            System.out.println("Please provide search terms separated by commas.");
            searchTermsLine = in.nextLine();
            System.out.println("You entered search terms: " + searchTermsLine);

            //ask user for linkDepth
            System.out.println("Please provide a link depth as a positive integer.");
            userLinkDepth = in.nextInt();
            System.out.println("You entered link depth: " + userLinkDepth);

            //ask user for maxVisitedPagesLimit
            System.out.println("Please provide a max pages limit as a positive integer.");
            userMaxPagesLimit = in.nextInt();
            System.out.println("You entered link depth: " + userMaxPagesLimit);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!StringUtils.isEmpty(seed) && !StringUtils.isEmpty(searchTermsLine)){

            //parse string into array, remove spaces at the start and end of each part, convert into a list
            ArrayList<String> searchTermsList = new ArrayList<>(Arrays.asList(StringUtils.stripAll(searchTermsLine.split(","))));
            searchInput = new SearchInput(seed, userLinkDepth, userMaxPagesLimit, searchTermsList);
        } else {
            System.out.println("Further operation is impossible: not enough data was provided.");
        }
        return searchInput;
    }

    boolean validateURL(String seed){
        try {
            new URL(seed).toURI();
            return true;
        } catch (Exception e) {
            System.out.println("Provided url " + seed + " is invalid. " + e);
            return false;
        }
    }

}
