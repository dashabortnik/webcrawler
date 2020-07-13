package com.softeq.output;

import com.opencsv.CSVWriter;
import com.softeq.constant.Constant;
import com.softeq.constant.ConstantConfig;
import com.softeq.service.SearchResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

/**
 * Class for handling of search results after web crawling.
 */
public class OutputHandler {

    final Logger logger = LogManager.getLogger(OutputHandler.class);

    /**
     * PrintAllData method handles all unsorted web crawling data and prints it to console and to CSV file.
     * @param searchData is a list of SearchResult objects.
     * @see SearchResult
     */
    public void printAllData(List<SearchResult> searchData) {
        try (CSVWriter writer = new CSVWriter(new FileWriter("C:\\Users\\solei\\Documents\\output.csv"))){
            for (SearchResult sr : searchData) {
                String stringFromSr = sr.toCSVStringHitsByWord();
                writer.writeNext(stringFromSr.split(","));
                logger.debug("CSV string hits by word: " + stringFromSr);
            }
            writer.flush();
            logger.info("All search data was successfully entered into a file.");
        } catch (IOException e) {
            logger.warn("Exception while writing all data to file: " + e);
        }
    }

    /**
     * PrintTopDataToFile method sorts web crawling data by total hits and prints a number of top entries
     * specified by properties to CSV file.
     * @param searchData is a list of SearchResult objects.
     * @see SearchResult
     */
    public void printTopDataToFile(List<SearchResult> searchData){

        searchData.sort(Comparator.comparing(SearchResult::getTotalHits).reversed());
        try (CSVWriter writer = new CSVWriter(new FileWriter("C:\\Users\\solei\\Documents\\topHitsOutput.csv"))){
            int listSize = searchData.size();
            int numberOfEntriesToPrint = Integer.parseInt(ConstantConfig.getInstance().getProperty(Constant.NUMBER_OF_TOP_ENTRIES_TO_PRINT));
            logger.debug("Number of entries to print: " + numberOfEntriesToPrint);
            int maxIndexToPrint = Math.min(listSize, numberOfEntriesToPrint);
            for (int i = 0; i<maxIndexToPrint; i++){
                writer.writeNext(searchData.get(i).toCSVStringHitsByWord().split(","));
            }
            writer.flush();
            logger.info("Search data by top hits was successfully entered into a file.");
        } catch (IOException e) {
            logger.warn("Exception while writing top data to a file: " + e);
        }
    }
}
