package View;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.ui.ApplicationFrame;

import Models.ChartCalculator;
import org.jfree.data.statistics.HistogramDataset;

public class Histogram extends ApplicationFrame {
  private JFreeChart histogram;
  
  public Histogram(String applicationTitle) {
	    super(applicationTitle);
		updateHistogram(new ChartCalculator());
		ChartPanel histogramPanel = new ChartPanel(histogram);
		histogramPanel.setPreferredSize(new java.awt.Dimension(560 , 367));
		setContentPane(histogramPanel);
  }

  //creates dataset that is being used by the histogram 
  public HistogramDataset createHistogramDataset(ChartCalculator histogramCalculator) {
	  ArrayList<Float> floatData = histogramCalculator.getTotalClickCostList();
	  ArrayList<Double> doubleData = new ArrayList<> ();
	  for ( float f : floatData) {
	    doubleData.add((double) f);
	  }
	  double [] data = new double [doubleData.size()];
	  for ( int i = 0 ; i<data.length ; i++) {
	    data[i] = doubleData.get(i);
	  }
	  HistogramDataset dataset = new HistogramDataset();

	  if (data.length != 0) {
		  dataset.addSeries("Histogram",data,20);
	  }
	  return dataset;
  }

	// updates the chart with time granularity change
	public void updateHistogram(ChartCalculator calculator) {
		this.histogram = ChartFactory.createHistogram( "Click Costs Distribution" ,"Click Costs","Frequency Density", createHistogramDataset(calculator),PlotOrientation.VERTICAL,true,true,false);
	}

  public JFreeChart getHistogram() {
    return histogram;
  }
}
