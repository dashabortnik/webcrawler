package com.softeq.input;

import com.softeq.constant.Constant;
import com.softeq.constant.ConstantConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.UrlValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class for checking and processing input data which prepares it for web crawler.
 */
public class ParametersResolver {

    final Logger logger = LogManager.getLogger(ParametersResolver.class);

    /**
     * ResolveParams method receives input data from user and checks its validity.
     *
     * @param seed            is start page
     * @param searchTermsLine contains search terms separated by commas
     * @param linkDepth       is number of moves to a new page in depth
     * @param maxPagesLimit   is max number of moves
     * @return SearchInput object with corrected data
     */
    public SearchInput resolveParams(String seed, String searchTermsLine, int linkDepth, int maxPagesLimit) {

        int inputLinkDepth = Integer.parseInt(ConstantConfig.getInstance().getProperty(Constant.DEF_LINK_DEPTH));
        if (linkDepth > 0) {
            inputLinkDepth = linkDepth;
        }

        int inputMaxPagesLimit = Integer.parseInt(ConstantConfig.getInstance().getProperty(Constant.DEF_VISITED_PAGES_LIMIT));
        if (maxPagesLimit > 0) {
            inputMaxPagesLimit = maxPagesLimit;
        }

        /* If input data is valid, a string of search terms is parsed into an array trimming extra spaces
          and converted into a list. All input data is used to create a SearchInput object.*/
        if (StringUtils.isEmpty(seed) || StringUtils.isEmpty(searchTermsLine)) {
            logger.warn("Not enough data was provided to ParametersResolver: either seed or search terms are empty.");
            return null;
        } else {
            ArrayList<String> searchTermsList = new ArrayList<>(Arrays.asList(StringUtils.stripAll(searchTermsLine.split(","))));
            return new SearchInput(seed, inputLinkDepth, inputMaxPagesLimit, searchTermsList);
        }
    }

    public boolean isInvalidUrl(String seed) {
        String[] schemes = {"http", "https"};
        UrlValidator urlValidator = new UrlValidator(schemes);
        if (urlValidator.isValid(seed)) {
            logger.debug("Provided URL " + seed + " is valid");
        } else {
            logger.warn("Provided URL " + seed + " is invalid");
        }

        final int maxConnAttempts = 3;
        int connAttempts = 0;
        int code;
        do {
            URL u;
            HttpURLConnection huc;
            try {
                u = new URL(seed);
                huc = (HttpURLConnection) u.openConnection();
                huc.setRequestMethod("HEAD");
                huc.connect();
                code = huc.getResponseCode();
                ++connAttempts;
                System.out.println("CODE=" + code);
                if (code == HttpURLConnection.HTTP_MOVED_TEMP
                    || code == HttpURLConnection.HTTP_MOVED_PERM
                    || code == HttpURLConnection.HTTP_SEE_OTHER) {
                    seed = huc.getHeaderField("Location");

                    System.out.println("New seed: " + seed + " \nConnection attempts: " + connAttempts);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return true;
            }
        } while (code != HttpURLConnection.HTTP_OK && connAttempts <= maxConnAttempts);
        return !(code==HttpURLConnection.HTTP_OK);
    }

    public boolean isNullOrEmptyString(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            logger.warn("Provided search terms are empty.");
            return true;
        } else {
            logger.debug("Provided search terms are not empty.");
            return false;
        }
    }
}
