package csuf.graduate.project.shared;

import java.io.IOException;
import java.util.Date;

import net.tinyos.packet.BuildSource;
import net.tinyos.packet.PacketSource;
import net.tinyos.util.Dump;
import net.tinyos.util.PrintStreamMessenger;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Series;

public class Listen {
	
	
	
	public Listen (String source,Chart chart,Series series) {
        PacketSource reader = BuildSource.makePacketSource(source);
		
        
        
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
