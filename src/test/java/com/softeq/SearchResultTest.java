package com.softeq;

import com.softeq.service.SearchResult;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchResultTest {

    @Test
    public void testToCSVStringHitsByWord() {
        SearchResult testSR = new SearchResult("https://github.com/", 10, new ArrayList<>(Arrays.asList(1, 0, 4, 5)));
        Assert.assertEquals("Output should contain a link and hitsByWord as comma-separated values.",
                "https://github.com/,1,0,4,5", testSR.toCSVStringHitsByWord());
    }
}