package com.softeq;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JsonInputHandler {

    void getCrawlingParameters(String link) {

        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(link))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;
            parseInputParameters(jsonObject);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static void parseInputParameters(JSONObject input)
    {

        String seed = (String) input.get("seed");
        System.out.println("1 - " + seed);

        String linkDepth = (String) input.get("linkDepth");
        System.out.println("2 - " + linkDepth);

        String maxPagesLimit = (String) input.get("maxPagesLimit");
        System.out.println("3 - " + maxPagesLimit);

        String searchTermsString = (String) input.get("searchTermsString");
        System.out.println("4 - " + searchTermsString);
    }

}
