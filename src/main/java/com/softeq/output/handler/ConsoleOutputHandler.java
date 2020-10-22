package com.softeq.output.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class ConsoleOutputHandler implements OutputHandler {

    Logger logger = LogManager.getLogger(ConsoleOutputHandler.class);

    @Override
    public void printData(ArrayList<String> stringDataList) {
        for (String s : stringDataList) {
            System.out.println(s);
        }
        logger.info("Requested data was printed to console successfully.");
    }
}
