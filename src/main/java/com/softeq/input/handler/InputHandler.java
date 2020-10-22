package com.softeq.input.handler;

import com.softeq.input.SearchInput;

import java.util.List;

/**
 * Interface for different types of handlers where every type gets input data from different source.
 */
public interface InputHandler {

    /**
     * GetCrawlingParameters gets search parameters from available source.
     *
     * @return a list where every item represents an individual search query
     */
    List<SearchInput> getCrawlingParameters();
}
