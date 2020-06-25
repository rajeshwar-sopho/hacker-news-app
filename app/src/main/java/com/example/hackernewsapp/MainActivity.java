package com.example.hackernewsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    public class APICaller extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url = null;
            URLConnection urlConnection = null;
            InputStream in = null;
            InputStreamReader reader = null;
            BufferedReader br = null;


            try {
                url = new URL(urls[0]);
                urlConnection = url.openConnection();
                urlConnection.connect();
                in = urlConnection.getInputStream();
                reader = new InputStreamReader(in);
                br = new BufferedReader(reader);
                String line = br.readLine();
                while (line != null) {
                    result += line;
                    line = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Log.i("DEBUG", result);


            JSONObject jsonObject = null;

            try {
                jsonObject = new JSONObject(result);
                JSONArray jsonArray = (JSONArray) jsonObject.get("weather");
                JSONObject jo1 = (JSONObject) jsonArray.get(0);

                try {
                    Log.i("main", jo1.get("main").toString());
                    Log.i("description", jo1.get("description").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }




        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String resultJSON = null;

        APICaller wapic = new APICaller();

        String[] categories = {"topstories", "newstories", "beststories", "askstories",
        "showstories", "jobstories", "updates", "user", "maxitem"};

        String itemId = "23640878";

        try {
            String u1 = "https://hacker-news.firebaseio.com/v0/"+ categories[5] +".json?print=pretty";
            String u2 = "https://hacker-news.firebaseio.com/v0/item/"+ itemId +".json?print=pretty";
            resultJSON = wapic.execute(u2).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        /*
        ArrayList<String> arrayList = new ArrayList<>();
        final ArrayList<Boolean> isRetracted = new ArrayList<>();

        arrayList.add("Rajesh");
        arrayList.add("Amar");
        arrayList.add("Priyanka");
        arrayList.add("Sachleen");

        isRetracted.add(true);
        isRetracted.add(true);
        isRetracted.add(true);
        isRetracted.add(true);

        MyAdapter adapter = new MyAdapter(this, R.layout.list_item, arrayList);

        ListView lv = findViewById(R.id.listView);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lv, View listItem, int i, long l) {

                TextView body = listItem.findViewById(R.id.body);

                if (isRetracted.get(i)) {
                    body.setText(R.string.lorem_text);
                    isRetracted.set(i, false);
                } else {
                    body.setText("");
                    isRetracted.set(i, true);
                }
            }
        });

         */
    }
}