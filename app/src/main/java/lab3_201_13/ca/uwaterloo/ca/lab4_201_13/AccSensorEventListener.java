package lab3_201_13.ca.uwaterloo.ca.lab4_201_13;

import android.hardware.SensorEventListener;
import android.widget.TextView;
import java.io.FileOutputStream;

import java.io.IOException;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;



/**
 * Created by Matthew on 2016-06-24.
 */
public class AccSensorEventListener implements SensorEventListener {


    TextView output;
    TextView stepView;
    LineGraphView graph;
    private OrientationUpdater OrientationUpdater;
    private boolean acceTooGreat = false;
    private int state;
    private int step = 0;
    private long totalTime;
    private float distanceN = 0;
    private float distanceE = 0;
    private float direction = 0;
    private float[] FilteredAcc;


    public AccSensorEventListener( TextView outputView, TextView stepView,LineGraphView graph , OrientationUpdater OrientationUpdater )
    {


        this.output = outputView;

        this.OrientationUpdater = OrientationUpdater;
        this.stepView = stepView;
        this.graph = graph;
        this.state = 0;
    }


    public void clear()
    {
        distanceN = 0;
        distanceE = 0;
        step=0;
    }

    public void onAccuracyChanged(Sensor s, int i) {}

    public float[] lowPassFilter(float[] in, float[] out)
    {
        float a = 0.25f;
        if (out == null ) return in;
        for ( int i = 0; i <in.length; i++ ) {
            out[i] = out[i] + a *(in[i] - out[i]);
        }
        return out;
    }
    public void onSensorChanged(SensorEvent se) {
        FilteredAcc = lowPassFilter(se.values.clone(), FilteredAcc);
        se.values[0] = FilteredAcc[0];
        se.values[1] = FilteredAcc[1];
        se.values[2] = FilteredAcc[2];

        direction=OrientationUpdater.getAzimuth();
        graph.addPoint(FilteredAcc);
        switch (state)
        {
            case 0:

                if ( (FilteredAcc[2] > 0.35) && (Math.abs(FilteredAcc[1]) > 0.1f)) {
                    state = 1;
                    totalTime = System.currentTimeMillis();
                }
                break;
            case 1:

                if ( FilteredAcc[2] < 0.35 )
                    state = 0;
                else if ( (FilteredAcc[2] > 1.3f && FilteredAcc[2] < 7) && (Math.abs(FilteredAcc[1]) > 0.35f )) {
                    state = 2;
                }
                break;
            case 2:
                if ( FilteredAcc[2] > 8.0f){
                    acceTooGreat = true;
                }
                else if ( FilteredAcc[2] < 1.3f) {
                    state = 3;
                }
                break;
            case 3:
                if  ( FilteredAcc[2] > 1.3f){
                    state = 2;
                } else if ( FilteredAcc[2] < -0.25f ){
                    state = 4;
                }
                break;
            case 4:
                if ( FilteredAcc[2] > -0.2f) {
                    totalTime = System.currentTimeMillis() - totalTime;
                    if ( totalTime > 200 && !acceTooGreat){
                        step++;
                        distanceN += Math.cos((double)direction);
                        distanceE += Math.sin((double)direction);
                    }
                    state = 0;

                    acceTooGreat = false;

                }
                break;
        }

        stepView.setText(String.format("Step Count: %d" , step));


        output.setText(String.format("\nDisplacement: \n N: %.5f E: %.5f\ndirection: %f\n" +
                " x: %.2f y: %.2f z: %.2f",  distanceN , distanceE,direction*(180f/3.14159f),FilteredAcc[0], FilteredAcc[1], FilteredAcc[2]) );
    }
}
