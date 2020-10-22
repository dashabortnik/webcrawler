package com.softeq;

import com.softeq.service.SearchResult;
import junit.framework.TestCase;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchResultTest extends TestCase {

    public void testToCSVStringHitsByWord() {
        SearchResult testSR = new SearchResult("https://github.com/", 10, new ArrayList<>(Arrays.asList(1, 0, 4, 5)));
        String result = testSR.toCSVStringHitsByWord();

        Assert.assertNotNull(result);
        Assert.assertFalse(result.isEmpty());
        Assert.assertEquals("Output should contain a link and hitsByWord as comma-separated values.",
            "https://github.com/,1,0,4,5", result);
    }
}