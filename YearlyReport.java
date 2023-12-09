package model;

import java.util.ArrayList;
import java.util.List;

public class YearlyReport {
    private int year;
    private List<Statistic> statistics;


    public YearlyReport(int year) {
        this.year = year;
        this.statistics = new ArrayList<>();
    }


    public void add(Statistic statistic) {
        statistics.add(statistic);
    }

    public List<Statistic> getStatistics() {
        return List.copyOf(statistics);
    }

    public int getYear() {
        return year;
    }
}
