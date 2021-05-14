package Controllers;

import Models.MetricCalculator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MetricController {
    private MetricCalculator metricCalculator;

    public MetricController(int pageLimit, int bounceTime) {
        this.metricCalculator = new MetricCalculator(pageLimit, bounceTime);
    }

    public void createMetrics(MetricCalculator metricCalculator) {
        this.metricCalculator = metricCalculator;
    }

    public void updateMetrics(String gender, String age, String context, String income, String stringStartDate, String stringEndDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDate;
        LocalDateTime endDate;

        if (stringStartDate.equals("Any")) {
            startDate = metricCalculator.getImpressionLog().get(0).getDate();
        } else {
            startDate = LocalDateTime.parse(stringStartDate, formatter);
        }
        if (stringEndDate.equals("Any")){
            endDate = metricCalculator.getImpressionLog().get(metricCalculator.getImpressionLog().size() - 1).getDate();
        } else {
            endDate = LocalDateTime.parse(stringEndDate, formatter);
        }
        metricCalculator.calculateMetrics(gender, age, context, income, startDate, endDate);
    }

    public void updateBounce(int pageLimit, int bounceTime) {
        metricCalculator.setPageLimit(pageLimit);
        metricCalculator.setBounceTime(bounceTime);
    }

    public int getImpressionsNo() {
        return metricCalculator.getImpressionsNo();
    }

    public int getUniquesNo() {
        return metricCalculator.getUniquesNo();
    }

    public int getClicksNo() {
        return metricCalculator.getClicksNo();
    }

    public int getBouncesNo() {
        return metricCalculator.getBouncesNo();
    }

    public int getConversionsNo() {
        return metricCalculator.getConversionsNo();
    }

    public float getTotalImpressionCost() {
        return metricCalculator.getTotalImpressionCost();
    }

    public float getTotalClickCost() {
        return metricCalculator.getTotalClickCost();
    }

    public float getCtr() {
        return metricCalculator.getCtr();
    }

    public float getCpa() {
        return metricCalculator.getCpa();
    }

    public float getCpc() {
        return metricCalculator.getCpc();
    }

    public float getCpm() {
        return metricCalculator.getCpm();
    }

    public float getBr() {
        return metricCalculator.getBr();
    }
}
