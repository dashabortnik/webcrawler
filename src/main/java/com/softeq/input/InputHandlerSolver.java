package com.softeq.input;

import com.softeq.input.handler.ConsoleInputHandler;
import com.softeq.input.handler.JsonInputHandler;

public class InputHandlerSolver {

    public SearchInput handleInput(String ... args){
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
