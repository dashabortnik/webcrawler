package com.softeq;

import com.softeq.input.InputHandlerSolver;
import com.softeq.input.SearchInput;
import com.softeq.output.OutputHandlerSolver;
import com.softeq.service.WebCrawler;

import java.util.List;

public class App
{
    public static void main( String[] args ){

        InputHandlerSolver inputHandlerSolver = new InputHandlerSolver();
        List<SearchInput> searchInputList = inputHandlerSolver.handleInput(args);
        OutputHandlerSolver oh = new OutputHandlerSolver();

        for(SearchInput searchInput : searchInputList){
            WebCrawler webCrawler = new WebCrawler();
            webCrawler.getPageLinks(searchInput, 0);
            oh.handleOutput(webCrawler.getSearchData());
        }
    }
}
