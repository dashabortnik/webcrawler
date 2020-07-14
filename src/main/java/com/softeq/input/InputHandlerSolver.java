package com.softeq.input;

import com.softeq.input.handler.ConsoleInputHandler;
import com.softeq.input.handler.JsonInputHandler;

import java.util.List;

/**
 * Class for redirecting input to a suitable handler.
 */

public class InputHandlerSolver {

    /**
     * HandleInput method checks if any arguments were provided at the start of the app and calls a suitable handler.
     * @return a list of objects with search parameters
     * @see SearchInput
     */
    public List<SearchInput> handleInput(String ... args){
        if(args.length == 0){
            //console input as no args were provided on start
            ConsoleInputHandler uih = new ConsoleInputHandler();
            return uih.getCrawlingParameters();
        } else {
            //json file link was provided on start
            JsonInputHandler jih = new JsonInputHandler(args[0]);
            return jih.getCrawlingParameters();
        }
    }
}
