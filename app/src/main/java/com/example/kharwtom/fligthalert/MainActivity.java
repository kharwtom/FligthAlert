package com.example.kharwtom.fligthalert;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    void connectTo(View view){
        Intent intent = new Intent(this,ShowInfomation.class);
        EditText flight =(EditText) findViewById(R.id.editText);
        String flight_number = flight.getText().toString();
        if(flight_number.equalsIgnoreCase("")){
            Toast.makeText(MainActivity.this,"Please enter your flight",Toast.LENGTH_LONG).show();
        }
        else{
        intent.putExtra("fnb",flight_number);
        startActivity(intent);
        }
    }

}
