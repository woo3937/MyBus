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
				
				Element table = (Element)source.getAllElements(HTMLElementName.TABLE).get(0);
				Element tr = (Element)table.getAllElements(HTMLElementName.TR).get(0);
				Element td = (Element)tr.getAllElements(HTMLElementName.TD).get(0);
				Element span = (Element)td.getAllElements(HTMLElementName.SPAN).get(0);
				String str = span.getContent().toString();
				
				Bundle bundle = new Bundle();
				bundle.putString("str", str);
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
}
