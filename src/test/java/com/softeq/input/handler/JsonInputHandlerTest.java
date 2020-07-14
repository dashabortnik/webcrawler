package com.softeq.input.handler;

import com.softeq.input.SearchInput;
import junit.framework.TestCase;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonInputHandlerTest extends TestCase {

    public void testGetCrawlingParameters() {
        String path = "src/test/resources/inputDataArray.txt";
        JsonInputHandler jih = new JsonInputHandler(path);
        List<SearchInput> searchInputList = jih.getCrawlingParameters();

        ArrayList<String> arrayList0 = new ArrayList<>(Arrays.asList("java", "tutorial", "com"));
        SearchInput searchInput0 = new SearchInput("https://stackoverflow.com/",2,4, arrayList0);

        ArrayList<String> arrayList1 = new ArrayList<>(Arrays.asList("товар", "ME"));
        SearchInput searchInput1 = new SearchInput("https://www.onliner.by",3,5, arrayList1);

        Assert.assertNotNull(searchInputList);
        Assert.assertEquals(2, searchInputList.size());
        Assert.assertEquals("EqualsTest1", searchInput0, searchInputList.get(0));
        Assert.assertEquals("EqualsTest2",searchInput1, searchInputList.get(1));
    }

    public void testGetCrawlingParametersWrongArg(){
        String path = "123";
        JsonInputHandler jih = new JsonInputHandler(path);
        List<SearchInput> searchInputList = jih.getCrawlingParameters();
        Assert.assertEquals(0, searchInputList.size());
    }
}