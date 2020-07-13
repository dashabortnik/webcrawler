package com.softeq.input;

import com.softeq.constant.Constant;
import com.softeq.constant.ConstantConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class ParametersResolver {

    final Logger logger = LogManager.getLogger(ParametersResolver.class);

    public SearchInput resolveParams(String seed, String searchTermsLine, int linkDepth, int maxPagesLimit){

        int inputLinkDepth = Integer.parseInt(ConstantConfig.getInstance().getProperty(Constant.DEF_LINK_DEPTH));
        logger.debug("Default depth:" + inputLinkDepth);
        if (linkDepth>0){
            inputLinkDepth = linkDepth;
        }

        int inputMaxPagesLimit = Integer.parseInt(ConstantConfig.getInstance().getProperty(Constant.DEF_VISITED_PAGES_LIMIT));
        logger.debug("Default page limit:" + inputMaxPagesLimit);
        if (maxPagesLimit>0){
            inputMaxPagesLimit = maxPagesLimit;
        }

        /* If input data is valid, a string of search terms is parsed into an array trimming extra spaces
          and converted into a list. All input data is used to create a SearchInput object.*/
        if (!StringUtils.isEmpty(seed) && !StringUtils.isEmpty(searchTermsLine)){
            ArrayList<String> searchTermsList = new ArrayList<>(Arrays.asList(StringUtils.stripAll(searchTermsLine.split(","))));
            return new SearchInput(seed, inputLinkDepth, inputMaxPagesLimit, searchTermsList);
        } else {
            logger.warn("Not enough data was provided to ParametersResolver: either seed or search terms are empty.");
            return null;
        }
    }

    public boolean isInvalidUrl(String seed){
        try {
            new URL(seed).toURI();
            return false;
        } catch (MalformedURLException e){
            logger.warn("Provided string " + seed + "is not a valid URL. " + e);
            return true;
        } catch (Exception e) {
            logger.warn("Validity check on " + seed + " failed. " + e);
            return true;
        }
    }
}
