package csuf.graduate.project.server;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import csuf.graduate.project.client.GreetingService;
import csuf.graduate.project.shared.Listen;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {
	
	Listen listen = new Listen("serial@/dev/ttyUSB0:115200");
	
	public String startListen()
	{
		return listen.startListen();
	}
}
