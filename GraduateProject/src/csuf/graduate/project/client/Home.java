package csuf.graduate.project.client;

import java.util.Date;

import org.moxieapps.gwt.highcharts.client.Axis;
import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Credits;
import org.moxieapps.gwt.highcharts.client.Legend;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.ToolTip;
import org.moxieapps.gwt.highcharts.client.ToolTipData;
import org.moxieapps.gwt.highcharts.client.ToolTipFormatter;
import org.moxieapps.gwt.highcharts.client.labels.DataLabels;
import org.moxieapps.gwt.highcharts.client.plotOptions.BarPlotOptions;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Heading;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
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
	
	Boolean mspToggle = true;
	Boolean senToggle = true;
	Boolean humToggle = true;
	
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	
	final Chart chart = new Chart()  
    .setType(Series.Type.SPLINE)  
    .setMarginRight(10)  
    .setChartTitleText("Sensor Data")  
    .setBarPlotOptions(new BarPlotOptions()  
        .setDataLabels(new DataLabels()  
            .setEnabled(true)  
        )  
    )  
    .setLegend(new Legend()  
        .setEnabled(false)  
    )  
    .setCredits(new Credits()  
        .setEnabled(false)  
    )  
    .setToolTip(new ToolTip()  
        .setFormatter(new ToolTipFormatter() {  
            public String format(ToolTipData toolTipData) {  
                return "<b>" + toolTipData.getSeriesName() + "</b><br/>" +  
                    DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss")  
                        .format(new Date(toolTipData.getXAsLong())) + "<br/>" +  
                    NumberFormat.getFormat("0.00").format(toolTipData.getYAsDouble());  
            }  
        })  
    );
	
	final Series msp = chart.createSeries()
			.setName("MSP Temperature");
	
	final Series hum = chart.createSeries()
			.setName("Humidity");
	
	final Series sen = chart.createSeries()
			.setName("Sen Temperature");
	

	interface HomeUiBinder extends UiBinder<Widget, Home> {
	}

	public Home() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getXAxis()  
        .setType(Axis.Type.DATE_TIME)  
        .setTickPixelInterval(150);  

	    chart.getYAxis()  
	        .setAxisTitleText("Value")  
	        .setPlotLines(  
	            chart.getYAxis().createPlotLine()  
	                .setValue(0)  
	                .setWidth(1)  
	                .setColor("#808080")  
	        );
          
		chart.addSeries(msp);
		chart.addSeries(sen);
		chart.addSeries(hum);
		RootPanel.get().add(chart);
	}

	@UiField
	Button startButton;
	Button MSPButton;
	Button SenButton;
	Button HumButton;
	Heading test;
	
	@UiHandler("MSPButton")
	void MSP(ClickEvent e){
		if (mspToggle)
			msp.hide();
		else
			msp.show();
		
		mspToggle = !mspToggle;
	}
	
	@UiHandler("SenButton")
	void Sen(ClickEvent e){
		if (senToggle)
			sen.hide();
		else
			sen.show();
		
		senToggle = !senToggle;
	}
	
	@UiHandler("HumButton")
	void Hum(ClickEvent e) {
		if (humToggle)
			hum.hide();
		else
			hum.show();
		
		humToggle = !humToggle;
	}
	
	

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
						
						final String[] newTextArray = result.split(" ", -1);
						for(Integer i = 0; i<20; i+=2) {
							System.out.println(result);
							System.out.println(newTextArray[15]);
							System.out.println(newTextArray[16]);
							System.out.println(Integer.parseInt((newTextArray[16+i] + newTextArray[15+i]),16));
							if(Integer.parseInt((newTextArray[16+i] + newTextArray[15+i]),16) < 1500 ) { //Humidity
								sumHum += Integer.parseInt((newTextArray[16+i] + newTextArray[15+i]),16);
								countHum++;
							}
							else if (Integer.parseInt((newTextArray[16+i] + newTextArray[15+i]),16) > 1500&& //MSP430 
									 Integer.parseInt((newTextArray[16+i] + newTextArray[15+i]),16) < 3500 ) {
								sumMSP += Integer.parseInt((newTextArray[16+i] + newTextArray[15+i]),16);
								countMSP++;
							}
							else if (Integer.parseInt((newTextArray[16+i] + newTextArray[15+i]),16) > 5500) { //Sensirian
								sumSen += Integer.parseInt((newTextArray[16+i] + newTextArray[15+i]),16);
								countSen++;
							}
							
							averageMSP = sumMSP/(countMSP * 1.0);
							averageSen = sumSen/(countSen * 1.0);
							averageHum = sumHum/(countHum * 1.0);
							
							temperatureMSP = ((((((averageMSP / 4096.0) * 1.5) - 0.986) / 0.00355) * 9.0) / 5.0) + 32.0;
							temperatureSen = (((-38.4 + (averageSen * 0.0098)) * 9.0) / 5.0) + 32.0;
							humidity = (-0.0000028 * averageHum * averageHum) + (0.0405 * averageHum - 4) ;
							
						}
						
						if (msp.getPoints().length > 15) {
							msp.addPoint(  
				                    new Date().getTime(),  
				                    temperatureMSP,  
				                    true, true, true  
				                );
						}
						else {
							msp.addPoint(  
				                    new Date().getTime(),  
				                    temperatureMSP,  
				                    true, false, true  
				                );
						}
						
						if (sen.getPoints().length > 15) {
							sen.addPoint(  
				                    new Date().getTime(),  
				                    temperatureSen,  
				                    true, true, true  
				                );
						}
						else {
							sen.addPoint(  
				                    new Date().getTime(),  
				                    temperatureSen,  
				                    true, false, true  
				                );
						}
						
						
						if (hum.getPoints().length > 15) {
							hum.addPoint(  
				                    new Date().getTime(),  
				                    humidity,  
				                    true, true, true  
				                );
						}
						else {
							hum.addPoint(  
				                    new Date().getTime(),  
				                    humidity,  
				                    true, false, true  
				                );
						}
							
				}
					
					@Override
					public void onFailure(Throwable caught) {
						System.out.println("FAIL");
						
					}
					
				});
				
			}
		};
		
		tempTimer.scheduleRepeating(1000);
	}
}
