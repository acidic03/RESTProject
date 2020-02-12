package com.matthew.restproject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@SpringBootApplication
public class RestProjectApplication {
	// holds all of the CSV records
	public static final Records RECORDS = new Records("real-estate-data.csv");
	public static final GsonBuilder BUILDER = new GsonBuilder();
	public static final Gson GSON = BUILDER.create();

	// Wed May 21 00:00:00 EDT 2008


	public static void main(String[] args) {

//		SimpleDateFormat utcDateFormat = new SimpleDateFormat("EEEE MMM dd HH:mm:ss zzz yyyy");
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy");
//		try {
//			//Date t = simpleDateFormat.parse("05/21/08");
//			Date t2 = utcDateFormat.parse("Mon May 19 00:00:00 EDT 2008");
//			System.out.println(simpleDateFormat.format(t2));
//
//			System.out.println(simpleDateFormat.parse("05/20/08").after(t2));
//		}
//		catch(ParseException e) {
//
//		}

		SpringApplication.run(RestProjectApplication.class, args);
	}



}
