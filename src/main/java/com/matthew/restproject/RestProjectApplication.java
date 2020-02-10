package com.matthew.restproject;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

@SpringBootApplication
public class RestProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestProjectApplication.class, args);
		loadCSV("real-estate-data.csv");

	}

	private static void loadCSV(String filename) {
		try {
			Reader in = new FileReader(filename);
			Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in);

			// print street column form csv
			for (CSVRecord r : records) {
				System.out.println(r.get(0));
			}

		} catch (FileNotFoundException e) {
			// csv file not found
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
