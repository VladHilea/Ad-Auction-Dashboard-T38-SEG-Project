package View;

import Models.ChartCalculator;
import org.jfree.chart.ChartPanel;

import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Chart extends ApplicationFrame{
	private final ChartCalculator daysCalculator;
	private final ChartCalculator weeksCalculator;
	private final ChartCalculator monthsCalculator;

	public Chart(String applicationTitle, ChartCalculator daysCalculator, ChartCalculator weeksCalculator, ChartCalculator monthsCalculator) {
		super(applicationTitle);

		this.daysCalculator = daysCalculator;
		this.weeksCalculator = weeksCalculator;
		this.monthsCalculator = monthsCalculator;
	}

	public JFreeChart getDaysChart(String chartTitle, String metric) {
		JFreeChart daysChart = ChartFactory.createLineChart(chartTitle,
				"Time (Day) ","Number of " + metric,
				createDataset(metric, daysCalculator),
				PlotOrientation.VERTICAL,
				true,true,false);

		ChartPanel chartPanel = new ChartPanel( daysChart );
		chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
		setContentPane( chartPanel );

		return daysChart;
	}

	public JFreeChart getWeeksChart(String chartTitle, String metric) {
		JFreeChart weeksChart = ChartFactory.createLineChart(chartTitle,
				"Time (Week) ","Number of " + metric,
				createDataset(metric, weeksCalculator),
				PlotOrientation.VERTICAL,
				true,true,false);

		ChartPanel chartPanel = new ChartPanel( weeksChart );
		chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
		setContentPane( chartPanel );

		return weeksChart;
	}

	public JFreeChart getMonthsChart(String chartTitle, String metric) {
		JFreeChart monthsChart = ChartFactory.createLineChart(chartTitle,
				"Time (Day) ","Number of " + metric,
				createDataset(metric, monthsCalculator),
				PlotOrientation.VERTICAL,
				true,true,false);

		ChartPanel chartPanel = new ChartPanel( monthsChart );
		chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
		setContentPane( chartPanel );

		return monthsChart;
	}

	// creates and adds to the dataset using values from the chartCalculator lists 
	private DefaultCategoryDataset createDataset(String metric, ChartCalculator calculator) {
	      DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
	      if (metric.equals("impressions")) {
	        ArrayList<Integer> impList = calculator.getImpressionsNoList();
	        int count = 1;
	        for (int i : impList) {
	          dataset.addValue(i, metric, String.valueOf(count));
	          count = count + 1;
	        }
	      }
	      if (metric.equals("uniques")) {
		    ArrayList<Integer> uniList = calculator.getUniquesNoList();
		    int count = 1;
		    for (int i : uniList) {
		      dataset.addValue(i, metric, String.valueOf(count));
		      count = count + 1;
		     }
		   }
	       if (metric.equals("clicks")) {
	         ArrayList<Integer> clickList = calculator.getClicksNoList();
			 int count = 1;
			 for (int i : clickList) {
			   dataset.addValue(i, metric, String.valueOf(count));
			   count = count + 1;
			 }	
		   }
	       if (metric.equals("bounces")) {
	         ArrayList<Integer> bounceList = calculator.getBouncesNoList();
		     int count = 1;
		     for (int i : bounceList) {
		       dataset.addValue(i, metric, String.valueOf(count));
			   count = count + 1;
			 }
		   }
	       if (metric.equals("conversions")) {
		     ArrayList<Integer> conversionList = calculator.getConversionsNoList();
		     int count = 1;
		     for (int i : conversionList) {
		       dataset.addValue(i, metric, String.valueOf(count));
			   count = count + 1;
		     }
		   }
	       if (metric.equals("total impression cost")) {
	         ArrayList<Float> ticList = calculator.getTotalImpressionCostList();
			     int count = 1;
           
			 for (double i : ticList) {
			   dataset.addValue(i, metric, String.valueOf(count));
			   count = count + 1;
			 }	
		   }
	       if (metric.equals("total click cost")) {
	         ArrayList<Float> tccList = calculator.getTotalClickCostList();
		       int count = 1;
           
			 for (double i : tccList) {
			   dataset.addValue(i, metric, String.valueOf(count));
			   count = count + 1;
		     }	
		   }
	       if (metric.equals("ctr")) {
		       ArrayList<Float> ctrList = calculator.getCtrList();
		       int count = 1;

			 for (double i : ctrList) {
			   dataset.addValue(i, metric, String.valueOf(count));
			   count = count + 1;
		     }
		   }
	       if (metric.equals("cpa")) {
		       ArrayList<Float> cpaList = calculator.getCpaList();
		       int count = 1;
           
			 for (double i : cpaList) {
			   dataset.addValue(i, metric, String.valueOf(count));
			   count = count + 1;
		     }
		   }
	       if (metric.equals("cpc")) {
		       ArrayList<Float> cpcList = calculator.getCpcList();
		       int count = 1;
           
			 for (double i : cpcList) {
			   dataset.addValue(i, metric, String.valueOf(count));
			   count = count + 1;
		     }
		   }
	       if (metric.equals("cpm")) {
		       ArrayList<Float> cpmList = calculator.getCpmList();
		       int count = 1;
           
			 for (double i : cpmList) {
			   dataset.addValue(i, metric, String.valueOf(count));
			   count = count + 1;
		     }
		   }
	       if (metric.equals("bounce rate")) {
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
