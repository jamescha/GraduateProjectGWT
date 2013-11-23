package csuf.graduate.project.server;
import java.io.IOException;

import csuf.graduate.project.shared.Listen;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import csuf.graduate.project.client.GreetingService;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {
	
	
	
	@SuppressWarnings("static-access")
	public void startListen()
	{
		Listen listen = new Listen("serial@/dev/ttyUSB0:115200");
	}
}
