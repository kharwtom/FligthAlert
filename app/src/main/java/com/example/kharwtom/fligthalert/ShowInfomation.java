package com.example.kharwtom.fligthalert;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

public class ShowInfomation extends AppCompatActivity {
    public String fligth;
    String all;
    private TextView fn,air,time,ft;
    private Button btn;
    String forInfo;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_infomation2);
        Bundle bundle = getIntent().getExtras();
        forInfo = "";
        fligth = bundle.getString("fnb");
        fn = (TextView)findViewById(R.id.textView1);
        air = (TextView)findViewById(R.id.textView2);
        time = (TextView)findViewById(R.id.textView3);
        ft = (TextView)findViewById(R.id.textView4);
        btn = (Button) findViewById(R.id.button);
        read(fligth);


    }

    void valid(View view){
        Intent intent = new Intent(this,notification.class);
        intent.putExtra("a",forInfo);
        startActivity(intent);
    }
    void invalid(View view){
        finish();
    }
    void read(String flight) {
        readerWeb read = new readerWeb();
        read.execute(flight);
        String[]sprit = {"NULL","NULL","NULL","NULL"};
    try {
        String re = read.get();
        sprit = re.split(",");
        forInfo = re;
    } catch (InterruptedException e) {
        e.printStackTrace();
    } catch (ExecutionException e) {
        e.printStackTrace();
    }
        if(sprit[0].equalsIgnoreCase("null"))
           btn.setEnabled(false);
        fn.setText(sprit[0]);
        air.setText(sprit[1]);
        time.setText(sprit[2]);
        ft.setText(sprit[3]);

    }
}

class readerWeb extends  AsyncTask<String,Void,String>{

    @Override
    protected String doInBackground(String... urls) {
        String forRe = "NULL,NULL,NULL,NULL";
        String textSource = "http://tuflightalert.esy.es/test.php?flight="+urls[0];
        String inputLine = "";
        try {
            URL oracle = new URL(textSource);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

            String ready="";

            while ((ready = in.readLine()) != null) {

                inputLine = inputLine + ready;
            }
            in.close();
            inputLine= inputLine.replace("<html><head><title>Test</title></head><body>", "");
            inputLine= inputLine.replace("</body></html>", "");
            int count = 0;
            for(int i =0;i<inputLine.length();i++){
                if(inputLine.charAt(i)==',')
                    count++;
            }
            if (count==3)
        forRe = inputLine;



        } catch (IOException e) {
            e.printStackTrace();
        }
        return forRe;
    }
}
