package com.matthew.restproject.routes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.matthew.restproject.RestProjectApplication;
import org.apache.commons.csv.CSVRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RestController
public class StatisticsController {

    private final String UTC_PATTERN = "EEE MMM dd HH:mm:ss zzz yyyy";
    private final String DATE_PATTERN = "M/d/y";

    private DateFormat dateFormat = new SimpleDateFormat(UTC_PATTERN, Locale.US);
    private DateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN, Locale.US);
    private GsonBuilder builder = new GsonBuilder();
    private Gson gson = builder.create();

    // a helper class that is used to return errors in JSON
    private class ErrorMsg {
        public String error;

        public ErrorMsg(String error) { this.error = error; }
    }

    @GetMapping("/housing-stats")
    public ResponseEntity<?> housingStatistics(@RequestParam(value = "statistic", required = true) String statistic, @RequestParam(value = "field") String field,
                                        @RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate", required = false) String endDate,
                                        @RequestParam(value = "zipCode", required = false) String zipCode) {
        // find the record matching the parameters provided
        // This is a point where it might be nice to have these records attached to a dependency-injected component. It
        // could help decouple your endpoint from global state.
        List<CSVRecord> rec = RestProjectApplication.RECORDS.getRecords();

        double sum = 0;
        double numberOfRecordsSaw = 0;
        double min = 0;
        double max = 0;
        boolean isFirstRun = true;


        // change to the correct header name in csv file
        if (field.equalsIgnoreCase("squareFootage")) field = "sq__ft";

        // convert the dates to be used for comparison
        Date tempStartDate = getDateFromString(startDate);
        Date tempEndDate = getDateFromString(endDate);

        if (startDate != null && tempStartDate == null) return new ResponseEntity<>(gson.toJson(new ErrorMsg("invalid start date: unknown format")), HttpStatus.NOT_FOUND);
        if (endDate != null && tempStartDate == null) return new ResponseEntity<>(gson.toJson(new ErrorMsg("invalid end date: unknown format")), HttpStatus.NOT_FOUND);

        for (CSVRecord r : rec) {
            if (field.equalsIgnoreCase("price") || field.equalsIgnoreCase("sq__ft")) {

                // checks to see if a zipCode was provided and if so, checks
                // to see if current record is a match
                // This is a clever approach to filtering (continue). As a purely style thing, I might do the filtering as a separate
                // step outside this main method and then do the statistics part as a separate method somewhere (for readability)
                if (zipCode != null) {
                    if (!r.get("zip").equalsIgnoreCase(zipCode))
                        continue;
                }

                if (tempStartDate != null) {
                    try {
                        // checks to see if date is equal or after the date in the record
                        // when they are not equal or after, ignore and continue to next record
                        if ((tempStartDate.compareTo(dateFormat.parse(r.get("sale_date"))) != 0) && !tempStartDate.before(dateFormat.parse(r.get("sale_date"))))
                            continue;
                    } catch (ParseException e) {}
                }

                if (endDate != null) {
                    try {
                        // checks to see if date is equal or before the date in the record
                        // when they are not equal or after, ignore and continue to next record
                        if ((tempEndDate.compareTo(dateFormat.parse(r.get("sale_date"))) != 0) && !tempEndDate.after(dateFormat.parse(r.get("sale_date"))))
                            continue;
                    } catch (ParseException e) {}
                }

                double tempValue = Double.parseDouble(r.get(field));

                if (isFirstRun) {
                    min = tempValue;
                    max = tempValue;
                    isFirstRun = false;
                }

                if (tempValue < min) min = tempValue;
                if (tempValue > max) max = tempValue;

                sum += tempValue;
                numberOfRecordsSaw++;
            }

        }

        Statistics requestedStat = null;

        switch (statistic) {
            case "min":
                requestedStat = new Statistics(min);
                break;
            case "max":
                requestedStat = new Statistics(max);
                break;
            case "average":
                double avg = (numberOfRecordsSaw == 0) ? 0 : (double)sum/(double)numberOfRecordsSaw;

                requestedStat = new Statistics(avg);
                break;
            case "sum":
                requestedStat = new Statistics(sum);
                break;
            case "range":
                requestedStat = new Statistics(max-min);
                break;
            default:
                return new ResponseEntity<>(gson.toJson(new ErrorMsg("invalid statistic: standardDeviation")), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(gson.toJson(requestedStat), HttpStatus.OK);
    }

    private Date getDateFromString(String dateStr) {
        if (dateStr == null) return null;
        Date date = null;

        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            System.out.println(e);
        }

        return date;
    }
}
