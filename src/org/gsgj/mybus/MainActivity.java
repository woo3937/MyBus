package org.gsgj.mybus;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	private String html = "";
	private TextView textView;
	private GetBusInfoThread getBusInfoThread;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			html = msg.getData().getString("str");
			textView.setText(html);
		}
	};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getBusInfoThread = new GetBusInfoThread(mHandler);
        getBusInfoThread.setDaemon(true);
        getBusInfoThread.start();
        
        textView = (TextView)findViewById(R.id.html);
        textView.setText(html);
        
    }

    /*
    private String downloadURL(String addr) {
		String doc = "";
		
		URL url;
		try {
			url = new URL(addr);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			
			if (con != null) {
				con.setConnectTimeout(10000);
				con.setUseCaches(false);
				
				if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
					BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
					for (;;) {
						String line = br.readLine();
						if (line == null) {
							break;
						}
						doc += line + "\n";
					}
					br.close();
				}
				con.disconnect();
			}
		} catch (Exception e) {
			Toast.makeText(this, "parsing error!", Toast.LENGTH_SHORT);
		}
		
		return doc;
	}
	*/

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
