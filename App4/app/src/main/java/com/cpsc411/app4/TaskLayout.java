package com.cpsc411.app4;

/**
 * Created by Jonathan on 12/1/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * Created by jpeng on 12/1/2017.
 */

public class TaskLayout extends RelativeLayout {
    private RatingBar ratingBar;
    private TextView txName;
    private TextView txDate;

    private Task task;
    private Context context;

    public TaskLayout(Context context) {   // used by Android tools
        super(context);
    }
    public TaskLayout(Context context, Task t){
        super(context);
        this.context=context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_listview, this, true);

        //reference to widget
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        txName = (TextView) findViewById(R.id.txName);
        txDate = (TextView) findViewById(R.id.lbDate);

        //set task data on widget
        setTask(t);
    }
    public void setTask(Task t){
        task = t;
        txName.setText(task.getMovie_name());
        txDate.setText(task.getMovie_date());
        ratingBar.setRating(task.getMovie_rate());
    }
}
