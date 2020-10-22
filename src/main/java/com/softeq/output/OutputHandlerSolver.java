package com.softeq.output;

import com.softeq.constant.Constant;
import com.softeq.constant.ConstantConfig;
import com.softeq.output.handler.CSVOutputHandler;
import com.softeq.output.handler.ConsoleOutputHandler;
import com.softeq.service.SearchResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class OutputHandlerSolver {

    final Logger logger = LogManager.getLogger(OutputHandlerSolver.class);

    private static List<OutputFormat> outputFormatList;

    public OutputHandlerSolver() {
        outputFormatList = new ArrayList<>();

        Properties props = ConstantConfig.getInstance().getProperties();

        for (int i = 1; i < props.size(); i++) {
            if (props.containsKey(String.format("output.%d.type", i))) {
                String type = (String) props.get(String.format("output.%d.type", i));
                Integer entriesNumber = null;
                try {
                    entriesNumber = Integer.parseInt(props.getProperty(String.format("output.%d.entries", i)));
                } catch (NumberFormatException e) {
                    logger.warn("Exception when parsing String into entriesNumber: " + e);
                }
                String sort = (String) props.get(String.format("output.%d.sort", i));
                String filepath = (String) props.get(String.format("output.%d.filepath", i));
                outputFormatList.add(new OutputFormat(type, entriesNumber, sort, filepath));
            }
        }
    }

    public void handleOutput(ArrayList<SearchResult> searchData, ArrayList<String> searchTermsList) {

        for (OutputFormat outputFormat : outputFormatList) {

            //deep clone search results list
            ArrayList<SearchResult> clonedSearchData = deepClone(searchData);
            //sort and choose number of entries to print
            ArrayList<String> formattedData = new OutputDataFormatter().formatOutputData(clonedSearchData, searchTermsList, outputFormat);

            //choose correct OutputHandler based on type parameter
            switch (outputFormat.getType()) {
                case "console": {
                    new ConsoleOutputHandler().printData(formattedData);
                    break;
                }
                case "csv": {
                    String filepath = outputFormat.getFilepath();
                    if (filepath == null) {
                        filepath = ConstantConfig.getInstance().getProperty(Constant.FILE_OUTPUT_NAME_NO_EXT);
                    }
                    new CSVOutputHandler(filepath).printData(formattedData);
                    break;
                }
            }
        }
    }

    private ArrayList<SearchResult> deepClone(ArrayList<SearchResult> searchData) {

        ArrayList<SearchResult> clonedList = new ArrayList<>();

        for (SearchResult sr : searchData) {
            SearchResult newSR = new SearchResult(sr.getLink(), sr.getTotalHits(), sr.getHitsByWord());
            clonedList.add(newSR);
        }
        return clonedList;
    }
}
