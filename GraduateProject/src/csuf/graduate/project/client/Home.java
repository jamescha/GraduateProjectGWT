package csuf.graduate.project.client;

import java.io.IOException;

import com.github.gwtbootstrap.client.ui.Button;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class Home extends Composite {

	private static HomeUiBinder uiBinder = GWT.create(HomeUiBinder.class);
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	interface HomeUiBinder extends UiBinder<Widget, Home> {
	}

	public Home() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Button startButton;

	@UiHandler("startButton")
	void onClick(ClickEvent e) {
		try {
			greetingService.startListen(new AsyncCallback<Void>() {
				
				@Override
				public void onSuccess(Void result) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}
			});
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}				
	}
}
