package io.github.ellismatthew4.empireeconomy.data;

import io.github.ellismatthew4.empireeconomy.utils.LoggerService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {
    public Map<String, Integer> currency;
    public transient Boolean challengeActive;
    public String emperor;
    public transient String challenger;
    public Integer zoningRate;
    public List<Zone> zones;
    public Map<String, List<WarpPoint>> wps;
    public List<String> punished;
    public Float salesTax;
    public Integer defaultWarpFee;
    public Integer warpCreationFee;
    public LocalDate lastStatUpdate;
    public Map<String, Integer[]> stats;
    public Map<String, Integer> fines;

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
            wps = new HashMap<>();
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
        if (lastStatUpdate == null) {
            this.lastStatUpdate = LocalDate.now().minus(1, ChronoUnit.DAYS);
        }
        if (stats == null) {
            stats = new HashMap<>();
        }
        if (fines == null) {
            fines = new HashMap<>();
        }
    }

    public int privateWarpCreationFee() {
        return warpCreationFee * 2;
    }

    public void clearStats() {
        if (!LocalDate.now().isEqual(this.lastStatUpdate)) {
            LoggerService.getInstance().info("Stats have expired. Clearing...");
            this.lastStatUpdate = LocalDate.now();
            this.stats = new HashMap<>();
        }
    }
}
