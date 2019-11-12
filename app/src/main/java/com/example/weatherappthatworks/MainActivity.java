package com.example.weatherappthatworks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    EditText cityname;
    Button go;

    String cName;

    class weather extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream ir = connection.getInputStream();
                InputStreamReader br = new InputStreamReader(ir);
                int data = br.read();
                String str = "";
                while (data != -1) {
                    char ch = (char) data;
                    str = str + ch;
                    data = br.read();


                }
                return str;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;

        }
    }

    public void search(View view) {


        TextView tempe, city, type;
        cityname = (EditText) findViewById(R.id.cityname);
        go = (Button) findViewById(R.id.button);
        tempe = (TextView) findViewById(R.id.textView_temp);
        type = (TextView) findViewById(R.id.textView_type);
         cName = cityname.getText().toString();
        String str;
                weather w = new weather();
                try {
                    str = w.execute("https://openweathermap.org/data/2.5/weather?q="+cName+"&appid=b6907d289e10d714a6e88b30761fae22").get();
                    //Log.i("str", str);


                    JSONObject jsonObject = new JSONObject(str);
                    JSONObject j = new JSONObject(jsonObject.getString("main"));

                    String temp = j.getString("temp");
                    for (int k = 0; k < temp.length(); k++) {

                        if (temp.charAt(k) =='.')
                        {
                            temp.substring(0, k );

                        }
                    }
                    String weatherData = jsonObject.getString("weather");
                    JSONArray array = new JSONArray(weatherData);
                    String des = "";
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject weatherDes = array.getJSONObject(i);
                        des = weatherDes.getString("description");
                    }
                    Log.i("tem",temp);
                    Log.i("description", des);

                    tempe.setText(temp);
                    type.setText(des);

                } catch (Exception e) {
                    e.printStackTrace();

                }


            }


            @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);







    }
}
