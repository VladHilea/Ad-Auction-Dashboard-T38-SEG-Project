package Controllers;

import Models.MetricCalculator;

public class MetricController {
    private MetricCalculator metricCalculator;

    public MetricController() {
        this.metricCalculator = new MetricCalculator();
    }

    public void createMetrics(MetricCalculator metricCalculator) {
        this.metricCalculator = metricCalculator;
    }

    public void updateMetrics(String gender, String age, String context, String income, String stringStartDate, String stringEndDate) {
        metricCalculator.calculateMetrics(gender, age, context, income, stringStartDate, stringEndDate);
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
