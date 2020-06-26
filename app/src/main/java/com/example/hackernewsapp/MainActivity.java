package com.example.hackernewsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    // private RequestQueue queue;
    RequestQueue queue;

    // async class to call api
    /*
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

     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // api test
        /*
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
        */


        // list view experimentation
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

        // recycler view demo
        /*
        ArrayList<String> arr = new ArrayList<>(Arrays.asList(new String[] {"Pokemon", "Death Note", "You Lie in April", "Ano Hana", "One Punch Man", "I'm a Spider, So What?"}));
        RecyclerView recyclerView = findViewById(R.id.myRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RVadapter(arr));

         */

        // volley demo
        // making a request queue
        // queue = Volley.newRequestQueue(this);

        // Singleton design pattern suggested in the docs
        // i.e. make only one que for entire application
        queue = MyRequestQueue.getInstance(this.getApplicationContext()).getRequestQueue();

        String[] categories = {"topstories", "newstories", "beststories", "askstories",
                "showstories", "jobstories", "updates", "user", "maxitem"};

        // String itemId = "23640878";

        String u1 = "https://hacker-news.firebaseio.com/v0/"+ categories[0] +".json?print=pretty";
        // String u2 = "https://hacker-news.firebaseio.com/v0/item/"+ itemId +".json?print=pretty";

        // making a request StringRequest
        /*
        StringRequest stringRequest = new StringRequest(Request.Method.GET, u1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("RESPONSE", response);
                        Toast.makeText(MainActivity.this, "got a response", Toast.LENGTH_SHORT);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "failed to get a response", Toast.LENGTH_SHORT);
                    }
                });

        stringRequest.setTag(this);

        queue.add(stringRequest);

         */

        // making a jsonArrayRequest
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, u1, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            String itemId = Integer.toString(response.getInt(0));
                            String u2 = "https://hacker-news.firebaseio.com/v0/item/"+ itemId +".json?print=pretty";

                            Log.i("DEBUG", "requesting url=" + u2);

                            StringRequest sr = new StringRequest(Request.Method.GET, u2,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Log.i("DEBUG", "response="+response);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(MainActivity.this, "Request failed", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            queue.add(sr);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Request failed", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(jsonArrayRequest);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // cancel all requests marked with this tag
        // mark all requests with current activity and in onStop cancel all to ensure there are no pending requests in the queue
        if (queue != null) {
            Log.i("DEBUG", "cancelling all requests of this activity!");
            queue.cancelAll(this);
        }
    }
}