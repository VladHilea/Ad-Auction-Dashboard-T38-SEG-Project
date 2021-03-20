package Controllers;

import Models.ChartCalculator;
import View.Chart;
import org.jfree.chart.JFreeChart;

public class ChartController {
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
        this.hoursChart.updateChart(chartCalculator);

        chartCalculator.calculateCharts("Days");
        this.daysChart.updateChart(chartCalculator);

        chartCalculator.calculateCharts("Weeks");
        this.weeksChart.updateChart(chartCalculator);

        chartCalculator.calculateCharts("Months");
        this.monthsChart.updateChart(chartCalculator);

        chartCalculator.calculateCharts("Years");
        this.yearsChart.updateChart(chartCalculator);
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
