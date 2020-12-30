package com.example.myapplication.Models;

public class BarGraphCountryModel {
    Float casesBar, deathsBar;
    String countryNameBar;

    public BarGraphCountryModel() {
    }

    public BarGraphCountryModel(Float casesBar, Float deathsBar, String countryNameBar) {
        this.casesBar = casesBar;
        this.deathsBar = deathsBar;
        this.countryNameBar = countryNameBar;
    }

    public Float getCasesBar() {
        return casesBar;
    }

    public void setCasesBar(Float casesBar) {
        this.casesBar = casesBar;
    }

    public Float getDeathsBar() {
        return deathsBar;
    }

    public void setDeathsBar(Float deathsBar) {
        this.deathsBar = deathsBar;
    }

    public String getCountryNameBar() {
        return countryNameBar;
    }

    public void setCountryNameBar(String countryNameBar) {
        this.countryNameBar = countryNameBar;
    }
}
