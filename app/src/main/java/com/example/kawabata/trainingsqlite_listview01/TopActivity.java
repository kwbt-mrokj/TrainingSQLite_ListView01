package com.example.kawabata.trainingsqlite_listview01;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Calendar;


public class TopActivity extends Activity {

    private SQLiteDatabase db;
    private ListView myListView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;

        } else if (id == R.id.action_addData) {

            Intent intent = new Intent(TopActivity.this, DataRegActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
    }

    private long insertData(String[] data) throws IOException {

        String time = Calendar.getInstance().getTime().toString();

        ContentValues values = new ContentValues();
        values.put(DBHelper.REG_NAME, data[0]);
        values.put(DBHelper.REG_CONTENT, data[1]);
        values.put(DBHelper.REG_DATE, time);

        long result = -1;

        if (db != null) {
            result = db.insert(DBHelper.TABLE_NAME, null, values);

        }
        return result;
    }

    @Override
    public void onStart() {
        super.onStart();

        DBHelper helper = new DBHelper(this);
        db = helper.getWritableDatabase();

        //String s[] = new String[]("","");
        //insertData();

        myListView = (ListView)findViewById(R.id.topListView);

        // 表示するカラム名
        String[] title = {
                "Isobe",
                "Otsubo",
                "Kato",
                "Shimazawa",
                "Yoshida",
                "Awaji",
                "Ito",
                "Gomibuchi",
                "Nagahora",
                "Kodate",
                "Odagiri",
                "Sunako",
                "Wada"
        };

        // アダプターの生成
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TopActivity.this, R.layout.listview_item, title);

        // adapterをListViewにbindして表示
        myListView.setAdapter(adapter);

        // クリックしたとき各itemのデータを取得
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //各要素を取得
                String transName = ((TextView)view).getText().toString();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setAction(transName);

                startActivity(intent);

            }
        });
    }
}
