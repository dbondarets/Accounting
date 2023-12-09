package repository;

import model.YearlyReport;

public class YearlyReportRepository {
    private YearlyReport report;

    public YearlyReportRepository() {
    }

    public void setReport(YearlyReport report) {
        this.report = report;
    }

    public YearlyReport getReport() {
        return report;
    }

    public boolean isEmpty() {
        return report == null;
    }
}
