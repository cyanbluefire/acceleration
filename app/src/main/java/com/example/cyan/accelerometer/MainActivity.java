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
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends Activity {

    public SensorManager mSensorManager;
    public Sensor mSensor;
    public static final  String TAG = "MainActivity";
    public static final double calibration = SensorManager.STANDARD_GRAVITY;
    private TextView tv_currentACC;
    private TextView tv_maxACC;
    private TextView tv_x;
    private TextView tv_y;
    private TextView tv_z;
    private float acceleration_x;
    private float acceleration_y;
    private float acceleration_z;

    public int left_down = 0;      //标识：向左下侧
    public int left_up = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_currentACC = (TextView)findViewById(R.id.currentAcceleration);
        tv_maxACC = (TextView)findViewById(R.id.maxAcceleration);
        tv_x = (TextView)findViewById(R.id.accelerationX);
        tv_y = (TextView)findViewById(R.id.accelerationY);
        tv_z = (TextView)findViewById(R.id.accelerationZ);

        mSensorManager= (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mSensor =  mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//      SensorEventListener listener;

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateGUI();
            }
        },0,100);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(listener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(listener);
        super.onPause();
    }

    protected SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            int sensorType = sensorEvent.sensor.getType();
            float[] values = sensorEvent.values;

            acceleration_x = values[0];
            acceleration_y = values[1];
            acceleration_z = values[2];

            Log.i(TAG,"x::"+acceleration_x+" y::"+acceleration_y+"z::"+acceleration_z);
//            Log.i(TAG,"Math.abs(x)"+Math.abs(x)+"Math.abs(y):" +Math.abs(y) + "Math.abs(z):" + Math.abs(z));
//            if(sensorType == Sensor.TYPE_ACCELEROMETER){
//                int value = 15; //阀值
//                if(acceleration_x >=value || acceleration_x <= -value || acceleration_y >= value || acceleration_y <= -value || acceleration_z >= value || acceleration_z <= -value){
//                    Log.e(TAG,"检测到摇动");
//                }
//            }
//            left_down = 0;      //标识：向左下侧
//            left_up = 0;
            if(acceleration_x >5 && acceleration_z < 9) {
                left_down = 1;
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        left_down = 0;
                    }
                }, 500);
                Log.e(TAG,"左 下 侧");
//                Toast.makeText(MainActivity.this,"左 下 侧",Toast.LENGTH_SHORT).show();
            }
            if(acceleration_x <-5 && acceleration_z < 8) {
                left_up = 1;
                new Timer().schedule(new TimerTask(){
                    @Override
                    public void run() {
                        left_up = 0;
                    }
                },500);
                Log.e(TAG,"左 上 侧");
//                Toast.makeText(MainActivity.this,"左 上 侧",Toast.LENGTH_SHORT).show();
            }


        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    private void updateGUI(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_x.setText(String.valueOf(acceleration_x));
                tv_x.invalidate();
                tv_y.setText(String.valueOf(acceleration_y));
                tv_y.invalidate();
                tv_z.setText(String.valueOf(acceleration_z));
                tv_z.invalidate();

            }
        });
    }

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
