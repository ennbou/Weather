package com.ennbou.meteo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private Toolbar toolBar;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private MenuItem searchItem;

    private ImageView img;
    private TextView temp, tempMax, tempMin, humidity, pressure, description, dateTime, cityNameTxt;
    private SearchView search;

    final String URL = "https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&appid=73a8d3d048f202365fd63c5c53338843";
    final String URL_ICON = "http://openweathermap.org/img/wn/%s@2x.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);


        cityNameTxt = findViewById(R.id.city_name);
        img = findViewById(R.id.img);
        temp = findViewById(R.id.temp);
        description = findViewById(R.id.description);
        tempMin = findViewById(R.id.temp_min);
        tempMax = findViewById(R.id.temp_max);
        humidity = findViewById(R.id.humidity_value);
        pressure = findViewById(R.id.pressure_value);
        dateTime = findViewById(R.id.date_time);

        requestQueue = Volley.newRequestQueue(this);

        getWeather("Casablanca"); // default city

    }

    public boolean getWeather(String cityName) {
        // the request do in background thread but the parsing in the UI thread,
            // we don't need other thread to parsing and got all data, because there is a few data
        requestQueue.add(
                new StringRequest(Request.Method.GET, String.format(URL, cityName),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {

                                    JSONObject data = new JSONObject(response);
                                    JSONObject weather = data.getJSONArray("weather").getJSONObject(0);
                                    JSONObject mainData = data.getJSONObject("main");

                                    String name = data.getString("name");

                                    String description = weather.getString("description");
                                    String icon = weather.getString("icon");

                                    double temp = mainData.getDouble("temp");
                                    double tempMin = mainData.getDouble("temp_min");
                                    double tempMax = mainData.getDouble("temp_max");
                                    double pressure = mainData.getDouble("pressure");
                                    double humidity = mainData.getDouble("humidity");
                                    MainActivity activity = MainActivity.this;

                                    Picasso.get().load(String.format(URL_ICON, icon)).into(activity.img, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            // TODO on image success loading (PICASSO)
                                        }

                                        @Override
                                        public void onError(Exception e) {
                                            // TODO when image failed loading (PICASSO)
                                        }
                                    });

                                    long datetime = data.getLong("dt");
                                    SimpleDateFormat format = new SimpleDateFormat("E MMMM d HH:mm:ss ");
                                    String date = format.format(new Date(datetime * 1000));

                                    activity.cityNameTxt.setText(name);

                                    activity.temp.setText(temp + "°");
                                    activity.tempMin.setText(tempMin + "°");
                                    activity.tempMax.setText(tempMax + "°");
                                    activity.pressure.setText("" + pressure);
                                    activity.humidity.setText("" + humidity);
                                    activity.description.setText("" + description);
                                    activity.dateTime.setText(date);


                                } catch (JSONException e) {
                                    // TODO error handling
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO ERROR in request
                    }
                })
        );
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        searchItem.collapseActionView();
        getWeather(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
