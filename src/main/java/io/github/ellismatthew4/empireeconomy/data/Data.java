package io.github.ellismatthew4.empireeconomy.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {
    public Map<String, Integer> currency;
    public Boolean challengeActive;
    public String emperor;
    public String challenger;
    public Integer zoningRate;
    public List<Zone> zones;
    public List<WarpPoint> wps;
    public List<String> punished;
    public Float salesTax;
    public Integer defaultWarpFee;
    public Integer warpCreationFee;

    public void init() {
        if (currency == null) {
            currency = new HashMap<>();
        }
        if (challengeActive == null) {
            challengeActive = false;
        }
        if (zones == null) {
            zones = new ArrayList<>();
        }
        if (wps == null) {
            wps = new ArrayList<>();
        }
        if (salesTax == null) {
            salesTax = (float) 0.0;
        }
        if (zoningRate == null) {
            zoningRate = 0;
        }
        if (defaultWarpFee == null) {
            defaultWarpFee = 10;
        }
        if (warpCreationFee == null) {
            warpCreationFee = 100;
        }
        if (punished == null) {
            punished = new ArrayList<>();
        }
    }

    public int privateWarpCreationFee() {
        return warpCreationFee * 2;
    }
}
