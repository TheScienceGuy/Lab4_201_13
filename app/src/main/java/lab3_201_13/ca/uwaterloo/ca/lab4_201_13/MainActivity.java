package lab3_201_13.ca.uwaterloo.ca.lab4_201_13;

        import android.graphics.PointF;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.ContextMenu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.LinearLayout;

        import mapper.MapLoader;
        import mapper.MapView;


public class MainActivity extends AppCompatActivity {
    MapView map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout layout = (LinearLayout) findViewById(R.id.ll);
        layout.setOrientation(LinearLayout.VERTICAL);
        map = new MapView(getApplicationContext(),640, 600,25, 25);
        registerForContextMenu(map);
        try {
            map.setMap(MapLoader.loadMap(getExternalFilesDir(null), "E2-3344.svg"));
            layout.addView(map);
            map.setVisibility(View.VISIBLE);

        } catch ( NullPointerException e){
            Log.d("EXCEPTION CAUGHT", "NO MAP FILE");
        }
        Graph g=new Graph(map);
        g.setInitial(new PointF(80f,256f));
        g.setDesitination(new PointF(255f,128f));
        g.getPath();

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        map.onCreateContextMenu(menu, v, menuInfo); }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item) || map.onContextItemSelected(item); }
}
