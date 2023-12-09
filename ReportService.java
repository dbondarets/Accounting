package service;

import model.*;
import repository.MonthlyReportRepository;
import repository.YearlyReportRepository;
import util.FileReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ReportService {
    private final static String RESOURCE_PATH = "././resources/";
    private final static String FILE_EXTENSION = ".csv";
    private final static String MONTHLY_REPORT_NAME_TEMPLATE = "m.20210";
    private final static String YEARLY_REPORT_NAME_TEMPLATE = "y.2021";
    private final static String DELIMITER = ",";
    public final static String LINE_BREAK = "\n";
    private final static String TABULATION = "\t";


    private final MonthlyReportRepository monthlyReportRepository;
    private final YearlyReportRepository yearlyReportRepository;

    public ReportService() {
        this.monthlyReportRepository = new MonthlyReportRepository();
        this.yearlyReportRepository = new YearlyReportRepository();
    }

    public String readMonthlyReport() {
        try {
            for (int i = 1; i <= 3; i++) {
                MonthlyReport monthlyReport = new MonthlyReport(Month.convertToMonth(i));
                List<String> data = FileReader.readFileContents(RESOURCE_PATH + MONTHLY_REPORT_NAME_TEMPLATE + i + FILE_EXTENSION);
                data = data.subList(1, data.size());
                for (String line : data) {
                    String[] values = line.split(DELIMITER);
                    monthlyReport.add(new Transaction(values[0], Boolean.parseBoolean(values[1]), Integer.parseInt(values[2]), Integer.parseInt(values[3])));
                }
                monthlyReportRepository.add(monthlyReport);
            }
        } catch (NumberFormatException e) {
            return "Некорректные данные в таблице";
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        return "Месячные отчёты успешно считаны!";
    }

    public String processMonthlyReports() {
        if (monthlyReportRepository.isEmpty()) {
            return "Необходимо вначале считать месячные отчёты!";
        }
        List<MonthlyReport> reports = monthlyReportRepository.getReports();
        StringBuilder answer = new StringBuilder();
        for (MonthlyReport report : reports) {
            answer.append(processMonthlyReport(report));
        }
        return answer.toString();
    }

    private String processMonthlyReport(MonthlyReport report) {
        String profitItemName = "";
        int profit = 0;
        String lossItemName = "";
        int loss = 0;
        for (Transaction transaction : report.getTransactions()) {
            int totalAmount = transaction.getQuantity() * transaction.getUnitPrice();
            if (transaction.isExpense()) {
                totalAmount *= -1;
            }
            if (!transaction.isExpense() && totalAmount > profit) {
                profitItemName = transaction.getItemName();
                profit = totalAmount;
            } else if (transaction.isExpense() && totalAmount < loss) {
                lossItemName = transaction.getItemName();
                loss = totalAmount;
            }
        }
        return "Название месяца : " + report.getMonth().translate() + LINE_BREAK +
                "Самый прибыльный товар : " + profitItemName + ", прибыль : " + profit + LINE_BREAK +
                "Самая большая трата : " + lossItemName + ", убыток : " + loss + LINE_BREAK;
    }

    public String readYearReport() {
        try {
            YearlyReport yearlyReport = new YearlyReport(2021);
            List<String> data = FileReader.readFileContents(RESOURCE_PATH + YEARLY_REPORT_NAME_TEMPLATE + FILE_EXTENSION);
            data = data.subList(1, data.size());
            for (String line : data) {
                String[] values = line.split(DELIMITER);
                yearlyReport.add(new Statistic(Month.convertToMonth(Integer.parseInt(values[0])), Integer.parseInt(values[1]), Boolean.parseBoolean(values[2])));
            }
            yearlyReportRepository.setReport(yearlyReport);
        } catch (NumberFormatException e) {
            return "Некорректные данные в таблице";
        }
        return "Годовой отчёт успешно считан!";
    }

    public String processYearlyReports() {
        if (monthlyReportRepository.isEmpty()) {
            return "Необходимо вначале считать годовые отчёты!";
        }
        YearlyReport report = yearlyReportRepository.getReport();
        return processYearlyReport(report);
    }

    private String processYearlyReport(YearlyReport report) {
        int year = report.getYear();
        double averageProfit = 0;
        double averageLoss = 0;
        Map<Month, Integer> monthlyProfitMap = new HashMap<>();
        for (Statistic statistic : report.getStatistics()) {
            Month currentMonth = statistic.getMonth();
            int amount = statistic.getAmount();
            if (statistic.isExpense()) {
                amount *= -1;
                averageLoss += statistic.getAmount();
            } else {
                averageProfit += amount;
            }
            if (!monthlyProfitMap.containsKey(currentMonth)) {
                monthlyProfitMap.put(currentMonth, 0);
            }
            monthlyProfitMap.put(currentMonth, monthlyProfitMap.get(currentMonth) + amount);
        }

        StringBuilder monthlyReport = new StringBuilder();
        for (Map.Entry<Month, Integer> entry : monthlyProfitMap.entrySet()) {
            monthlyReport.append(TABULATION)
                    .append("Прибыль за месяц ")
                    .append(entry.getKey().translate())
                    .append(" : ")
                    .append(entry.getValue())
                    .append(LINE_BREAK);
        }

        return "Рассматриваемый год: " + year + LINE_BREAK +
                "Прибыль по месяцам: " + LINE_BREAK +
                monthlyReport +
                "Средний расход : " + averageLoss / monthlyProfitMap.size() + LINE_BREAK +
                "Средний доход : " + averageProfit / monthlyProfitMap.size();
    }

    public String checkReport() {
        if (yearlyReportRepository.isEmpty() || monthlyReportRepository.isEmpty()) {
            return "Необходимо вначале считать отчёты!";
        }
        List<MonthlyReport> monthlyReports = monthlyReportRepository.getReports();
        Map<Month, Integer> monthlyProfitMap = new HashMap<>();
        for (MonthlyReport monthlyReport : monthlyReports) {
            Month currentMonth = monthlyReport.getMonth();
            int monthlyProfit = 0;
            for (Transaction transaction : monthlyReport.getTransactions()) {
                int amount = transaction.getQuantity() * transaction.getUnitPrice();
                if (transaction.isExpense()) {
                    amount *= -1;
                }
                monthlyProfit += amount;
            }
            monthlyProfitMap.put(currentMonth, monthlyProfit);
        }

        YearlyReport yearlyReport = yearlyReportRepository.getReport();
        Map<Month, Integer> yearlyProfitMap = new HashMap<>();
        for (Statistic statistic : yearlyReport.getStatistics()) {
            Month currentMonth = statistic.getMonth();
            int amount = statistic.getAmount();
            if (statistic.isExpense()) {
                amount *= -1;
            }
            if (!yearlyProfitMap.containsKey(currentMonth)) {
                yearlyProfitMap.put(currentMonth, 0);
            }
            yearlyProfitMap.put(currentMonth, yearlyProfitMap.get(currentMonth) + amount);
        }

        if (monthlyProfitMap.size() != yearlyProfitMap.size()) {
            return "Ошибка при сверке отчётов";
        }

        for (Month month : monthlyProfitMap.keySet()) {
            if (!Objects.equals(monthlyProfitMap.get(month), yearlyProfitMap.get(month))) {
                return "Ошибка при сверке отчётов";
            }
        }
        return "Ошибок при сверке отчётов не обнаружено!";
    }
}
