package csuf.graduate.project.client;

import java.util.Date;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Series;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Heading;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
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
	Heading test;

	@UiHandler("startButton")
	void onClick(ClickEvent e) {
		Timer tempTimer =new Timer() {
			
			@Override
			public void run() {
				greetingService.startListen(new AsyncCallback<String>() {
					
					@Override
					public void onSuccess(String result) {
						
						Integer countHum = 0;
						Integer countMSP = 0;
						Integer countSen = 0;
						Integer sumMSP = 0;
						Integer sumSen = 0;
						Integer sumHum = 0;
						Double averageMSP = 0.0;
						Double averageHum = 0.0;
						Double averageSen = 0.0;
						Double temperatureMSP = 0.0;
						Double temperatureSen = 0.0;
						Double humidity = 0.0;
						
						
						try{
							test.setText(result);
							final String[] newTextArray = result.split(" ", -1);
							for(Integer i = 0; i<20; i+=2) {
								if(Integer.parseInt((newTextArray[18+i] + newTextArray[19+i]),16) < 1500 ) { //Humidity
									sumHum += Integer.parseInt((newTextArray[18+i] + newTextArray[19+i]),16);
									countHum++;
								}
								else if (Integer.parseInt((newTextArray[18+i] + newTextArray[19+i]),16) > 1500&& //MSP430 
										 Integer.parseInt((newTextArray[18+i] + newTextArray[19+i]),16) < 3500 ) {
									sumMSP += Integer.parseInt((newTextArray[18+i] + newTextArray[19+i]),16);
									countMSP++;
								}
								else if (Integer.parseInt((newTextArray[18+i] + newTextArray[19+i]),16) > 5500) { //Sensirian
									sumSen += Integer.parseInt((newTextArray[18+i] + newTextArray[19+i]),16);
									countSen++;
								}
								
								averageMSP = sumMSP/(countMSP * 1.0);
								averageSen = sumSen/(countSen * 1.0);
								averageHum = sumHum/(countHum * 1.0);
								
								temperatureMSP = ((((((averageMSP / 4096.0) * 1.5) - 0.986) / 0.00355) * 9.0) / 5.0) + 32.0;
								temperatureSen = (((-38.4 + (averageSen * 0.0098)) * 9.0) / 5.0) + 32.0;
								humidity = (-0.0000028 * averageHum * averageHum) + (0.0405 * averageHum - 4) ;

							}
							
							series.addPoint(  
				                    new Date().getTime(),  
				                    com.google.gwt.user.client.Random.nextDouble(),  
				                    true, true, true  
				                );  
						}
						catch (NullPointerException e){}
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
					
				});
				
			}
		};
		
		tempTimer.scheduleRepeating(1000);
	}
}
