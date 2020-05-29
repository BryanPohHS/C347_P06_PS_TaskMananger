package com.bryanpoh.c347_p06_ps_taskmananger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<Task> al;
    TaskArrayAdapter aa;

    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.listview);
        btnAdd = findViewById(R.id.btnAdd);

        al = new ArrayList<>();

        DBHelper dbh = new DBHelper(MainActivity.this);
        al.clear();
        al.addAll(dbh.getAllTasks(""));

        dbh.close();
        aa = new TaskArrayAdapter(this, R.layout.row, al);
        ArrayAdapter aa = new TaskArrayAdapter(getApplicationContext(),R.layout.row,al);
        lv.setAdapter(aa);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, 9);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 9){
            DBHelper dbh = new DBHelper(MainActivity.this);
            al.clear();
            al.addAll(dbh.getAllTasks(""));
            dbh.close();
            aa = new TaskArrayAdapter(this, R.layout.row, al);
            ArrayAdapter aa = new TaskArrayAdapter(getApplicationContext(),R.layout.row,al);
            lv.setAdapter(aa);
        }
    }
}