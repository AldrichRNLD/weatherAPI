package android.projectcy.weatherapicom;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.projectcy.weatherapicom.addon.RecyclerItemClickListener;
import android.projectcy.weatherapicom.getset.AdapterTheDay;
import android.projectcy.weatherapicom.getset.GetSetTheDay;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class TheDayActivity extends AppCompatActivity {

    AdapterTheDay adapterTheDay;
    List<GetSetTheDay> getSetTheDays;
    RecyclerView recyclerView;
    TextView id_region, id_country, id_date;
    Intent intent;
    Button button_forecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theday);

        intent = getIntent();

        id_region = findViewById(R.id.id_region);
        id_country = findViewById(R.id.id_country);
        id_date = findViewById(R.id.id_date);
        button_forecast = findViewById(R.id.button_forecast);
        button_forecast.setOnClickListener(view -> {
            startActivity(new Intent(TheDayActivity.this, ForecastActivity.class));
            finish();
        });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        int itemPosition = recyclerView.getChildLayoutPosition(view);
                        Intent intent = new Intent(TheDayActivity.this, TheDayActivity.class);
                        intent.putExtra("index", itemPosition);
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );

        new GetDataTask().execute();
    }

    class GetDataTask extends AsyncTask<String, Void, String> {

        Setting setting = new Setting();
        String json_url;
        URL url;

        @Override
        protected void onPreExecute() {
            json_url = setting.forecast;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("key","UTF-8")+"="+ URLEncoder.encode("4bc8cfe44b324366909170149210706","UTF-8")+"&"+
                        URLEncoder.encode("q","UTF-8")+"="+ URLEncoder.encode("Jakarta","UTF-8")+"&"+
                        URLEncoder.encode("days","UTF-8")+"="+ URLEncoder.encode("4","UTF-8")+"&"+
                        URLEncoder.encode("aqi","UTF-8")+"="+ URLEncoder.encode("yes","UTF-8")+"&"+
                        URLEncoder.encode("alerts","UTF-8")+"="+ URLEncoder.encode("no","UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine())!=null)
                {
                    response+= line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(TheDayActivity.this, result, Toast.LENGTH_SHORT).show();
            adapterTheDay = new AdapterTheDay(getSetTheDaysFunc(result), TheDayActivity.this);
            recyclerView.setAdapter(adapterTheDay);
        }

        public List<GetSetTheDay> getSetTheDaysFunc(String result) {
            getSetTheDays = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(result);
                String region, country;
                region = jsonObject.getJSONObject("location").getString("region");
                country = jsonObject.getJSONObject("location").getString("country");
                id_region.setText(region);
                id_country.setText(country);
                JSONArray jsonArray = jsonObject.getJSONObject("forecast").getJSONArray("forecastday");
                JSONObject JO = jsonArray.getJSONObject(intent.getIntExtra("index", 0));
                JSONArray jsonArray2 = JO.getJSONArray("hour");
                String date = JO.getString("date");
                id_date.setText(date);
                int count = 0;
                String time, mintemp_c, maxtemp_c, avgtemp_c, max_wind_speed, total_precipitation, condition_text, icon;
                while (count<jsonArray2.length()){
                    JSONObject JO2 = jsonArray2.getJSONObject(count);
                    time = JO2.getString("time");
                    mintemp_c = JO.getJSONObject("day").getString("mintemp_c");
                    maxtemp_c = JO.getJSONObject("day").getString("maxtemp_c");
                    avgtemp_c = JO.getJSONObject("day").getString("avgtemp_c");
                    max_wind_speed = JO.getJSONObject("day").getString("maxwind_kph");
                    total_precipitation = JO.getJSONObject("day").getString("totalprecip_mm");
                    condition_text = JO2.getJSONObject("condition").getString("text");
                    icon = JO2.getJSONObject("condition").getString("icon");

                    getSetTheDays.add(new GetSetTheDay(time, maxtemp_c, mintemp_c, avgtemp_c, max_wind_speed, total_precipitation, condition_text, icon));
                    count++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return getSetTheDays;
        }
    }
}