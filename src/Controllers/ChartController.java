package Controllers;

import Models.ChartCalculator;
import View.Chart;
import org.jfree.chart.JFreeChart;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChartController {
    private ChartCalculator chartCalculator;

    private final Chart hoursChart;
    private final Chart daysChart;
    private final Chart weeksChart;
    private final Chart monthsChart;
    private final Chart yearsChart;

    public ChartController() {
        this.hoursChart = new Chart("Hours Chart","Impressions", "Hours");
        this.daysChart = new Chart("Days Chart", "Impressions", "Days");
        this.weeksChart = new Chart("Weeks Chart", "Impressions", "Weeks");
        this.monthsChart = new Chart("Months Chart", "Impressions", "Months");
        this.yearsChart = new Chart("Years Chart",  "Impressions", "Years");
    }

    public void createCharts(ChartCalculator chartCalculator) {
        chartCalculator.calculateCharts("Hours");
        this.hoursChart.updateChart(chartCalculator);

        chartCalculator.calculateCharts("Days");
        this.daysChart.updateChart(chartCalculator);

        chartCalculator.calculateCharts("Weeks");
        this.weeksChart.updateChart(chartCalculator);

        chartCalculator.calculateCharts("Months");
        this.monthsChart.updateChart(chartCalculator);

        chartCalculator.calculateCharts("Years");
        this.yearsChart.updateChart(chartCalculator);

        this.chartCalculator = chartCalculator;
    }

    // filters the charts
    public void updateCharts(String metric, String gender, String age, String context, String income, String stringStartDate, String stringEndDate) {
        if (stringStartDate.equals("Any") || stringEndDate.equals("Any")) {
            chartCalculator.calculateCharts("Hours", gender, age, context, income);
            this.hoursChart.updateChart(chartCalculator, metric);

            chartCalculator.calculateCharts("Days", gender, age, context, income);
            this.daysChart.updateChart(chartCalculator, metric);

            chartCalculator.calculateCharts("Weeks", gender, age, context, income);
            this.weeksChart.updateChart(chartCalculator, metric);

            chartCalculator.calculateCharts("Months", gender, age, context, income);
            this.monthsChart.updateChart(chartCalculator, metric);

            chartCalculator.calculateCharts("Years", gender, age, context, income);
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime startDate = LocalDateTime.parse(stringStartDate, formatter);
            LocalDateTime endDate = LocalDateTime.parse(stringEndDate, formatter);

            chartCalculator.calculateCharts("Hours", gender, age, context, income, startDate, endDate);
            this.hoursChart.updateChart(chartCalculator, metric);

            chartCalculator.calculateCharts("Days", gender, age, context, income, startDate, endDate);
            this.daysChart.updateChart(chartCalculator, metric);

            chartCalculator.calculateCharts("Weeks", gender, age, context, income, startDate, endDate);
            this.weeksChart.updateChart(chartCalculator, metric);

            chartCalculator.calculateCharts("Months", gender, age, context, income, startDate, endDate);
            this.monthsChart.updateChart(chartCalculator, metric);

            chartCalculator.calculateCharts("Years", gender, age, context, income, startDate, endDate);
        }
        this.yearsChart.updateChart(chartCalculator, metric);
    }

    public JFreeChart getHoursChart() {
        return hoursChart.getChart();
    }

    public JFreeChart getDaysChart() {
        return daysChart.getChart();
    }

    public JFreeChart getWeeksChart() {
        return weeksChart.getChart();
    }

    public JFreeChart getMonthsChart() {
        return monthsChart.getChart();
    }

    public JFreeChart getYearsChart() {
        return yearsChart.getChart();
    }
}
