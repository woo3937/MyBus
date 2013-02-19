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

public class GetBusInfoThread extends Thread {
	private Handler mHandler;
	private String busStopName;
	private String busNumber;
	private String busDestination;
	private String beforeBusStopCount;
	private String estimatedTime;
	
	public GetBusInfoThread(Handler handler) {
		this.mHandler = handler;
	}
	
	@Override
	public void run() {
		super.run();
		
		try {
			while(true) {
				Thread.sleep(1000);
				
				URL url;
				url = new URL("http://m.gbis.go.kr/jsp/searchList.jsp?mode=isearch&searchKeyValue=22219");
			    InputStream is = url.openStream();
				Source source = new Source(new InputStreamReader(is, "UTF-8"));
				
				busStopName = getBusStopName(source);
				busNumber = getBusNumber(source);
				busDestination = getBusDestination(source);
				//beforeBusStopCount
				//estimatedTime
				
				Bundle bundle = new Bundle();
				bundle.putString("busStopName", busStopName);
				bundle.putString("busNumber", busNumber);
				bundle.putString("busDestination", busDestination);
				Message msg = new Message();
				msg.setData(bundle);
				mHandler.sendMessage(msg);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
