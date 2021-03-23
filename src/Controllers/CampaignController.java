package Controllers;

import Models.Campaign;
import Models.ChartCalculator;
import Models.MetricCalculator;

public class CampaignController {
    public Campaign campaign;

    public CampaignController() {
        this.campaign = new Campaign();
    }

    public void createCampaign() {
        this.campaign = new Campaign("src/Logs/impression_log.csv", "src/Logs/click_log.csv", "src/Logs/server_log.csv");
    }

    public MetricCalculator createMetrics() {
        return new MetricCalculator(campaign.getImpressionLog(), campaign.getClickLog(), campaign.getServerLog());
    }

    public ChartCalculator createCharts() {
        return new ChartCalculator(campaign.getImpressionLog(), campaign.getClickLog(), campaign.getServerLog(), campaign.getUsers());
    }
}
