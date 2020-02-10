package com.matthew.restproject;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Records {

    private Iterable<CSVRecord> records;
    private boolean csvLoaded;

    public Records(String filename) {
        csvLoaded = loadCSV(filename);
    }

    private boolean loadCSV(String filename) {
        try {
            Reader in = new FileReader(filename);
            records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);

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

}
