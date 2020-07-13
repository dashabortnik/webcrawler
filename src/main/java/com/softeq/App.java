package com.softeq;

public class App
{
    public static void main( String[] args ){

        JsonInputHandler jih = new JsonInputHandler();
        jih.getCrawlingParameters(args[0]);

        UserInputHandler uih = new UserInputHandler();
        WebCrawler wc = new WebCrawler();
        wc.getPageLinks(uih.getCrawlingParameters(), 0);

        OutputHandler oh = new OutputHandler();
        oh.printAllData(wc.getSearchData());
        oh.printTopDataToFile(wc.getSearchData());
    }
}
