package com.softeq;

public class App 
{
    public static void main( String[] args )
    {
        UserInputHandler uih = new UserInputHandler();

        new WebCrawler().getPageLinks(uih.getCrawlingParameters(), 0);
    }
}
