package com.softeq.output.handler;

import com.opencsv.CSVWriter;
import com.softeq.input.handler.AbstractFileHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CSVOutputHandler extends AbstractFileHandler implements OutputHandler{

    Logger logger = LogManager.getLogger(CSVOutputHandler.class);

    public CSVOutputHandler(String fileLink) {
        super(fileLink);
    }

    @Override
    public void printData(ArrayList<String> stringDataList) {
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String filePath = new StringBuilder(this.getFileLink()).append(date).append(".csv").toString();

        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                logger.debug("New file was created successfully.");
            } catch (IOException e) {
                logger.warn("Exception while creating a file: " + e);
            }
        }

        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath, true))){
            for (String s : stringDataList) {
                writer.writeNext(s.split(","));
            }
            writer.flush();
            logger.info("Search data was successfully entered into a file.");
        } catch (IOException e) {
            logger.warn("Exception while writing search data to file: " + e);
        }
    }
}
