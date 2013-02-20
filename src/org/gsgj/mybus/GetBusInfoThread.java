package org.gsgj.mybus;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class GetBusInfoThread extends Thread {
	private static final String address = "http://m.gbis.go.kr/jsp/searchList.jsp?mode=isearch&searchKeyValue=22219"; 
	
	private Handler mHandler;
	private String busStopName;
	private String busNumber;
	private String busDestination;
	private String estimatedTime;
	
	public GetBusInfoThread(Handler handler) {
		this.mHandler = handler;
	}
	
	@Override
	public void run() {
		super.run();
		
		try {
			while(true) {
				URL url;
				url = new URL(address);
			    InputStream is = url.openStream();
				Source source = new Source(new InputStreamReader(is, "UTF-8"));
				
				busStopName = getBusStopName(source);
				busNumber = getBusNumber(source);
				busDestination = getBusDestination(source);
				estimatedTime = getEstimatedTime(source);
				
				Bundle bundle = new Bundle();
				bundle.putString("busStopName", busStopName);
				bundle.putString("busNumber", busNumber);
				bundle.putString("busDestination", busDestination);
				bundle.putString("estimatedTime", estimatedTime);
				
				Message msg = new Message();
				msg.setData(bundle);
				
				mHandler.sendMessage(msg);
				
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getEstimatedTime(Source source) {
		Element table = (Element)source.getAllElements(HTMLElementName.TABLE).get(1);
		Element tr = (Element)table.getAllElements(HTMLElementName.TR).get(0);
		Element td = (Element)tr.getAllElements(HTMLElementName.TD).get(0);
		Element span = (Element)td.getAllElements(HTMLElementName.SPAN).get(0);
		
		return span.getContent().toString();
	}

	private String getBusDestination(Source source) {
		Element table = (Element)source.getAllElements(HTMLElementName.TABLE).get(1);
		Element tr = (Element)table.getAllElements(HTMLElementName.TR).get(0);
		Element td = (Element)tr.getAllElements(HTMLElementName.TD).get(0);
		Element em = (Element)td.getAllElements(HTMLElementName.EM).get(0);
		
		return em.getContent().toString();
	}

	private String getBusNumber(Source source) {
		Element table = (Element)source.getAllElements(HTMLElementName.TABLE).get(1);
		Element tr = (Element)table.getAllElements(HTMLElementName.TR).get(0);
		Element td = (Element)tr.getAllElements(HTMLElementName.TD).get(0);
		Element strong = (Element)td.getAllElements(HTMLElementName.STRONG).get(0);
		
		return strong.getContent().toString();
	}

	private String getBusStopName(Source source) {
		Element table = (Element)source.getAllElements(HTMLElementName.TABLE).get(0);
		Element tr = (Element)table.getAllElements(HTMLElementName.TR).get(0);
		Element td = (Element)tr.getAllElements(HTMLElementName.TD).get(0);
		Element span = (Element)td.getAllElements(HTMLElementName.SPAN).get(0);
		
		return span.getContent().toString();
	}
}
