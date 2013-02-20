package org.gsgj.mybus;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class MainActivity extends Activity {
	private String html = "";
	private TextView textView;
	private GetBusInfoThread getBusInfoThread;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			html = msg.getData().getString("busStopName") + " ";
			html += msg.getData().getString("busNumber") + " ";
			html += msg.getData().getString("busDestination") + " ";
			html += msg.getData().getString("estimatedTime");
			textView.setText(html);
		}
	};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.parsingText);
        
        DisplayUtil.NANUM_GOTHIC = Typeface.createFromAsset(getAssets(), "fonts/NanumGothic.ttf");
		DisplayUtil.NANUM_GOTHIC_BOLD = Typeface.createFromAsset(getAssets(), "fonts/NanumGothicBold.ttf");
		DisplayUtil.NANUM_GOTHIC_EXTRABOLD = Typeface.createFromAsset(getAssets(), "fonts/NanumGothicExtraBold.ttf");
    }
    
    
    
	@Override
	protected void onPause() {
		super.onPause();
		
		if (getBusInfoThread != null) {
			getBusInfoThread.interrupt();
		}
	}



	@Override
	protected void onResume() {
		super.onResume();
		
		 getBusInfoThread = new GetBusInfoThread(mHandler);
	        getBusInfoThread.setDaemon(true);
	        getBusInfoThread.start(); 
	}    
}
