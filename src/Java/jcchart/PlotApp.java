
import java.awt.*;
import jclass.chart.*;
import jclass.chart.JCChart;
import jclass.chart.ChartDataView;
import jclass.chart.FileDataSource;


public class PlotApp extends java.applet.Applet {
    JCChart c;
  
	public void init() {

	      setLayout(new GridLayout(1,1));


        System.out.println("applet initialized");
	      try {
	          c = new JCChart(JCChart.BAR);
	          /*
	          c.getHeader().setText("plot1 Bar Chart");
	          c.getHeader().setIsShowing(true);
	          c.getFooter().setText("plot1 Footer Text");
	          c.getFooter().setIsShowing(true);
	         */
	          
	          c.getLegend().setIsShowing(true);         
	          ChartDataView arr =	 c.getDataView(0);
	          arr.setDataSource(new AppletDataSource(this));
              System.out.println("got data view " + arr); 
              
              arr.setAutoLabel(true);
              arr.getYAxis().setPrecision(0);
              arr.getYAxis().setTitle(new JCAxisTitle("My YAxis Title"));          
             
              add(c);
	      }  catch (Exception e) {
	          e.printStackTrace(System.out);
      	      }
			//{{INIT_CONTROLS
		//setLayout(null);
		//setSize(430,270);
		//}}
}
      
    
    
    
    
    
	public static void main(String args[]) 
 	{
	      Frame f = new Frame();
	      PlotApp p = new PlotApp();
	      p.init();
      	  f.setLayout(new GridLayout(2,1));
      	  f.add(p);
          f.pack();
          f.resize(600, 400);
          f.show();
  	}
    
	//{{DECLARE_CONTROLS
	//}}
}
