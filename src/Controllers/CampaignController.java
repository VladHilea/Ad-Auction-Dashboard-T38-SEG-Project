package Controllers;

import Models.Campaign;
import Models.ChartCalculator;
import Models.MetricCalculator;

public class CampaignController {
    public Campaign campaign;

    public CampaignController() {
        this.campaign = new Campaign();
    }

    public void createCampaign(String impressionFileLocation, String clickFileLocation, String serverFileLocation) {
        this.campaign = new Campaign(impressionFileLocation, clickFileLocation, serverFileLocation);
    }

    public MetricCalculator createMetrics() {
        return new MetricCalculator(campaign.getImpressionLog(), campaign.getClickLog(), campaign.getServerLog());
    }

    public ChartCalculator createCharts() {
        return new ChartCalculator(campaign.getImpressionLog(), campaign.getClickLog(), campaign.getServerLog(), campaign.getUsers());
    }
}
