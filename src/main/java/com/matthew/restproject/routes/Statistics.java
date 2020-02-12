package com.matthew.restproject.routes;

public class Statistics {

    public double value;
    public String error;

    public Statistics(double value) {
        this.value = (value);
        this.error = "";
    }

    public Statistics(double value, String error) {
        this.value = (value);
        this.error = error;
    }
}
