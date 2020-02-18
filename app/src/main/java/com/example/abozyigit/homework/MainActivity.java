package com.example.abozyigit.homework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    TextView toplamsure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        Intent service = new Intent(this, MyReceiverService.class);
        startService(service);
        Intent intent = getIntent();
        boolean kullanici = intent.getBooleanExtra("kullanıcı", false);

    }
    public void goruntuleOnClick(View view) {
        MyDatabase myDatabase= new MyDatabase(this);
        Map<String, List<Object>> map = myDatabase.verileriAl();

        List<Object> timesobjects = map.get("times");
        ///https://stackoverflow.com/a/4581429
        List<String> times = new ArrayList<>(timesobjects.size());
        for (Object object : timesobjects) {
            times.add(object != null ? object.toString() : null);
        }

        List<Object> lockedobjects = map.get("locked");
        ///https://stackoverflow.com/a/4581429
        List<Boolean> locked = new ArrayList<>(lockedobjects.size());
        for (Object object : lockedobjects) {
            locked.add(object != null ? (Boolean) object : null);
        }

        List<Object> times2objects = map.get("times2");
        ///https://stackoverflow.com/a/4581429
        List<String> times2 = new ArrayList<>(times2objects.size());
        for (Object object : times2objects) {
            times2.add(object != null ? object.toString() : null);
        }

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                times2);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        long totaltime = 0L;
        long unlockedtime = -1;
        for(int i=0; i<times.size(); i++){
            SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssX");
            Date date = null;
            if(times.get(i).equals("null")){
                continue;
            }
            try{
                date = parser.parse(times.get(i));
            } catch(ParseException e) {}
            if(unlockedtime == -1 && !locked.get(i)){
                unlockedtime = date.getTime();
            } else if (unlockedtime != -1 && locked.get(i)){
                totaltime += (date.getTime() - unlockedtime) / 1000;
                unlockedtime = -1;
            }
        }
        toplamsure = findViewById(R.id.textView2);
        toplamsure.setText(DateUtils.formatElapsedTime(totaltime));
    }
}