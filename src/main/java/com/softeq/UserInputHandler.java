package com.softeq;

import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

public class UserInputHandler {

    SearchInput getCrawlingParameters(){

        String seed = null;
        int userLinkDepth = Constant.DEF_LINK_DEPTH.getValue();
        int userMaxPagesLimit = Constant.DEF_VISITED_PAGES_LIMIT.getValue();
        SearchInput searchInput = null;

        try (Scanner in = new Scanner(System.in)){
            // ask user for seed
            System.out.println("Please provide a starting URL (seed) for web crawling.");
            seed = in.nextLine();
            System.out.println("You entered seed: " + seed);

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

        if (StringUtils.isEmpty(seed)){
            searchInput = new SearchInput(seed, userLinkDepth, userMaxPagesLimit);
        } else {
            System.out.println("Further operation is impossible without a seed.");
        }
        return searchInput;
    }

}
