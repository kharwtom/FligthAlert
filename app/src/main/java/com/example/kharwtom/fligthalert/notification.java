package com.example.kharwtom.fligthalert;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class notification extends AppCompatActivity {
    private Spinner vehicle;
    private ArrayList<String> name_vehicle = new ArrayList<String>();
    private String select;
    private EditText email;
    String detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        vehicle = (Spinner) findViewById(R.id.spinner);
        Bundle bundle = getIntent().getExtras();
        detail =   bundle.getString("a");
        email = (EditText)findViewById(R.id.editText4);
        createSpinner();


        vehicle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                select = name_vehicle.get(position);
                //Toast.makeText(notification.this, "Select : " + name_vehicle.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void sender(){
        sendMailClass read = new sendMailClass();
        String s = email.getText().toString()+"*Detail of your flight is "+detail;
        read.execute(s);

    }
    private void createSpinner(){
        name_vehicle.add("Please select");
        name_vehicle.add("Personal car");
        name_vehicle.add("Taxi");
        name_vehicle.add("Bus");
        name_vehicle.add("Van");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,name_vehicle);
        vehicle.setAdapter(adapter);

    }

    void submitemail(View view){
        if(email.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(notification.this, "Please enter your email.", Toast.LENGTH_SHORT).show();
        }
        else if(select.equalsIgnoreCase("Please select"))
            Toast.makeText(notification.this, "Please select", Toast.LENGTH_SHORT).show();
        else if (select.equalsIgnoreCase("Personal car")) {
            sender();
            Intent intent = new Intent(this, Finishpage.class);
            startActivity(intent);
        }else {
            sender();
            Intent intent = new Intent(this, Location.class);
            startActivity(intent);
        }
    }
}
class sendMailClass extends AsyncTask<String,Void,String> {

    @Override
    protected String doInBackground(String... urls) {

        String textSource = "http://tuflightalert.esy.es/send.php?email="+urls[0].split("\\*")[0]+"&flight="+urls[0].split("\\*")[1];

        try {
            URL oracle = new URL(textSource);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            in.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
