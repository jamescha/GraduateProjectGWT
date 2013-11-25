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
	
	
	//Node 1
	Boolean mspToggle = true;
	Boolean senToggle = true;
	Boolean humToggle = true;
	
	//Node 2
	Boolean mspToggle2 = true;
	Boolean senToggle2 = true;
	Boolean humToggle2 = true;
	
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
			.setName("Node 1 MSP Temperature");
	
	final Series hum = chart.createSeries()
			.setName("Node 1 Humidity");
	
	final Series sen = chart.createSeries()
			.setName("Node 1 Sen Temperature");
	
	final Series msp2 = chart.createSeries()
			.setName("Node 2 MSP Temperature");
	
	final Series hum2 = chart.createSeries()
			.setName("Node 2 Humidity");
	
	final Series sen2 = chart.createSeries()
			.setName("Node 2 Sen Temperature");
	

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
		chart.addSeries(msp2);
		chart.addSeries(sen2);
		chart.addSeries(hum2);
		RootPanel.get().add(chart);
	}

	@UiField
	Button startButton;
	Button MSPButton;
	Button SenButton;
	Button HumButton;

	Button MSPButton2;
	Button SenButton2;
	Button HumButton2;
	
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
	
	//Node 2
	@UiHandler("MSPButton2")
	void MSP2(ClickEvent e){
		if (mspToggle2)
			msp2.hide();
		else
			msp2.show();
		
		mspToggle2 = !mspToggle2;
	}
	
	@UiHandler("SenButton2")
	void Sen2(ClickEvent e){
		if (senToggle2)
			sen2.hide();
		else
			sen2.show();
		
		senToggle2 = !senToggle2;
	}
	
	@UiHandler("HumButton2")
	void Hum2(ClickEvent e) {
		if (humToggle2)
			hum2.hide();
		else
			hum2.show();
		
		humToggle2 = !humToggle2;
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
						
						//Very Dirty Way. Making a Class would be cleaner.
						Integer countMSP2 = 0;
						Integer countSen2 = 0;
						Integer countHum2 = 0;
						Integer sumMSP2 = 0;
						Integer sumHum2 = 0;
						Integer sumSen2 = 0;
						Double averageMSP2 = 0.0;
						Double averageHum2 = 0.0;
						Double averageSen2 = 0.0;
						Double temperatureMSP2 = 0.0;
						Double temperatureSen2 = 0.0;
						Double humidity2 = 0.0;
						
						System.out.println(result);
						
						final String[] newTextArray = result.split(" ", -1);
						if (newTextArray[13].toString().contentEquals("01")) {
							for(Integer i = 0; i<20; i+=2) {
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
							
							//Node 1
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
						
						if (newTextArray[13].toString().contentEquals("02")) {
							for(Integer i = 0; i<20; i+=2) {
								if(Integer.parseInt((newTextArray[16+i] + newTextArray[15+i]),16) < 1500 ) { //Humidity
									sumHum2 += Integer.parseInt((newTextArray[16+i] + newTextArray[15+i]),16);
									countHum2++;
								}
								else if (Integer.parseInt((newTextArray[16+i] + newTextArray[15+i]),16) > 1500&& //MSP430 
										 Integer.parseInt((newTextArray[16+i] + newTextArray[15+i]),16) < 3500 ) {
									sumMSP2 += Integer.parseInt((newTextArray[16+i] + newTextArray[15+i]),16);
									countMSP2++;
								}
								else if (Integer.parseInt((newTextArray[16+i] + newTextArray[15+i]),16) > 5500) { //Sensirian
									sumSen2 += Integer.parseInt((newTextArray[16+i] + newTextArray[15+i]),16);
									countSen2++;
								}
								
								averageMSP2 = sumMSP2/(countMSP2 * 1.0);
								averageSen2 = sumSen2/(countSen2 * 1.0);
								averageHum2 = sumHum2/(countHum2* 1.0);
								
								temperatureMSP2 = ((((((averageMSP2 / 4096.0) * 1.5) - 0.986) / 0.00355) * 9.0) / 5.0) + 32.0;
								temperatureSen2 = (((-38.4 + (averageSen2 * 0.0098)) * 9.0) / 5.0) + 32.0;
								humidity2 = (-0.0000028 * averageHum2 * averageHum2) + (0.0405 * averageHum2 - 4) ;
							}
							
							//Node 2
							if (msp2.getPoints().length > 15) {
								msp2.addPoint(  
					                    new Date().getTime(),  
					                    temperatureMSP2,  
					                    true, true, true  
					                );
							}
							else {
								msp2.addPoint(  
					                    new Date().getTime(),  
					                    temperatureMSP2,  
					                    true, false, true  
					                );
							}
							
							if (sen2.getPoints().length > 15) {
								sen2.addPoint(  
					                    new Date().getTime(),  
					                    temperatureSen2,  
					                    true, true, true  
					                );
							}
							else {
								sen2.addPoint(  
					                    new Date().getTime(),  
					                    temperatureSen2,  
					                    true, false, true  
					                );
							}
							
							
							if (hum2.getPoints().length > 15) {
								hum2.addPoint(  
					                    new Date().getTime(),  
					                    humidity2,  
					                    true, true, true  
					                );
							}
							else {
								hum2.addPoint(  
					                    new Date().getTime(),  
					                    humidity2,  
					                    true, false, true  
					                );
							}
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
