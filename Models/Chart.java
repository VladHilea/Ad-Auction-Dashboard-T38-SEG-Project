import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Chart extends ApplicationFrame{
	public Chart( String applicationTitle , String chartTitle ) {
	      super(applicationTitle);
	      String metric = "impressions"; // change this to view a different metric on the graph
	      JFreeChart Chart = ChartFactory.createLineChart(
	         chartTitle,
	         "Time (Day) ","Number of "+metric,
	         createDataset(metric,14),
	         PlotOrientation.VERTICAL,
	         true,true,false);
	         
	      ChartPanel chartPanel = new ChartPanel( Chart );
	      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
	      setContentPane( chartPanel );
	   }
	
	private DefaultCategoryDataset createDataset( String metric, int days) {
	      DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
	      MetricCalculator calculator = new MetricCalculator();
	      for (int i = 1 ; i<days+1 ; i++) {
	    	String day = String.valueOf(i);
	    	calculator.calculateMetrics(2, 200, "2015-01-0"+ day +" 00:00:00", "2015-01-0"+ day + " 23:59:59");
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
		    	  dataset.addValue(calculator.getCTR(), metric, day);	
		    }
	    	if (metric.equals("cpa")) {
		    	  dataset.addValue(calculator.getCPA(), metric, day);	
		    }
	    	if (metric.equals("cpc")) {
		    	  dataset.addValue(calculator.getCPC(), metric, day);	
		    }
	    	if (metric.equals("cpm")) {
		    	  dataset.addValue(calculator.getCPM(), metric, day);	
		    }
	    	if (metric.equals("bounce rate")) {
		    	  dataset.addValue(calculator.getBounceRate(), metric, day);	
		    }
	        
	      }
	      return dataset;
	   }
	   
	public static void main( String[ ] args ) {
	      Chart chart = new Chart(
	         "Metrics vs Time" ,
	         "Metrics vs Time");

	      chart.pack();
	      RefineryUtilities.centerFrameOnScreen( chart );
	      chart.setVisible( true );
	   }
}
