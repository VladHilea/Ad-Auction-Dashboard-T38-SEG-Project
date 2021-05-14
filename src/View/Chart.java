package View;

import Models.ChartCalculator;

import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.ui.ApplicationFrame;
import org.jfree.data.category.DefaultCategoryDataset;

public class Chart extends ApplicationFrame {
	private JFreeChart chart;

	private final String granularity;
	private String chartTitle;
	private String metric;

	public Chart(String applicationTitle, String metric, String granularity, int pageLimit, int bounceTime) {
		super(applicationTitle);

		this.granularity = granularity;
		this.chartTitle = metric + " vs Time (" + granularity + ")";
		this.metric = metric;

		updateChart(new ChartCalculator(pageLimit, bounceTime));

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560 , 367));
		setContentPane(chartPanel);
	}

	// updates the chart with no metric change
	public void updateChart(ChartCalculator calculator) {
		this.chart = ChartFactory.createLineChart(chartTitle,
				"Time (" + granularity + ") ","Number of " + metric,
				createDataset(calculator, metric),
				PlotOrientation.VERTICAL,
				true,true,false);
	}

	// updates the chart with a metric change
	public void updateChart(ChartCalculator calculator, String metric) {
		this.chartTitle = metric + " vs Time (" + granularity + ")";
		this.metric = metric;

		this.chart = ChartFactory.createLineChart(chartTitle,
				"Time (" + granularity + ") ","Number of " + metric,
				createDataset(calculator, metric),
				PlotOrientation.VERTICAL,
				true,true,false);
	}

	// creates and adds to the dataset using values from the chartCalculator lists
	public DefaultCategoryDataset createDataset(ChartCalculator calculator, String metric) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		switch (metric) {
			case "Impressions": {
				ArrayList<Integer> impList = calculator.getImpressionsNoList();
				int count = 1;

				for (int i : impList) {
					dataset.addValue(i, metric, String.valueOf(count));
					count++;
				}
				break;
			}
			case "Uniques": {
				ArrayList<Integer> uniList = calculator.getUniquesNoList();
				int count = 1;

				for (int i : uniList) {
					dataset.addValue(i, metric, String.valueOf(count));
					count++;
				}
				break;
			}
			case "Clicks": {
				ArrayList<Integer> clickList = calculator.getClicksNoList();
				int count = 1;

				for (int i : clickList) {
					dataset.addValue(i, metric, String.valueOf(count));
					count++;
				}
				break;
			}
			case "Bounces": {
				ArrayList<Integer> bounceList = calculator.getBouncesNoList();
				int count = 1;

				for (int i : bounceList) {
					dataset.addValue(i, metric, String.valueOf(count));
					count++;
				}
				break;
			}
			case "Conversions": {
				ArrayList<Integer> conversionList = calculator.getConversionsNoList();
				int count = 1;

				for (int i : conversionList) {
					dataset.addValue(i, metric, String.valueOf(count));
					count++;
				}
				break;
			}
			case "Total Impression Cost": {
				ArrayList<Float> ticList = calculator.getTotalImpressionCostList();
				int count = 1;

				for (float i : ticList) {
					dataset.addValue(i, metric, String.valueOf(count));
					count++;
				}
				break;
			}
			case "Total Click Cost": {
				ArrayList<Float> tccList = calculator.getTotalClickCostList();
				int count = 1;

				for (float i : tccList) {
					dataset.addValue(i, metric, String.valueOf(count));
					count++;
				}
				break;
			}
			case "CTR": {
				ArrayList<Float> ctrList = calculator.getCtrList();
				int count = 1;

				for (float i : ctrList) {
					dataset.addValue(i, metric, String.valueOf(count));
					count++;
				}
				break;
			}
			case "CPA": {
				ArrayList<Float> cpaList = calculator.getCpaList();
				int count = 1;

				for (float i : cpaList) {
					dataset.addValue(i, metric, String.valueOf(count));
					count++;
				}
				break;
			}
			case "CPC": {
				ArrayList<Float> cpcList = calculator.getCpcList();
				int count = 1;

				for (float i : cpcList) {
					dataset.addValue(i, metric, String.valueOf(count));
					count++;
				}
				break;
			}
			case "CPM": {
				ArrayList<Float> cpmList = calculator.getCpmList();
				int count = 1;

				for (float i : cpmList) {
					dataset.addValue(i, metric, String.valueOf(count));
					count++;
				}
				break;
			}
			case "Bounce Rate": {
				ArrayList<Float> brList = calculator.getBrList();
				int count = 1;

				for (float i : brList) {
					dataset.addValue(i, metric, String.valueOf(count));
					count++;
				}
				break;
			}
		}
		return dataset;
	}

	public JFreeChart getChart() {
		return chart;
	}

}
