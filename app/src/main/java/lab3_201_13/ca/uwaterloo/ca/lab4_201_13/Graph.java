package lab3_201_13.ca.uwaterloo.ca.lab4_201_13;

import android.graphics.PointF;
import android.util.Log;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import mapper.MapView;

/**
 * Created by ywt on 7/2/16.
 */
public class Graph {

    public MapView map;
    LinkedList<Vertex> V = new LinkedList<>();
    float[][] edge;
    PointF destination;
    int start;
    PointF scale;
    PointF initial;
    LinkedList<Edge> E = new LinkedList<>();
    LinkedList<LinkedList<Integer>> adj=new LinkedList<>();
    public Graph(MapView map){
        this.map=map;
        scale=new PointF(640,600);
        Log.d("w and h:", String.format("%f,%f",scale.x,scale.y));
        for(int i=0;i<scale.x;i+=55){
            for(int j=0;j<scale.y;j+=55){
                V.add(new Vertex(new PointF(i,j)));
            }
        }
        edge=new float[V.size()][V.size()];
        for(int i=0;i<V.size();i++){
            for(int j=0;j<V.size();j++){
                if(map.map.calculateIntersections(V.get(i).p,V.get(j).p).isEmpty()){
                    E.add(new Edge(V.get(i),V.get(j)));

                    edge[i][j]=PointF.length(V.get(j).p.x-V.get(i).p.x,V.get(j).p.y-V.get(i).p.y);

                }
                else{
                    edge[i][j]=Float.POSITIVE_INFINITY;
                }
            }
        }








        /*edge=new float[34][34];
        V.add((new Vertex(new PointF(80,392))));//0
        V.add((new Vertex(new PointF(80,372))));//1
        V.add((new Vertex(new PointF(125,372))));//2
        V.add((new Vertex(new PointF(125,320))));//3
        V.add((new Vertex(new PointF(125,268))));//4
        V.add((new Vertex(new PointF(125,206))));//5
        V.add((new Vertex(new PointF(125,154))));//6
        V.add((new Vertex(new PointF(125,102))));//7
        V.add((new Vertex(new PointF(125,50))));//8
        V.add((new Vertex(new PointF(180,372))));//9
        V.add((new Vertex(new PointF(247,372))));//10
        V.add((new Vertex(new PointF(247,320))));//11
        V.add((new Vertex(new PointF(247,268))));//12
        V.add((new Vertex(new PointF(247,206))));
        V.add((new Vertex(new PointF(247,154))));
        V.add((new Vertex(new PointF(247,102))));
        V.add((new Vertex(new PointF(247,50))));
        V.add((new Vertex(new PointF(314,372))));
        V.add((new Vertex(new PointF(381,372))));
        V.add((new Vertex(new PointF(418,372))));
        V.add((new Vertex(new PointF(381,320))));
        V.add((new Vertex(new PointF(418,320))));
        V.add((new Vertex(new PointF(381,268))));
        V.add((new Vertex(new PointF(418,268))));
        V.add((new Vertex(new PointF(381,206))));
        V.add((new Vertex(new PointF(418,206))));
        V.add((new Vertex(new PointF(381,154))));
        V.add((new Vertex(new PointF(418,154))));
        V.add((new Vertex(new PointF(381,102))));
        V.add((new Vertex(new PointF(418,102))));//30
        V.add((new Vertex(new PointF(381,50))));
        V.add((new Vertex(new PointF(418,410))));
        V.add((new Vertex(new PointF(468,102))));
        V.add((new Vertex(new PointF(468,392))));

        for(Vertex v:V){
            String s=String.format("%d: %f, %f ",V.indexOf(v),v.p.x,v.p.y);
            Log.d("\nPoint:", s);

        }
        //initialize edges to form adj list
        for(int i=0;i<34;i++){
            for(int j=0;j<34;j++){
                edge[i][j]=Float.POSITIVE_INFINITY;
            }
        }
        for (int i=18;i<30;i++){
            for(int j=i+1;j<30;j++){
                E.add(new Edge(V.get(i),V.get(j)));
                E.add(new Edge(V.get(j),V.get(i)));
                edge[i][j]=PointF.length(V.get(j).p.x-V.get(i).p.x,V.get(j).p.y-V.get(i).p.y);
                edge[j][i]=PointF.length(V.get(j).p.x-V.get(i).p.x,V.get(j).p.y-V.get(i).p.y);
            }
        }
        String s=String.format("%d",E.size());
        Log.d("", s);
        for(int i=2;i<8;i++){
            E.add(new Edge(V.get(i),V.get(i+1)));
            E.add(new Edge(V.get(i+1),V.get(i)));
            edge[i][i+1]=PointF.length(V.get(i).p.x-V.get(i).p.x,V.get(i+1).p.y-V.get(i).p.y);
            edge[i+1][i]=PointF.length(V.get(i).p.x-V.get(i).p.x,V.get(i+1).p.y-V.get(i).p.y);
        }
        for(int i=10;i<16;i++){
            E.add(new Edge(V.get(i),V.get(i+1)));
            E.add(new Edge(V.get(i+1),V.get(i)));
            edge[i][i+1]=PointF.length(V.get(i).p.x-V.get(i).p.x,V.get(i+1).p.y-V.get(i).p.y);
            edge[i+1][i]=PointF.length(V.get(i).p.x-V.get(i).p.x,V.get(i+1).p.y-V.get(i).p.y);
        }
        s=String.format("%d",E.size());
        Log.d("", s);
        E.add(new Edge(V.get(0),V.get(1)));
        E.add(new Edge(V.get(1),V.get(0)));
        edge[0][1]=PointF.length(V.get(1).p.x-V.get(0).p.x,V.get(1).p.y-V.get(0).p.y);
        edge[1][0]=PointF.length(V.get(1).p.x-V.get(0).p.x,V.get(1).p.y-V.get(0).p.y);
        E.add(new Edge(V.get(2),V.get(1)));
        E.add(new Edge(V.get(1),V.get(2)));
        edge[2][1]=PointF.length(V.get(2).p.x-V.get(1).p.x,V.get(2).p.y-V.get(1).p.y);
        edge[1][2]=PointF.length(V.get(2).p.x-V.get(1).p.x,V.get(2).p.y-V.get(1).p.y);

        E.add(new Edge(V.get(9),V.get(2)));
        E.add(new Edge(V.get(2),V.get(9)));
        edge[9][2]=PointF.length(V.get(2).p.x-V.get(9).p.x,V.get(2).p.y-V.get(9).p.y);
        edge[2][9]=PointF.length(V.get(2).p.x-V.get(9).p.x,V.get(2).p.y-V.get(9).p.y);

        E.add(new Edge(V.get(10),V.get(9)));
        E.add(new Edge(V.get(9),V.get(10)));
        edge[10][9]=PointF.length(V.get(10).p.x-V.get(9).p.x,V.get(10).p.y-V.get(9).p.y);
        edge[9][10]=PointF.length(V.get(10).p.x-V.get(9).p.x,V.get(10).p.y-V.get(9).p.y);

        E.add(new Edge(V.get(17),V.get(10)));
        E.add(new Edge(V.get(10),V.get(17)));
        edge[10][17]=PointF.length(V.get(17).p.x-V.get(10).p.x,V.get(17).p.y-V.get(10).p.y);
        edge[17][10]=PointF.length(V.get(17).p.x-V.get(10).p.x,V.get(17).p.y-V.get(10).p.y);

        E.add(new Edge(V.get(18),V.get(17)));
        E.add(new Edge(V.get(17),V.get(18)));
        edge[18][17]=PointF.length(V.get(17).p.x-V.get(18).p.x,V.get(17).p.y-V.get(18).p.y);
        edge[17][18]=PointF.length(V.get(17).p.x-V.get(18).p.x,V.get(17).p.y-V.get(18).p.y);

        E.add(new Edge(V.get(19),V.get(31)));
        E.add(new Edge(V.get(31),V.get(19)));
        edge[19][31]=PointF.length(V.get(19).p.x-V.get(31).p.x,V.get(19).p.y-V.get(31).p.y);
        edge[31][19]=PointF.length(V.get(19).p.x-V.get(31).p.x,V.get(19).p.y-V.get(31).p.y);

        E.add(new Edge(V.get(33),V.get(19)));
        E.add(new Edge(V.get(19),V.get(33)));
        edge[33][19]=PointF.length(V.get(19).p.x-V.get(33).p.x,V.get(19).p.y-V.get(33).p.y);
        edge[19][33]=PointF.length(V.get(19).p.x-V.get(33).p.x,V.get(19).p.y-V.get(33).p.y);

        E.add(new Edge(V.get(32),V.get(29)));
        E.add(new Edge(V.get(29),V.get(32)));
        edge[32][29]=PointF.length(V.get(29).p.x-V.get(32).p.x,V.get(29).p.y-V.get(32).p.y);
        edge[29][32]=PointF.length(V.get(29).p.x-V.get(32).p.x,V.get(29).p.y-V.get(32).p.y);

        E.add(new Edge(V.get(28),V.get(30)));
        E.add(new Edge(V.get(30),V.get(28)));
        edge[28][30]=PointF.length(V.get(30).p.x-V.get(28).p.x,V.get(30).p.y-V.get(28).p.y);
        edge[30][28]=PointF.length(V.get(30).p.x-V.get(28).p.x,V.get(30).p.y-V.get(28).p.y);

        */
        //form adj list
        for(int i=0;i<V.size();i++){
            adj.add(new LinkedList<Integer>());
            Log.d("", adj.toString());
            for(int j=0;j<V.size();j++){
                if(edge[i][j]!=Float.POSITIVE_INFINITY){
                    adj.get(i).add(j);
                }
            }
        }


        for(int i=0;i<V.size();i++){
            String s=String.format("adj of %d:",i);
            s+=adj.get(i).toString();
            //s=String.format("point: %f, %f\npoint:%f, %f",e.e.get(0).p.x,e.e.get(0).p.y,e.e.get(1).p.x,e.e.get(1).p.y);
            Log.d("ADJ\n", s);
        }
        //s=String.format("%d",E.size());
        //Log.d("", s);

        //find starting vertex




    }
    public  void setInitial(PointF p){
        initial=p;

        float d=Float.POSITIVE_INFINITY;
        for(Vertex v:V){
            float x=PointF.length(v.p.x-initial.x,v.p.y-initial.y);
            if(x<d){
                start=V.indexOf(v);
            }
        }
        dijkstra(start);

    }
    private void initializeSingleSourse(int s){
        for(Vertex v:V){
            v.d=10000f;
            v.pi=null;

        }
        V.get(s).d=0;
    }
    private void relax(int u, int v){
        if (V.get(v).d>V.get(u).d+edge[u][v]){
            V.get(v).d=V.get(u).d+edge[u][v];
            V.get(v).pi=V.get(u);
        }


    }
    public void dijkstra(int s){
        initializeSingleSourse(s);

        LinkedList<Vertex> S=new LinkedList<>();
        LinkedList<Vertex> Q=(LinkedList<Vertex>) V.clone();

        while(!Q.isEmpty()){
            Collections.sort(Q, new Comparator<Vertex>() {
                @Override
                public int compare(Vertex v1, Vertex v2) {
                    if(v1.d>v2.d){
                        return 1;
                    }
                    else if(v1.d<v2.d){
                        return -1;
                    }
                    else {
                        return 0;
                    }
                }
            });
            String out=String.format(Q.toString());
            Log.d("Queue:", out);
            Vertex u=Q.pollFirst();

            S.add(u);
            for(int i:adj.get(V.indexOf(u))){
                relax(V.indexOf(u),i);
            }
            out=String.format("Vertex %d, pi:%d, d: %f", V.indexOf(u),V.indexOf(u.pi),u.d);
            Log.d("Dijkstra", out);

        }

    }
    public  void setDesitination(PointF p){
        destination=p;
    }
    LinkedList<PointF> getPath(){
        if(initial!=null){
            int end = 0;
            float d=Float.POSITIVE_INFINITY;
            for(Vertex v:V){
                float x=PointF.length(v.p.x-initial.x,v.p.y-initial.y);
                if(x<d){
                    end=V.indexOf(v);
                    d=x;
                }
            }
            Vertex endVertex=V.get(end);
            Vertex iterator=endVertex;
            LinkedList<PointF> path=new LinkedList<>();
            path.add(destination);
            while(iterator.pi!=null){
                path.add(iterator.pi.p);
                iterator=iterator.pi;
            }
            path.add(initial);
            String out=path.toString();
            Log.d("path: ", out);
            return path;


        }
        else{
            return null;
        }
    }
}
class Edge{
    LinkedList<Vertex> e=new LinkedList<>();
    float weight;
    public  Edge(Vertex x, Vertex y){
        e.add(x);
        e.add(y);
        weight=PointF.length(x.p.x-y.p.x,x.p.y-y.p.y);

    }
}
class Vertex{
    PointF p;
    Vertex pi;
    float d;
    public Vertex(PointF p){
        this.p=p;
    }
}

