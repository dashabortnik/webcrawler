package com.softeq;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import static org.junit.Assert.assertTrue;


public class AppTest{

    private String fileName1;
    private String fileName2;

    @Before
    public void init() throws IOException {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("test.properties");
        Properties props = new Properties();
        props.load(stream);
        fileName1 = props.getProperty("filepath1");
        fileName2 = props.getProperty("filepath2");
    }

    @Test
    public void integrationTest(){
        App app = new App();
        app.main(new String[]{"src/test/resources/inputDataArray.txt"});

        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String pathToFile1 = new StringBuilder(fileName1).append(date).append(".csv").toString();
        String pathToFile2 = new StringBuilder(fileName2).append(date).append(".csv").toString();
        File file1 = new File(pathToFile1);
        File file2 = new File(pathToFile2);
        assertTrue(file1.exists());
        assertTrue(file2.exists());
        assertTrue(file1.length()!=0);
        assertTrue(file2.length()!=0);
    }
}
