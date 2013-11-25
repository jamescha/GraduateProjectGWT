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
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	
	final Chart chart = new Chart()  
    .setType(Series.Type.SPLINE)  
    .setMarginRight(10)  
    .setChartTitleText("Live random data")  
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
	
	final Series series = chart.createSeries()
			.setName("Random data");
	

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
							System.out.println("SUCCESS");
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
