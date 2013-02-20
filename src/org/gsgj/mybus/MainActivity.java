package org.gsgj.mybus;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView busStopName;
	private TextView busNumber;
	private TextView busLoc;
	private TextView estimatedTime;
	private GetBusInfoThread getBusInfoThread;
	
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			busStopName.setText(msg.getData().getString("busStopName"));
			busNumber.setText(msg.getData().getString("busNumber"));
			busLoc.setText(msg.getData().getString("busDestination"));
			estimatedTime.setText(msg.getData().getString("estimatedTime"));
		}
	};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        busStopName = (TextView)findViewById(R.id.busStopName);
        busNumber = (TextView)findViewById(R.id.busNumber);
        busLoc = (TextView)findViewById(R.id.busLoc);
        estimatedTime = (TextView)findViewById(R.id.estimatedTime);

        DisplayUtil.NANUM_GOTHIC = Typeface.createFromAsset(getAssets(), "fonts/NanumGothic.ttf");
		DisplayUtil.NANUM_GOTHIC_BOLD = Typeface.createFromAsset(getAssets(), "fonts/NanumGothicBold.ttf");
		DisplayUtil.NANUM_GOTHIC_EXTRABOLD = Typeface.createFromAsset(getAssets(), "fonts/NanumGothicExtraBold.ttf");
		
		setFontConfiguration(busStopName, DisplayUtil.NANUM_GOTHIC_EXTRABOLD, 40);
		setFontConfiguration(busNumber, DisplayUtil.NANUM_GOTHIC_BOLD, 50);
		setFontConfiguration(busLoc, DisplayUtil.NANUM_GOTHIC, 20);
		setFontConfiguration(estimatedTime, DisplayUtil.NANUM_GOTHIC, 40);
		
    }
  
    private void setFontConfiguration(TextView textView, Typeface typeface,int fontsize) {
    	textView.setTypeface(typeface);
    	textView.setTextSize(fontsize);    	
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
