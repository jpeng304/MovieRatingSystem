package com.cpsc411.app4;
/*
    App4 Movie Rating
    Jonathan Peng (jspeng@csu.fullerton.edu)
    Jason Han (jasonhan0426@csu.fullerton.edu)
    Peter Vu (eclipseraid@csu.fullerton.edu)
 */
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import com.google.tabmanager.TabManager;
import java.util.List;
public class MainActivity extends FragmentActivity {

    private DBHelper db;
    TabHost tabHost;
    TabManager tabManager;
    //Tab name
    private String recent = "Most Recent";
    private String highest = "Highest Rated";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get tab manager
        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();
        tabManager = new TabManager(this, tabHost, R.id.realtabcontent);
        TabHost.TabSpec tabSpec = tabHost.newTabSpec(recent);
        tabSpec.setIndicator(recent);
        tabManager.addTab(tabSpec,TaskListFragment.class,null);
        tabSpec = tabHost.newTabSpec(highest);
        tabSpec.setIndicator(highest);
        tabManager.addTab(tabSpec,TaskListFragment.class,null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_add:
                Intent movieUpdate = new Intent(this,MovieUpdate.class);
                startActivity(movieUpdate);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}