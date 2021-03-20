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

	private final String chartTitle;
	private final String metric;

	public Chart(String applicationTitle, String chartTitle, String metric) {
		super(applicationTitle);

		this.chartTitle = chartTitle;
		this.metric = metric;

		updateChart(new ChartCalculator());

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560 , 367));
		setContentPane(chartPanel);
	}

	public void updateChart(ChartCalculator calculator) {
		this.chart = ChartFactory.createLineChart(chartTitle,
				"Time (Day) ","Number of " + metric,
				createDataset(metric, calculator),
				PlotOrientation.VERTICAL,
				true,true,false);
	}

	public JFreeChart getChart() {
		return chart;
	}

	// creates and adds to the dataset using values from the chartCalculator lists
	public DefaultCategoryDataset createDataset(String metric, ChartCalculator calculator) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset( );

		if (metric.equals("Impressions")) {
			ArrayList<Integer> impList = calculator.getImpressionsNoList();
			int count = 1;
			for (int i : impList) {
				dataset.addValue(i, metric, String.valueOf(count));
				count = count + 1;
			}
		}

		if (metric.equals("Uniques")) {
			ArrayList<Integer> uniList = calculator.getUniquesNoList();
			int count = 1;
			for (int i : uniList) {
				dataset.addValue(i, metric, String.valueOf(count));
				count = count + 1;
			}
		}

		if (metric.equals("Clicks")) {
			ArrayList<Integer> clickList = calculator.getClicksNoList();
			int count = 1;
			for (int i : clickList) {
				dataset.addValue(i, metric, String.valueOf(count));
				count = count + 1;
			}
		}

		if (metric.equals("Bounces")) {
			ArrayList<Integer> bounceList = calculator.getBouncesNoList();
			int count = 1;
			for (int i : bounceList) {
				dataset.addValue(i, metric, String.valueOf(count));
				count = count + 1;
			}
		}

		if (metric.equals("Conversions")) {
			ArrayList<Integer> conversionList = calculator.getConversionsNoList();
			int count = 1;
			for (int i : conversionList) {
				dataset.addValue(i, metric, String.valueOf(count));
				count = count + 1;
			}
		}

		if (metric.equals("Total Impression Cost")) {
			ArrayList<Float> ticList = calculator.getTotalImpressionCostList();
			int count = 1;

			for (double i : ticList) {
				dataset.addValue(i, metric, String.valueOf(count));
				count = count + 1;
			}
		}

		if (metric.equals("Total Click Cost")) {
			ArrayList<Float> tccList = calculator.getTotalClickCostList();
			int count = 1;

			for (double i : tccList) {
				dataset.addValue(i, metric, String.valueOf(count));
				count = count + 1;
			}
		}

		if (metric.equals("CTR")) {
			ArrayList<Float> ctrList = calculator.getCtrList();
			int count = 1;

			for (double i : ctrList) {
				dataset.addValue(i, metric, String.valueOf(count));
				count = count + 1;
			}
		}

		if (metric.equals("CPA")) {
			ArrayList<Float> cpaList = calculator.getCpaList();
			int count = 1;

			for (double i : cpaList) {
				dataset.addValue(i, metric, String.valueOf(count));
				count = count + 1;
			}
		}

		if (metric.equals("CPC")) {
			ArrayList<Float> cpcList = calculator.getCpcList();
			int count = 1;

			for (double i : cpcList) {
				dataset.addValue(i, metric, String.valueOf(count));
				count = count + 1;
			}
		}

		if (metric.equals("CPM")) {
			ArrayList<Float> cpmList = calculator.getCpmList();
			int count = 1;

			for (double i : cpmList) {
				dataset.addValue(i, metric, String.valueOf(count));
				count = count + 1;
			}
		}

		if (metric.equals("Bounce Rate")) {
			ArrayList<Float> brList = calculator.getBrList();
			int count = 1;

			for (double i : brList) {
				dataset.addValue(i, metric, String.valueOf(count));
				count = count + 1;
			}
		}
		return dataset;
	}
}
