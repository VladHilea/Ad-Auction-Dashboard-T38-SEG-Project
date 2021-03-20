package Controllers;

import Models.ChartCalculator;
import View.Chart;
import org.jfree.chart.JFreeChart;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChartController {
    private ChartCalculator hoursChartCalculator;
    private ChartCalculator daysChartCalculator;
    private ChartCalculator weeksChartCalculator;
    private ChartCalculator monthsChartCalculator;
    private ChartCalculator yearsChartCalculator;

    private final Chart hoursChart;
    private final Chart daysChart;
    private final Chart weeksChart;
    private final Chart monthsChart;
    private final Chart yearsChart;

    public ChartController() {
        this.hoursChart = new Chart("Impressions vs Time (Hours)", "Impressions vs Time (Hours)", "Impressions");
        this.daysChart = new Chart("Impressions vs Time (Days)", "Impressions vs Time (Days)", "Impressions");
        this.weeksChart = new Chart("Impressions vs Time (Weeks)", "Impressions vs Time (Weeks)", "Impressions");
        this.monthsChart = new Chart("Impressions vs Time (Months)", "Impressions vs Time (Months)", "Impressions");
        this.yearsChart = new Chart("Impressions vs Time (Years)", "Impressions vs Time (Years)", "Impressions");
    }

    public void createCharts(ChartCalculator chartCalculator) {
        chartCalculator.calculateCharts("Hours");
        this.hoursChartCalculator = chartCalculator;
        this.hoursChart.updateChart(chartCalculator);

        chartCalculator.calculateCharts("Days");
        this.daysChartCalculator = chartCalculator;
        this.daysChart.updateChart(chartCalculator);

        chartCalculator.calculateCharts("Weeks");
        this.weeksChartCalculator = chartCalculator;
        this.weeksChart.updateChart(chartCalculator);

        chartCalculator.calculateCharts("Months");
        this.monthsChartCalculator = chartCalculator;
        this.monthsChart.updateChart(chartCalculator);

        chartCalculator.calculateCharts("Years");
        this.yearsChartCalculator = chartCalculator;
        this.yearsChart.updateChart(chartCalculator);

    }

    // filters the charts
    public void updateCharts(String startDate, String endDate) {
        if (startDate.equals("Any") || endDate.equals("Any")) {
            hoursChartCalculator.calculateCharts("Hours");
            daysChartCalculator.calculateCharts("Days");
            weeksChartCalculator.calculateCharts("Weeks");
            monthsChartCalculator.calculateCharts("Months");
            yearsChartCalculator.calculateCharts("Years");
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            hoursChartCalculator.calculateCharts("Hours", LocalDateTime.parse(startDate, formatter), LocalDateTime.parse(endDate, formatter));
            daysChartCalculator.calculateCharts("Days", LocalDateTime.parse(startDate, formatter), LocalDateTime.parse(endDate, formatter));
            weeksChartCalculator.calculateCharts("Weeks", LocalDateTime.parse(startDate, formatter), LocalDateTime.parse(endDate, formatter));
            monthsChartCalculator.calculateCharts("Months", LocalDateTime.parse(startDate, formatter), LocalDateTime.parse(endDate, formatter));
            yearsChartCalculator.calculateCharts("Years", LocalDateTime.parse(startDate, formatter), LocalDateTime.parse(endDate, formatter));
        }
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
