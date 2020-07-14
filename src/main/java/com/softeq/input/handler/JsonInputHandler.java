package com.softeq.input.handler;

import com.softeq.input.ParametersResolver;
import com.softeq.input.SearchInput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handler class for user input from json file. Accepts multiple search queries.
 */

public class JsonInputHandler extends AbstractFileHandler implements InputHandler {

    final Logger logger = LogManager.getLogger(JsonInputHandler.class);

    public JsonInputHandler(String fileLink) {
        super(fileLink);
    }

    /**
     * GetCrawlingParameters method processes a file by given link into a list of search queries.
     * @return list of SearchInput objects
     * @see SearchInput
     */
    @Override
    public List<SearchInput> getCrawlingParameters() {

        ArrayList <SearchInput> searchInput = new ArrayList<>();
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(this.getFileLink()))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray searchDataArray = (JSONArray) obj;

            for (Object o : searchDataArray) {
                SearchInput searchInputTemp = parseInputParameters((JSONObject) o);
                if(searchInputTemp!=null){
                    searchInput.add(searchInputTemp);
                }
            }
        } catch (IOException | ParseException e) {
            logger.warn("Exception while reading and parsing file: " + e);
        }
        return searchInput;
    }

    /**
     * ParseInputParameters method processes every Json object into a SearchInput object.
     * @return SearchInput object which represents an individual search query
     * @see SearchInput
     */
    private SearchInput parseInputParameters(JSONObject input){

        ParametersResolver parametersResolver = new ParametersResolver();

        String seed = (String) input.get("seed");
        logger.debug("1. Seed - " + seed);

        int linkDepth;
        linkDepth = handleInt("linkDepth", input);
        logger.debug("2. Link depth - " + linkDepth);

        int maxPagesLimit;
        maxPagesLimit = handleInt("maxPagesLimit", input);
        logger.debug("3. Max pages limit - " + maxPagesLimit);

        String searchTermsString = (String) input.get("searchTermsString");
        logger.debug("4. Search terms string - " + searchTermsString);

        if(parametersResolver.isInvalidUrl(seed) || parametersResolver.isNullOrEmptyString(searchTermsString)){
            logger.warn("Parsing of input parameters failed: invalid seed or search terms.");
            return null;
        }

        return parametersResolver.resolveParams(seed, searchTermsString, linkDepth, maxPagesLimit);
    }

    private int handleInt(String intString, JSONObject input){
        int result = 0;
        try{
            Object obj = input.get(intString);
            if(obj!=null){
                result = (int)(long)obj;
            }
        } catch (NumberFormatException e){
            logger.warn("Given number is not integer: " + e);
        }
        return result;
    }
}
