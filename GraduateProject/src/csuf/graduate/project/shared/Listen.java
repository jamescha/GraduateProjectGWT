package csuf.graduate.project.shared;

import java.io.IOException;
import java.util.Date;

import net.tinyos.packet.BuildSource;
import net.tinyos.packet.PacketSource;
import net.tinyos.util.Dump;
import net.tinyos.util.PrintStreamMessenger;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Series;

import com.google.gwt.user.client.ui.RootPanel;

public class Listen {
	Chart chart = new Chart()
	   .setType(Series.Type.SPLINE)
	   .setChartTitleText("Lawn Tunnels")
	   .setMarginRight(10);
	
	Series series = chart.createSeries()
			   .setName("Moles per Yard")
			   .setPoints(new Number[] { 163, 203, 276, 408, 547, 729, 628 });
	
	
	public Listen (String source) {
        PacketSource reader = BuildSource.makePacketSource(source);
		
        chart.addSeries(series);
		RootPanel.get().add(chart);
        
		try {
			  reader.open(PrintStreamMessenger.err);
			  for (;;) {
			    byte[] packet = reader.readPacket();
			    
			    series.addPoint(new Date().getTime(), 10, true, true, true);
			    Dump.printPacket(System.out, packet);
			    System.out.println();
			    System.out.flush();
			  }
			}
		catch (IOException e) {
			    System.err.println("Error on " + reader.getName() + ": " + e);
			}
	}
}
