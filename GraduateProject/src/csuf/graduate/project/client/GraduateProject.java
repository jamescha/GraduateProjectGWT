package csuf.graduate.project.client;

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
	
	
	private HorizontalPanel homePanel = new HorizontalPanel();
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		final Home home = new Home();
		
		homePanel.add(home);
		RootPanel.get("home").add(homePanel);
		
	}
}
