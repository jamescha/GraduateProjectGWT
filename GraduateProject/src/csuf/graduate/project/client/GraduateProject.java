package csuf.graduate.project.client;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Series;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GraduateProject implements EntryPoint {
	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	Chart chart = new Chart()
	   .setType(Series.Type.SPLINE)
	   .setChartTitleText("Lawn Tunnels")
	   .setMarginRight(10);
	
	Series series = chart.createSeries()
			   .setName("Moles per Yard")
			   .setPoints(new Number[] { 163, 203, 276, 408, 547, 729, 628 });
	
	private HorizontalPanel homePanel = new HorizontalPanel();
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		final Home home = new Home();
		
		homePanel.add(home);
		RootPanel.get("home").add(homePanel);
		
		chart.addSeries(series);
		RootPanel.get().add(chart);
		
	}
}
