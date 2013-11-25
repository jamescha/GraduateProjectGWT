package csuf.graduate.project.server;
import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Series;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import csuf.graduate.project.client.GreetingService;
import csuf.graduate.project.shared.Listen;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {
	
	
	public void startListen(Chart chart,Series series)
	{
		Listen listen = new Listen("serial@/dev/ttyUSB0:115200",chart,series);
	}
}
