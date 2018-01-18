package com.cpsc411.app4;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Jonathan on 12/2/2017.
 */

public class MovieUpdate extends AppCompatActivity implements View.OnClickListener {

    private DBHelper db;
    private EditText etName;
    private DatePicker datePicker;
    private RatingBar movieRate;
    private CheckBox cbLater;
    private String mName;
    private String mDate;
    private float mRate = 1.5f;
    private String dateYear;
    private String dateMonth;
    private String dateDay;
    private String saved_name;
    //check to see if it is add or update
    private int add_or_update=0;
    //See if checkbox is check or not
    private int checked =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit);
        //reference to widget
        movieRate = (RatingBar) findViewById(R.id.movieRate);
        etName = (EditText) findViewById(R.id.etName);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        cbLater = (CheckBox) findViewById(R.id.cbLater);
        //Check wether there was value passed to this intent
        if(getIntent().getExtras()!= null){
            Intent i = getIntent();
            mName = i.getStringExtra("moviename");
            mDate = i.getStringExtra("moviedate");
            mRate = i.getFloatExtra("movierate",0);
            saved_name = mName;
            //seperate the date into year, month and day
            String[] splitDate = mDate.split("-");
            int year = Integer.parseInt(splitDate[0]);
            int month = Integer.parseInt(splitDate[1]) -1;
            //If it is January, then month will be 0 instead of 12
            if (month == 0)
                month = 12;
            int day = Integer.parseInt(splitDate[2]);
            etName.setText(mName);
            movieRate.setRating(mRate);
            datePicker.updateDate(year,month,day);
            add_or_update = 1;
        }
        cbLater.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_update,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_save:
                if(!etName.getText().toString().equals("")){
                    //check if it is to update or add data into table
                    if(add_or_update == 0) {
                        insertIntoDB();
                        Intent goMain = new Intent(this,MainActivity.class);
                        startActivity(goMain);
                    }
                    else if (add_or_update == 1) {
                        updateDB();
                        Intent goMain = new Intent(this,MainActivity.class);
                        startActivity(goMain);
                    }
                }
                else{
                    Toast.makeText(this, "Please input the movie name", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.menu_cancel:
                Intent movieMain = new Intent(this,MainActivity.class);
                startActivity(movieMain);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //Insert into DB MOVIE table
    private void insertIntoDB(){
        db = new DBHelper(this);
        //check if the database is there
        /*
        File database = getDatabasePath(DBHelper.DB_NAME);
        if(database.exists()){
            db.getWritableDatabase();
            if(copyDatabase(this)){
                db.getWritableDatabase();
                if(copyDatabase(this)){
                    Toast.makeText(this, "Copy database success", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(this, "Copy data error",Toast.LENGTH_LONG).show();
                    return;

                }
            }
        }*/
        //Get the movie name, date and rating from the widget then call the function insertData
        mName = etName.getText().toString();
        dateYear = Integer.toString(datePicker.getYear());
        dateMonth= Integer.toString(datePicker.getMonth() +1);
        dateDay = Integer.toString(datePicker.getDayOfMonth());

        mDate = dateYear+"-"+dateMonth+"-"+dateDay;
        mRate = movieRate.getRating();
        db.insertData(mName,mDate,mRate);
        sendNotification();
    }

    //Update the DB Movie table with the selected movie from the listview
    private void updateDB(){
        db = new DBHelper(this);
        //check if database is there
        /*
        File database = getDatabasePath(DBHelper.DB_NAME);
        if(database.exists()){
            db.getWritableDatabase();
            if(copyDatabase(this)){

                db.getWritableDatabase();
                if(copyDatabase(this)){
                    Toast.makeText(this, "Copy database success", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(this, "Copy data error",Toast.LENGTH_LONG).show();
                    return;

                }
            }
        }*/
        //Get the movie name, date, rating from the widget and the movie name from
        // the selected listview item then call the function updateData
        dateYear = Integer.toString(datePicker.getYear());
        dateMonth= Integer.toString(datePicker.getMonth() +1);
        dateDay = Integer.toString(datePicker.getDayOfMonth());
        mName = etName.getText().toString();
        mDate = dateYear+"-"+dateMonth+"-"+dateDay;
        mRate = movieRate.getRating();
        db.updateData(mName,mDate,mRate,saved_name);
        sendNotification();
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
    }
    */

    //When the check box is clicked, it will check wether the checkbox is checked or not
    @Override
    public void onClick(View view) {
        if(cbLater.isChecked()){
            checked =1;
        }
        else if (!cbLater.isChecked()) {
            checked =0;
        }
    }

    //Send the notification to the user. Only one notification, not multiple
    private void sendNotification(){
        //When the checkbox "Ask Later" is checked, it will send the notification
        if(checked == 1){
            Intent intent = new Intent(this,MovieUpdate.class);
            intent.putExtra("moviename", mName);
            intent.putExtra("moviedate", mDate);
            intent.putExtra("movierate", mRate);
            PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setContentTitle("Give this movie a rating");
            builder.setContentText("Give rating to: " + mName);
            builder.setSmallIcon(R.drawable.small_m_icon);
            builder.setContentIntent(pIntent);
            builder.setAutoCancel(true);
            Notification n = builder.build();
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.notify(0,n);
        }

    }
}
