package Controllers;

import Models.ChartCalculator;
import View.Chart;
import View.Histogram;
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

    private final Histogram histogram;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // all charts are given a default display
    public ChartController(int pageLimit, int bounceTime) {
        this.hoursChart = new Chart("Hours Chart","Impressions", "Hours", pageLimit, bounceTime);
        this.daysChart = new Chart("Days Chart", "Impressions", "Days", pageLimit, bounceTime);
        this.weeksChart = new Chart("Weeks Chart", "Impressions", "Weeks", pageLimit, bounceTime);
        this.monthsChart = new Chart("Months Chart", "Impressions", "Months", pageLimit, bounceTime);
        this.yearsChart = new Chart("Years Chart",  "Impressions", "Years", pageLimit, bounceTime);

        this.histogram = new Histogram("Histogram", pageLimit, bounceTime);
    }

    // initial charts display data with no filtering and the entire time range
    public void createCharts(ChartCalculator chartCalculator) {
        // initial calculation of data intervals (for each chart point)
        chartCalculator.calculateIntervals(null, null);

        chartCalculator.calculateFilters("hours", "Any" ,"Any", "Any", "Any", null, null);
        this.hoursChart.updateChart(chartCalculator);

        chartCalculator.calculateFilters("days", "Any" ,"Any", "Any", "Any", null, null);
        this.daysChart.updateChart(chartCalculator);

        chartCalculator.calculateFilters("weeks", "Any" ,"Any", "Any", "Any", null, null);
        this.weeksChart.updateChart(chartCalculator);

        chartCalculator.calculateFilters("months", "Any" ,"Any", "Any", "Any", null, null);
        this.monthsChart.updateChart(chartCalculator);

        chartCalculator.calculateFilters("years", "Any" ,"Any", "Any", "Any", null, null);
        this.yearsChart.updateChart(chartCalculator);

        this.chartCalculator = chartCalculator;

        /*
        this method is a little weird due to the nature of the chart calculator class
        1. the chart calculator stores the data points for only the last granularity filtered
        2. an alternate method would be to store a list of each metric for each granularity so that the method calculateFilters() only needs to be called once
        3. this would also apply to the updateCharts() method below
        4. during increment 3 i may change some of these method names to be clearer but we don't have a huge amount of time for it to matter
         */
    }

    // filters the charts, also updates data intervals if they need to change
    public void updateCharts(String metric, String gender, String age, String context, String income, String stringStartDate, String stringEndDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDate, endDate;

        // converting the string dates
        if (stringStartDate.equals("Any")) {
            startDate = chartCalculator.getImpressionLog().get(0).getDate();
        } else {
            startDate = LocalDateTime.parse(stringStartDate, formatter);
        }
        if (stringEndDate.equals("Any")){
             endDate = chartCalculator.getImpressionLog().get(chartCalculator.getImpressionLog().size() - 1).getDate();
        } else {
            endDate = LocalDateTime.parse(stringEndDate, formatter);
        }

        // checking if the date selection is correct
        if (startDate.isBefore(chartCalculator.getImpressionLog().get(0).getDate())) {
            return;
        } else if (endDate.isAfter(chartCalculator.getImpressionLog().get(chartCalculator.getImpressionLog().size() - 1).getDate())) {
            return;
        } else if (startDate.isAfter(endDate)) {
            return;
        }

        // update intervals only if the ranges need to change
        if (!(this.startDate == startDate) || !(this.endDate == endDate)) {
            chartCalculator.calculateIntervals(startDate, endDate);
        }
        this.startDate = startDate;
        this.endDate = endDate;

        // update the filters
        chartCalculator.calculateFilters("hours", gender, age, context, income, startDate, endDate);
        this.hoursChart.updateChart(chartCalculator, metric);

        chartCalculator.calculateFilters("days", gender, age, context, income, startDate, endDate);
        this.daysChart.updateChart(chartCalculator, metric);

        chartCalculator.calculateFilters("weeks", gender, age, context, income, startDate, endDate);
        this.weeksChart.updateChart(chartCalculator, metric);

        chartCalculator.calculateFilters("months", gender, age, context, income, startDate, endDate);
        this.monthsChart.updateChart(chartCalculator, metric);

        chartCalculator.calculateFilters("years", gender, age, context, income, startDate, endDate);
        this.yearsChart.updateChart(chartCalculator, metric);;
    }

    public void updateBounce(int pageLimit, int bounceTime) {
        if (chartCalculator != null) {
            chartCalculator.setPageLimit(pageLimit);
            chartCalculator.setBounceTime(bounceTime);
        }
    }

    public void createHistogram(ChartCalculator histogramCalculator) {
        histogramCalculator.calculateIntervals(null, null);
        histogramCalculator.calculateFilters("days", "Any" ,"Any", "Any", "Any", null, null);
        histogram.updateHistogram(histogramCalculator);
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

    public JFreeChart getHistogram() {
        return histogram.getHistogram();
    }
}
