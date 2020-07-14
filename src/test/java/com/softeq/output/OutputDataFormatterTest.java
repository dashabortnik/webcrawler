package com.softeq.output;

import com.softeq.service.SearchResult;
import junit.framework.TestCase;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OutputDataFormatterTest extends TestCase {

    public void testFormatOutputData() {

    OutputDataFormatter odf = new OutputDataFormatter();
    ArrayList<SearchResult> searchData = new ArrayList<>();
    searchData.add(new SearchResult("abcd.com", 18, new ArrayList<>(Arrays.asList(3, 4, 5, 6))));
    ArrayList<String> searchTermsList = new ArrayList<>(Arrays.asList("by","com","ru","for"));
    OutputFormat outputFormat = new OutputFormat("console", 5,"asc", "output.csv");

    List<String> resultList = odf.formatOutputData(searchData, searchTermsList, outputFormat);
    Assert.assertNotNull(resultList);
    Assert.assertEquals(2, resultList.size());
    Assert.assertEquals("Link,by,com,ru,for", resultList.get(0));
    Assert.assertEquals("abcd.com,3,4,5,6", resultList.get(1));

    }
}