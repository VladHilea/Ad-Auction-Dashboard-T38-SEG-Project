package View;

import Models.MetricCalculator;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Chart extends ApplicationFrame{
	public Chart( String applicationTitle , String chartTitle, MetricCalculator calculator ) {
	      super(applicationTitle);
	      String metric = "impressions"; // change this to view a different metric on the graph
	      JFreeChart Chart = ChartFactory.createLineChart(
	         chartTitle,
	         "Time (Day) ","Number of "+metric,
	         createDataset(metric,14, calculator),
	         PlotOrientation.VERTICAL,
	         true,true,false);
	         
	      ChartPanel chartPanel = new ChartPanel( Chart );
	      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
	      setContentPane( chartPanel );
	   }
	
	private DefaultCategoryDataset createDataset(String metric, int days, MetricCalculator calculator) {
	      DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
	      for (int i = 0 ; i<days ; i++) {
	    	String day = String.valueOf(i);

	    	if (metric.equals("impressions")) {
	    	  dataset.addValue(calculator.getImpressionsNo(), metric, day);	
	    	}
	    	if (metric.equals("uniques")) {
		    	  dataset.addValue(calculator.getUniquesNo(), metric, day);	
		    }
	    	if (metric.equals("clicks")) {
		    	  dataset.addValue(calculator.getClicksNo(), metric, day);	
		    }
	    	if (metric.equals("bounces")) {
		    	  dataset.addValue(calculator.getBouncesNo(), metric, day);
		    }
	    	if (metric.equals("conversions")) {
		    	  dataset.addValue(calculator.getConversionsNo(), metric, day);	
		    }
	    	if (metric.equals("total impression cost")) {
		    	  dataset.addValue(calculator.getTotalImpressionCost(), metric, day);	
		    }
	    	if (metric.equals("total click cost")) {
		    	  dataset.addValue(calculator.getTotalClickCost(), metric, day);	
		    }
	    	if (metric.equals("ctr")) {
		    	  dataset.addValue(calculator.getCtr(), metric, day);
		    }
	    	if (metric.equals("cpa")) {
		    	  dataset.addValue(calculator.getCpa(), metric, day);
		    }
	    	if (metric.equals("cpc")) {
		    	  dataset.addValue(calculator.getCpc(), metric, day);
		    }
	    	if (metric.equals("cpm")) {
		    	  dataset.addValue(calculator.getCpm(), metric, day);
		    }
	    	if (metric.equals("bounce rate")) {
		    	  dataset.addValue(calculator.getBr(), metric, day);
		    }
	        
	      }
	      return dataset;
	   }
}
