package com.cpsc411.app4;

/**
 * Created by Jonathan on 12/1/2017.
 */
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;


public class TaskListFragment extends Fragment {
    private String currentTabTag;
    private ListView taskListView;
    private List<Task> myTaskList;
    private DBHelper db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_list,
                container, false);

        // get references to widgets
        taskListView = (ListView) view.findViewById (R.id.taskListView);

        // get the current tab
        TabHost tabHost = (TabHost) container.getParent().getParent();
        currentTabTag = tabHost.getCurrentTabTag();
        // refresh the task list view
        refreshTaskList();

        // return the view
        return view;
    }

    public void refreshTaskList() {
        // get task list for current tab from database

        final Context context = getActivity().getApplicationContext();
        db = new DBHelper(context);
        //Check if the database exist in the path
        /*
        File database = getContext().getDatabasePath(DBHelper.DB_NAME);
        if(database.exists()){
            db.getReadableDatabase();
            if(copyDatabase(context)){

                db.getReadableDatabase();
                if(copyDatabase(context)){
                    Toast.makeText(context, "Copy database success", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(context, "Copy data error",Toast.LENGTH_LONG).show();
                    return;

                }
            }
        }*/
        //See which tab the UI is showing and call functions to either get the list as
        // rating or date
        if(currentTabTag.equals("Highest Rated"))
            myTaskList = db.getMoviebyRating();
        else
            myTaskList = db.getMoviebyDate();

        //create adapter and set it in the ListView widget
        TaskAdapter adapter = new TaskAdapter(context,myTaskList);
        taskListView.setAdapter(adapter);
        //When the item in the list is pressed it would pass the list and the item number to
        //MovieUpdate class
        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {

                String MovieName = myTaskList.get(myItemInt).getMovie_name();
                String MovieDate = myTaskList.get(myItemInt).getMovie_date();
                float MovieRate = myTaskList.get(myItemInt).getMovie_rate();

                Intent movieUpdate = new Intent(context,MovieUpdate.class);
                movieUpdate.putExtra("moviename",MovieName);
                movieUpdate.putExtra("moviedate", MovieDate);
                movieUpdate.putExtra("movierate", MovieRate);
                startActivity(movieUpdate);
            }
        });

    }

    /*
    private boolean copyDatabase(Context context){
        try{
            InputStream inputStream = context.getAssets().open(DBHelper.DB_NAME);
            String outFileName = DBHelper.DBLOCATION + DBHelper.DB_NAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[]buff = new byte[1024];
            int length = 0;
            while((length=inputStream.read(buff))>0){
                outputStream.write(buff,0,length);
            }
            outputStream.flush();
            outputStream.close();
            Log.v("MainActivity", "DB copied" );
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }*/
}