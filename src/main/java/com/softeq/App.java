package com.softeq;

import com.softeq.input.InputHandlerSolver;
import com.softeq.input.SearchInput;
import com.softeq.output.OutputHandlerSolver;
import com.softeq.service.WebCrawler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class App {
    public static void main(String[] args) {

        Logger logger = LogManager.getLogger(App.class);

        InputHandlerSolver inputHandlerSolver = new InputHandlerSolver();
        List<SearchInput> searchInputList = inputHandlerSolver.handleInput(args);

        if (searchInputList.isEmpty()) {
            logger.warn("Not enough data was provided for web crawling. The app is shutting down.");
            System.exit(0);
        } else {
            OutputHandlerSolver oh = new OutputHandlerSolver();

            for (SearchInput searchInput : searchInputList) {
                WebCrawler webCrawler = new WebCrawler();
                webCrawler.execute(searchInput);
                oh.handleOutput(webCrawler.getSearchData(), searchInput.getSearchTermsList());
            }
        }
    }
}
