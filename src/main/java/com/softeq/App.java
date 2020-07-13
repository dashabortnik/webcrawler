package com.softeq;

import com.softeq.input.InputHandlerSolver;
import com.softeq.input.SearchInput;
import com.softeq.output.OutputHandler;
import com.softeq.service.WebCrawler;

public class App
{
    public static void main( String[] args ){

        InputHandlerSolver inputHandlerSolver = new InputHandlerSolver();
        SearchInput searchInput = inputHandlerSolver.handleInput(args);
        WebCrawler webCrawler = new WebCrawler();
        webCrawler.getPageLinks(searchInput, 0);

        OutputHandler oh = new OutputHandler();
        oh.printAllData(webCrawler.getSearchData());
        oh.printTopDataToFile(webCrawler.getSearchData());
    }
}
