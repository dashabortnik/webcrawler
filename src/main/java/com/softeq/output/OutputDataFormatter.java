package com.softeq.output;

import com.softeq.constant.Constant;
import com.softeq.constant.ConstantConfig;
import com.softeq.service.SearchResult;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OutputDataFormatter {

    public ArrayList<String> formatOutputData(List<SearchResult> searchData, ArrayList<String> searchTermsList, OutputFormat outputFormat) {

        String sort = outputFormat.getSort();
        int entriesNumber = outputFormat.getEntriesNumber();
        ArrayList<String> stringDataList = new ArrayList<>();

        //add header
        String listString = String.join(",", searchTermsList);
        stringDataList.add("Link," + listString);

        //add data
        if (("asc").equals(sort)) {
            searchData.sort(Comparator.comparing(SearchResult::getTotalHits));
        } else if (("desc").equals(sort)) {
            searchData.sort(Comparator.comparing(SearchResult::getTotalHits).reversed());
        }

        if (entriesNumber < 0) {
            entriesNumber = Integer.parseInt(ConstantConfig.getInstance().getProperty(Constant.NUMBER_OF_TOP_ENTRIES_TO_PRINT));
        } else if (entriesNumber == 0) {
            entriesNumber = searchData.size();
        }
        int maxIndexToPrint = Math.min(searchData.size(), entriesNumber);
        for (int i = 0; i < maxIndexToPrint; i++) {
            stringDataList.add(searchData.get(i).toCSVStringHitsByWord());
        }

        return stringDataList;
    }

}
