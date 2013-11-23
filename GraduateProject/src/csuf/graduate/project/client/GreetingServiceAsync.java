package csuf.graduate.project.client;

import java.io.IOException;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void startListen(AsyncCallback<Void> callback) throws IOException;
}
