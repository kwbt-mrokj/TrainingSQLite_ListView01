package com.example.kawabata.trainingsqlite_listview01;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;


public class DataRegActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_reg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_data_reg, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Button regBtn = (Button)findViewById(R.id.regBtn);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDatabase db;

                DBHelper helper = new DBHelper(DataRegActivity.this);
                db = helper.getWritableDatabase();

                String time = Calendar.getInstance().getTime().toString();

                ContentValues values = new ContentValues();
                values.put(DBHelper.REG_NAME, ((EditText)findViewById(R.id.regNameEdit)).getText().toString());
                values.put(DBHelper.REG_CONTENT, ((EditText)findViewById(R.id.regContentEdit)).getText().toString());
                values.put(DBHelper.REG_DATE, time);

                if (db != null) {
                    try {
                        if (db.insert(DBHelper.TABLE_NAME, null, values) != -1) {

                            AlertDialog.Builder ad = new AlertDialog.Builder(DataRegActivity.this);

                            ad.setTitle("result");
                            ad.setMessage(values.get(DBHelper.REG_NAME).toString() + "\n" +
                                    values.get(DBHelper.REG_CONTENT).toString() + "\n" +
                                    values.get(DBHelper.REG_DATE).toString());

                            ad.setPositiveButton("ok",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            Intent intent = new Intent(DataRegActivity.this, TopActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                            ad.show();
                        }

                    }catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
