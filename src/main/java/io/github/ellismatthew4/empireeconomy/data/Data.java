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
    public Float salesTax;

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
        if (salesTax == null) {
            salesTax = (float) 0.0;
        }
    }
}
