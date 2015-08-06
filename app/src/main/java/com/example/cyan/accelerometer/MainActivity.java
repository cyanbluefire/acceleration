package com.example.cyan.accelerometer;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

    public SensorManager mSensorManager;
    public Sensor mSensor;
    public static final  String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager= (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mSensor =  mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        SensorEventListener listener;
        mSensorManager.registerListener(listener,mSensor,SensorManager.SENSOR_DELAY_NORMAL);

    }
    protected SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            int sensorType = sensorEvent.sensor.getType();
            float[] values = sensorEvent.values;

            float x = values[0];
            float y = values[1];
            float z = values[2];

            Log.i(TAG,"x::"+x+" y::"+y+"z::"+z);
            Log.i(TAG,"Math.abs(x)"+Math.abs(x)+"Math.abs(y):" +Math.abs(y) + "Math.abs(z):" + Math.abs(z));
            if(sensorType == Sensor.TYPE_ACCELEROMETER){
                int value = 15; //阀值
                if(x >=value || x <= -value || y >= value || y <= -value || z >= value || z <= -value){
                    Log.e(TAG,"检测到摇动");
                }
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
