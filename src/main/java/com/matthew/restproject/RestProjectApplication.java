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

	public static Records records;

	public static void main(String[] args) {
		// holds all of the CSV records
		records = new Records("real-estate-data.csv");
		records.get("street", "645 MORRISON AVE");
		
		//SpringApplication.run(RestProjectApplication.class, args);
	}



}
