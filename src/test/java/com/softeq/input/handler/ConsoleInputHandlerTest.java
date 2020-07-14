package com.softeq.input.handler;

import com.softeq.input.SearchInput;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConsoleInputHandlerTest{

    @Test
    public void testGetCrawlingParameters() {

        ByteArrayInputStream inContent = new ByteArrayInputStream("https://stackoverflow.com/\njava, tutorial, com\n2\n4".getBytes());
        InputStream originalIn = System.in;
        System.setIn(inContent);

        ConsoleInputHandler cih = new ConsoleInputHandler();
        List<SearchInput> searchInputList = cih.getCrawlingParameters();

        List<SearchInput> resultSearchInputList = new ArrayList<>();
        ArrayList<String> searchTermsList = new ArrayList<>(Arrays.asList("java", "tutorial", "com"));
        resultSearchInputList.add(new SearchInput("https://stackoverflow.com/", 2, 4, searchTermsList));

        Assert.assertNotNull(searchInputList);
        Assert.assertEquals(1, searchInputList.size());
        Assert.assertEquals(resultSearchInputList, searchInputList);

        System.setIn(originalIn);
    }
}