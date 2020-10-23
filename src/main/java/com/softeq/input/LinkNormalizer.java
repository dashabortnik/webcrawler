package com.softeq.input;
import com.norconex.commons.lang.url.URLNormalizer;

 public class LinkNormalizer {

    public String normalizeUrl(String seed){
        if (!seed.toLowerCase().matches("^\\w+://.*")) {
            seed = "http://" + seed;
        }

        URLNormalizer un = new URLNormalizer(seed);

        un.removeDuplicateSlashes();
        un.removeTrailingHash();
        un.removeTrailingQuestionMark();
        un.removeTrailingSlash();

        un.lowerCaseSchemeHost();
        un.removeDefaultPort();
        un.removeDotSegments();
        un.removeEmptyParameters();
        un.removeSessionIds();
        un.sortQueryParameters();

        return un.toString();
    }



}
