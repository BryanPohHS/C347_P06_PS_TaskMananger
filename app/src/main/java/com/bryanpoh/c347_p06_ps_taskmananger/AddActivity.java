package com.bryanpoh.c347_p06_ps_taskmananger;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    EditText etName, etDesc, etSeconds;
    Button btnAdd, btnCancel;

    int reqCode = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etName = findViewById(R.id.etName);
        etDesc = findViewById(R.id.etDescription);
        etSeconds = findViewById(R.id.etSeconds);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*String name = etName.getText().toString();
                String description = etDesc.getText().toString();

                DBHelper dbh = new DBHelper(AddActivity.this);
                long inserted_id = dbh.insertTask(name, description);
                dbh.close();

                if (inserted_id != -1){
                    Toast.makeText(AddActivity.this, "Insert successful",
                            Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    setResult(RESULT_OK, intent);
                    finish();
                }*/

                String name = etName.getText().toString();
                String description = etDesc.getText().toString();
                int seconds = Integer.parseInt(etSeconds.getText().toString());

                DBHelper dbh = new DBHelper(AddActivity.this);
                long inserted_id = dbh.insertTask(name, description);
                dbh.close();

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, seconds);

                Intent intent = new Intent(AddActivity.this, MyReceiver.class);
                intent.putExtra("data", description);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(AddActivity.this, reqCode,
                        intent, PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager am = (AlarmManager)
                        getSystemService(Activity.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                        pendingIntent);


                if (inserted_id != -1){
                    Toast.makeText(AddActivity.this, "Insert successful",
                            Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    setResult(RESULT_OK, i);
                    finish();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
