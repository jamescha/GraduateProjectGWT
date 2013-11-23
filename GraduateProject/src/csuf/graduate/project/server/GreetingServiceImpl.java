package csuf.graduate.project.server;
import java.io.IOException;

import net.tinyos.tools.Listen;

//import csuf.graduate.project.shared.Listen;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import csuf.graduate.project.client.GreetingService;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {
	
	public void startListen()
	{
		String args[] = {"-comm", "serial@/dev/ttyUSB0:115200"};
		try {
			Listen.main(args);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
