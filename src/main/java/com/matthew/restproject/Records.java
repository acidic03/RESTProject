package com.matthew.restproject;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class Records {

    private List<CSVRecord> records;

    public Records(String filename) {
        loadCSV(filename);
    }

    private boolean loadCSV(String filename) {
        try {
            Reader in = new FileReader(filename);
            CSVParser parser = new CSVParser(in, CSVFormat.RFC4180.withFirstRecordAsHeader());
            records = parser.getRecords();

            return true;
        } catch (IOException e) {
            // csv file not found
            // I might consider just having this method continue throwing. The server is not going to work well without
            // being able to load the file
            e.printStackTrace();
        }
        return false;
    }

    public List<CSVRecord> getRecords() { return records; }
}
