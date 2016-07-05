package lab3_201_13.ca.uwaterloo.ca.lab4_201_13;

import mapper.MapView;
import mapper.NavigationalMap;
import mapper.PositionListener;

/**
 * Created by Matthew on 2016-07-05.
 */

        import java.util.ArrayList;
        import java.util.List;

        import android.graphics.PointF;
        import android.hardware.Sensor;
        import android.hardware.SensorEvent;
        import android.hardware.SensorEventListener;
        import android.hardware.SensorManager;
        import android.widget.TextView;


public class MyPositionListener implements SensorEventListener, PositionListener{

    float [] values;
    float x;
    float y;
    float z;
    float userx;
    float usery;
    float mx = 0;
    float my = 0;
    float mz = 0;
    float [] yarray = new float [29];
    float [] lowarray = new float [31];
    float [] R = new float [9];
    float [] Rvalues = new float [3];
    float azimuth;
    float[] gravity = new float[3];
    float[] geomagnetic = new float[3];
    //float lowpass = 0;
    int counter = 0;
    int stepcounter = 0;
    float stepNorth =0;
    float stepEast =0;
    float lowtrigger = 0.02f;
    float hightrigger = 0.05f;
    float fallcount = 0;
    float risecount = 0;
    float peakcount = 0;
    float C = 4F ;
    float points [] = new float[1];
    TextView output;
    TextView output1;
    LineGraphView graph;
    MapView map;
    NavigationalMap navmap;
    List <PointF> point ;
    PointF user;

    PointF a1 = new PointF(3,9);
    PointF a2 = new PointF(7,9);
    PointF a3 = new PointF(11,9);
    PointF a4 = new PointF(15,9);

    //assign local variables in constructor
    public MyPositionListener(TextView outputView, TextView counts,
                                            LineGraphView Graph, float [] gravity, float [] geomag, MapView map, NavigationalMap navmap) {
        output = outputView;
        output1 = counts;
        graph = Graph;
        //this.gravity = gravity;
        //this. geomagnetic = geomag;
        user= map.getUserPoint();
        userx = user.x;
        usery = user.y;
        this.map = map;
        this.navmap=navmap;

    }

    //resets the number of steps taken
    public void reset ()
    {
        stepcounter = 0;
        stepNorth = 0;
        stepEast = 0;
    }

    //get and set method used during debugging stage
    public float getMax()
    {
        return my;
    }

    public void setMax(float y)
    {
        my = y;
        mx = y;
        mz = y;
    }

    public void addnorth()
    {
        stepNorth ++;

        map.setUserPoint(userx,usery);
        PointF current = new PointF(userx,usery);
        PointF next = new PointF(userx,usery-0.1f);
        //PointF next = new PointF((float) (userx + (0.2* Math.sin(azimuth))),(float) (usery+(0.2* Math.cos(azimuth))));

        if (navmap.calculateIntersections(current, next).size() == 0)
        {
            stepNorth ++;
            usery = (float) (usery-(0.1));
            map.setUserPoint(userx,usery);
            populatepoints();

        }

    }

    public void addsouth()
    {

        PointF current = new PointF(userx,usery);
        PointF next = new PointF(userx,usery+0.1f);
        //PointF next = new PointF((float) (userx + (0.2* Math.sin(azimuth))),(float) (usery+(0.2* Math.cos(azimuth))));

        if (navmap.calculateIntersections(current, next).size() == 0)
        {
            stepNorth ++;
            usery = (float) (usery+(0.1));
            map.setUserPoint(userx,usery);
            populatepoints();

        }

    }

    public void addeast()
    {
        PointF current = new PointF(userx,usery);
        PointF next = new PointF(userx + 0.1f,usery);
        //PointF next = new PointF((float) (userx + (0.2* Math.round((Math.sin(azimuth))))),(float) (usery+(0.2* Math.round((Math.cos(azimuth))))));

        if (navmap.calculateIntersections(current, next).size() == 0)
        {
            stepEast ++;

            userx = (float) (userx+(0.1));
            map.setUserPoint(userx,usery);
            populatepoints();

        }
    }

    public void addwest()
    {
        PointF current = new PointF(userx,usery);
        PointF next = new PointF(userx - 0.1f,usery);
        //PointF next = new PointF((float) (userx + (0.2* Math.round((Math.sin(azimuth))))),(float) (usery+(0.2* Math.round((Math.cos(azimuth))))));

        if (navmap.calculateIntersections(current, next).size() == 0)
        {
            stepEast --;

            userx = (float) (userx-(0.1));
            map.setUserPoint(userx,usery);
            populatepoints();
        }
    }

    public void populatepoints()
    {
        PointF origin = map.getOriginPoint();
        user = map.getUserPoint();
        userx = user.x;
        usery = user.y;
        PointF dest = map.getDestinationPoint();
        boolean p1=false;
        boolean p2=false;
        boolean p3=false;
        boolean p4=false;
//		PointF second = new PointF(15.0f,10.0f);
//		PointF origin = new PointF(8.0f,9.0f);
        point = new ArrayList<PointF>();
//		if(navmap.calculateIntersections(origin, second).size() == 0)
//		{
//		point.add(origin);
//		point.add(second);
//		map.setUserPath(point);
//		}
        if(navmap.calculateIntersections(user, dest).size() == 0)
        {
            point.add(user);
            point.add(dest);
            map.setUserPath(point);
        }

        else
        {if (navmap.calculateIntersections(a1, dest).size() == 0)
        {
            p1= true;
        }
            if (navmap.calculateIntersections(a2, dest).size() == 0)
            {
                p2= true;
            }
            if (navmap.calculateIntersections(a3, dest).size() == 0)
            {
                p3= true;
            }
            if (navmap.calculateIntersections(a4, dest).size() == 0)
            {
                p4= true;
            }
            if (navmap.calculateIntersections(a1, user).size() == 0)
            {
                if(navmap.calculateIntersections(a1, dest).size() == 0)
                {
                    point.add(user);
                    point.add(a1);
                    point.add(dest);
                    map.setUserPath(point);
                }
                else if(p2)
                {
                    point.add(user);
                    point.add(a1);
                    point.add(a2);
                    point.add(dest);
                    map.setUserPath(point);
                }
                else if(p3)
                {
                    point.add(user);
                    point.add(a1);
                    point.add(a3);
                    point.add(dest);
                    map.setUserPath(point);
                }
                else if(p4)
                {
                    point.add(user);
                    point.add(a1);
                    point.add(a4);
                    point.add(dest);
                    map.setUserPath(point);
                }
            }
            else if (navmap.calculateIntersections(a2, user).size() == 0)
            {
                if(navmap.calculateIntersections(a2, dest).size() == 0)
                {
                    point.add(user);
                    point.add(a2);
                    point.add(dest);
                    map.setUserPath(point);
                }
                else if(p1)
                {
                    point.add(user);
                    point.add(a2);
                    point.add(a1);
                    point.add(dest);
                    map.setUserPath(point);
                }
                else if(p3)
                {
                    point.add(user);
                    point.add(a2);
                    point.add(a3);
                    point.add(dest);
                    map.setUserPath(point);
                }
                else if(p4)
                {
                    point.add(user);
                    point.add(a2);
                    point.add(a4);
                    point.add(dest);
                    map.setUserPath(point);
                }
            }
            else if (navmap.calculateIntersections(a3, user).size() == 0)
            {
                if(navmap.calculateIntersections(a3, dest).size() == 0)
                {
                    point.add(user);
                    point.add(a3);
                    point.add(dest);
                    map.setUserPath(point);
                }
                else if(p1)
                {
                    point.add(user);
                    point.add(a3);
                    point.add(a1);
                    point.add(dest);
                    map.setUserPath(point);
                }
                else if(p2)
                {
                    point.add(user);
                    point.add(a3);
                    point.add(a2);
                    point.add(dest);
                    map.setUserPath(point);
                }
                else if(p4)
                {
                    point.add(user);
                    point.add(a3);
                    point.add(a4);
                    point.add(dest);
                    map.setUserPath(point);
                }
            }
            else if (navmap.calculateIntersections(a4, user).size() == 0)
            {
                if(navmap.calculateIntersections(a4, dest).size() == 0)
                {
                    point.add(user);
                    point.add(a4);
                    point.add(dest);
                    map.setUserPath(point);
                }
                else if(p1)
                {
                    point.add(user);
                    point.add(a4);
                    point.add(a1);
                    point.add(dest);
                    map.setUserPath(point);
                }
                else if(p2)
                {
                    point.add(user);
                    point.add(a4);
                    point.add(a2);
                    point.add(dest);
                    map.setUserPath(point);
                }
                else if(p3)
                {
                    point.add(user);
                    point.add(a4);
                    point.add(a3);
                    point.add(dest);
                    map.setUserPath(point);
                }
            }

        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }
    @Override
    public void originChanged(MapView source, PointF loc) {
        source.setUserPoint(loc);


    }



    @Override
    public void destinationChanged(MapView source, PointF dest)
    {
        source.setDestinationPoint(dest);

    }



    }
