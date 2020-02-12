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
    private boolean csvLoaded;
    private int numberOfRecords;

    public Records(String filename) {
        csvLoaded = loadCSV(filename);
    }

    private boolean loadCSV(String filename) {
        try {
            Reader in = new FileReader(filename);
            CSVParser parser = new CSVParser(in, CSVFormat.RFC4180.withFirstRecordAsHeader());
            records = parser.getRecords();

            return true;
        } catch (FileNotFoundException e) {
            // csv file not found
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public CSVRecord get(String header, String value) {
        if (csvLoaded) {
            for (CSVRecord rec : records) {

                if (rec.get(header).equalsIgnoreCase(value)) {
                    System.out.println("found " + value);
                    return rec;
                }
            }
        }

        return null;
    }

    public List<CSVRecord> getRecords() { return records; }

}
