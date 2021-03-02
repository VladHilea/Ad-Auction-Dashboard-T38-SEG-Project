package View;

import Models.ChartCalculator;
import Models.MetricCalculator;
import org.jfree.chart.ChartPanel;

import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Chart extends ApplicationFrame{
	public Chart( String applicationTitle , String chartTitle, ChartCalculator calculator ) {
	      super(applicationTitle);
	      String metric = "uniques"; // change this to view a different metric on the graph
	      JFreeChart Chart = ChartFactory.createLineChart(
	         chartTitle,
	         "Time (Day) ","Number of "+metric,
	         createDataset(metric,calculator),
	         PlotOrientation.VERTICAL,
	         true,true,false);
	         
	      ChartPanel chartPanel = new ChartPanel( Chart );
	      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
	      setContentPane( chartPanel );
	   }
	
	
	// creates and adds to the dataset using values from the chartCalculator lists 
	private DefaultCategoryDataset createDataset(String metric, ChartCalculator calculator) {
	      DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
	      if (metric.equals("impressions")) {
	        ArrayList<Integer> impList = calculator.getImpressionsNoList();
	        int count = 0;
	        for (int i : impList) {
	          dataset.addValue(i, metric, String.valueOf(count));
	          count = count + 1;
	        }
	      }
	      if (metric.equals("uniques")) {
		    ArrayList<Integer> uniList = calculator.getUniquesNoList();
		    int count = 0;
		    for (int i : uniList) {
		      dataset.addValue(i, metric, String.valueOf(count));
		      count = count + 1;
		     }
		   }
	       if (metric.equals("clicks")) {
	         ArrayList<Integer> clickList = calculator.getClicksNoList();
			 int count = 0;
			 for (int i : clickList) {
			   dataset.addValue(i, metric, String.valueOf(count));
			   count = count + 1;
			 }	
		   }
	       if (metric.equals("bounces")) {
	         ArrayList<Integer> bounceList = calculator.getBouncesNoList();
		     int count = 0;
		     for (int i : bounceList) {
		       dataset.addValue(i, metric, String.valueOf(count));
			   count = count + 1;
			 }
		   }
	       if (metric.equals("conversions")) {
		     ArrayList<Integer> conversionList = calculator.getConversionsNoList();
		     int count = 0;
		     for (int i : conversionList) {
		       dataset.addValue(i, metric, String.valueOf(count));
			   count = count + 1;
		     }
		   }
	       if (metric.equals("total impression cost")) {
	         ArrayList<Double> ticList = calculator.getTotalImpressionCostList();
			 int count = 0;
			 for (double i : ticList) {
			   dataset.addValue(i, metric, String.valueOf(count));
			   count = count + 1;
			 }	
		   }
	       if (metric.equals("total click cost")) {
	         ArrayList<Double> tccList = calculator.getTotalClickCostList();
		     int count = 0;
			 for (double i : tccList) {
			   dataset.addValue(i, metric, String.valueOf(count));
			   count = count + 1;
		     }	
		   }
	       if (metric.equals("ctr")) {
		     ArrayList<Double> ctrList = calculator.getCtrList();
		     int count = 0;
			 for (double i : ctrList) {
			   dataset.addValue(i, metric, String.valueOf(count));
			   count = count + 1;
		     }
		   }
	       if (metric.equals("cpa")) {
		     ArrayList<Double> cpaList = calculator.getCpaList();
		     int count = 0;
			 for (double i : cpaList) {
			   dataset.addValue(i, metric, String.valueOf(count));
			   count = count + 1;
		     }
		   }
	       if (metric.equals("cpc")) {
		     ArrayList<Double> cpcList = calculator.getCpcList();
		     int count = 0;
			 for (double i : cpcList) {
			   dataset.addValue(i, metric, String.valueOf(count));
			   count = count + 1;
		     }
		   }
	       if (metric.equals("cpm")) {
		     ArrayList<Double> cpmList = calculator.getCpmList();
		     int count = 0;
			 for (double i : cpmList) {
			   dataset.addValue(i, metric, String.valueOf(count));
			   count = count + 1;
		     }
		   }
	       if (metric.equals("bounce rate")) {
		     ArrayList<Double> brList = calculator.getBrList();
		     int count = 0;
			 for (double i : brList) {
			   dataset.addValue(i, metric, String.valueOf(count));
			   count = count + 1;
		     }
		   }
	      return dataset;
	   }
}
