package org.gsgj.mybus;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ShowToast") public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        String url = "http://m.gbis.go.kr/jsp/searchList.jsp?mode=isearch&searchKeyValue=22219";
        String html = downloadURL(url);
        
        TextView textView = (TextView)findViewById(R.id.html);
        textView.setText(html);
        
    }


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


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
