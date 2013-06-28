package org.jaynestown.tragic7ball;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Resources;
import android.view.Menu;
import android.widget.TextView;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.lang.Runnable;
import java.util.Random;

public class DisplayMessageActivity extends Activity {

    public class DisplayMessageRunnable implements Runnable, SensorEventListener {
    	private String[] msgs;
    	private TextView tv;
    	private Random rnd;
    	
    	private SensorManager mSensorManager;
    	private Sensor mAccelerometer;
    	
    	private static final int SHAKE_THRESHOLD = 800;
    	
    	public DisplayMessageRunnable(String[] msgs, TextView tv, Random rnd) {
    		this.msgs = msgs;
    		this.tv = tv;
    		this.rnd = rnd;    		
    	}
    	
    	public void run() {
    		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    		
    		this.changeText();
    	  }
    	
    	
    	
    	public void changeText() {
    		int msgIdx = rnd.nextInt(msgs.length);
            tv.setText(msgs[msgIdx]);
    	}

		@Override
		public void onAccuracyChanged(Sensor arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSensorChanged(SensorEvent se) {
			if (se.sensor.getType() == se.sensor.TYPE_ACCELEROMETER) {
				/* long curTime = System.currentTimeMillis();
				long lastUpdate = 0;
			    // only allow one update every 100ms.
			    if ((curTime - lastUpdate) > 100) {
			      long diffTime = (curTime - lastUpdate);
			      lastUpdate = curTime;
			      
				float x, y, z, old_x = 0, old_y = 0, old_z = 0;
				x = se.values[0];
				y = se.values[1];
			    z = se.values[2];
			    
			    float speed = Math.abs(x + y + z – old_x – old_y – old_z) / diffTime * 10000;
	
			    if (speed > SHAKE_THRESHOLD) {
			    */
			    	this.changeText();
			    /*
			    }
			     
			    
			    old_x = x;
			    old_y = y;
			    old_z = z;
			    */
			}
		    
		    
			// this.changeText();
		}
	}


	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources res = getResources();
        String[] messages = res.getStringArray(R.array.messages);
        setContentView(R.layout.activity_display_message);
        TextView msgTextView = (TextView)findViewById(R.id.messageTextView);
        
        Random rg = new Random();
        DisplayMessageRunnable r = new DisplayMessageRunnable(messages, msgTextView, rg);
        // r.run();
        Thread thr = new Thread(r);
        thr.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.display_message, menu);
        return true;
    }



    
}
