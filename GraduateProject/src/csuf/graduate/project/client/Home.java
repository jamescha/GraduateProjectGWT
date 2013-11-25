package csuf.graduate.project.client;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Series;

import com.github.gwtbootstrap.client.ui.Button;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class Home extends Composite {

	private static HomeUiBinder uiBinder = GWT.create(HomeUiBinder.class);
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	
	Chart chart = new Chart()
	   .setType(Series.Type.SPLINE)
	   .setChartTitleText("Lawn Tunnels")
	   .setMarginRight(10);
	
	Series series = chart.createSeries()
			   .setName("Moles per Yard")
			   .setPoints(new Number[] { 163, 203, 276, 408, 547, 729, 628 });
	

	interface HomeUiBinder extends UiBinder<Widget, Home> {
	}

	public Home() {
		initWidget(uiBinder.createAndBindUi(this));
		chart.addSeries(series);
		RootPanel.get().add(chart);
	}

	@UiField
	Button startButton;

	@UiHandler("startButton")
	void onClick(ClickEvent e) {
		greetingService.startListen(chart,series,new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});				
	}
	
	
}
