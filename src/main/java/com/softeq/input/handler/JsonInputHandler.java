package com.softeq.input.handler;

import com.softeq.input.ParametersResolver;
import com.softeq.input.SearchInput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JsonInputHandler extends AbstractFileHandler implements InputHandler {

    final Logger logger = LogManager.getLogger(JsonInputHandler.class);

    public JsonInputHandler(String fileLink) {
        super(fileLink);
    }

    @Override
    public SearchInput getCrawlingParameters() {

        SearchInput searchInput = null;
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(this.getFileLink()))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;
            searchInput = parseInputParameters(jsonObject);

        } catch (IOException | ParseException e) {
            logger.warn("Exception while reading and parsing file: " + e);
        }
        return searchInput;
    }

    private SearchInput parseInputParameters(JSONObject input){

        ParametersResolver parametersResolver = new ParametersResolver();

        String seed = (String) input.get("seed");
        logger.debug("1. Seed - " + seed);

        int linkDepth = (int)(long) input.get("linkDepth");
        logger.debug("2. Link depth - " + linkDepth);

        int maxPagesLimit = (int)(long) input.get("maxPagesLimit");
        logger.debug("3. Max pages limit - " + maxPagesLimit);

        String searchTermsString = (String) input.get("searchTermsString");
        logger.debug("4. Search terms string - " + searchTermsString);

        if(parametersResolver.isInvalidUrl(seed)){
            logger.warn("Parsing of input parameters failed: invalid seed. The program is shutting down.");
            System.exit(0);
        }

        return parametersResolver.resolveParams(seed, searchTermsString, linkDepth, maxPagesLimit);
    }
}
