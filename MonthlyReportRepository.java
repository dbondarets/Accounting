package repository;

import model.MonthlyReport;

import java.util.ArrayList;
import java.util.List;

public class MonthlyReportRepository {
    private List<MonthlyReport> reports = new ArrayList<>();

    public void add(MonthlyReport report) {
        reports.add(report);
    }

    public List<MonthlyReport> getReports() {
        return List.copyOf(reports);
    }

    public boolean isEmpty() {
        return reports.isEmpty();
    }
}
