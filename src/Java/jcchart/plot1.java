
import java.awt.*;
import jclass.chart.*;
import jclass.chart.JCChart;
import jclass.chart.ChartDataView;
import jclass.chart.FileDataSource;


public class plot1 extends java.applet.Applet {
    JCChart c;
   
  
	public void init() {

	    /*
	      JCTitle title = new JCTitle();
	      title.setText("plot1 bar chart");
	      */
	      setLayout(new GridLayout(1,1));
	   	   

	      try {
	          c = new JCChart(JCChart.BAR);
	          c.getHeader().setText("plot1 Bar Chart");
	          c.getHeader().setIsShowing(true);
	          c.getFooter().setText("plot1 Footer Text");
	          c.getFooter().setIsShowing(true);
	         
	          
	          c.getLegend().setIsShowing(true);
	          
	          c.getDataView(0).setDataSource(new
	                 FileDataSource("e:\\dev\\src\\java\\plot2.txt"));

              ChartDataView arr = c.getDataView(0);
              arr.setAutoLabel(true);
              arr.getYAxis().setPrecision(0);
//              arr.getXAxis().setGap(100);
              arr.getYAxis().setTitle(new JCAxisTitle("My YAxis Title"));
            
             
              add(c);
	      }  catch (Exception e) {
	          e.printStackTrace(System.out);
      	      }
	}
      
    
    
    
    
    
	public static void main(String args[]) 
 	{
	      Frame f = new Frame();
	      plot1 p = new plot1();
	      /*
	      Button b = new Button();
	      b.setLabel("Switch Data View");
	      */
	      p.init();
      	  f.setLayout(new GridLayout(2,1));
      	  f.add(p);
//      	  f.add(b);
          f.pack();
          f.resize(600, 400);
          f.show();
  	}
    
}
