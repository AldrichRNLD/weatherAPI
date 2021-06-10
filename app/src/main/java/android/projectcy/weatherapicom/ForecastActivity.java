package android.projectcy.weatherapicom;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.projectcy.weatherapicom.addon.RecyclerItemClickListener;
import android.projectcy.weatherapicom.getset.AdapterForecast;
import android.projectcy.weatherapicom.getset.GetSetForecast;
import android.view.View;
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

public class ForecastActivity extends AppCompatActivity {

    AdapterForecast adapterForecast;
    List<GetSetForecast> getSetForecasts;
    RecyclerView recyclerView;
    TextView id_region, id_country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        id_region = findViewById(R.id.id_region);
        id_country = findViewById(R.id.id_country);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        int itemPosition = recyclerView.getChildLayoutPosition(view);
                        Intent intent = new Intent(ForecastActivity.this, TheDayActivity.class);
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
            adapterForecast = new AdapterForecast(getSetForecastsFunc(result), ForecastActivity.this);
            recyclerView.setAdapter(adapterForecast);
        }

        public List<GetSetForecast> getSetForecastsFunc(String result) {
            getSetForecasts = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(result);
                String region, country;
                region = jsonObject.getJSONObject("location").getString("region");
                country = jsonObject.getJSONObject("location").getString("country");
                id_region.setText(region);
                id_country.setText(country);
                JSONArray jsonArray = jsonObject.getJSONObject("forecast").getJSONArray("forecastday");
                int count = 0;
                String date, avgtemp_c, condition_text, icon;
                while (count<jsonArray.length()){
                    JSONObject JO = jsonArray.getJSONObject(count);
                    date = JO.getString("date");
                    avgtemp_c = JO.getJSONObject("day").getString("avgtemp_c");
                    condition_text = JO.getJSONObject("day").getJSONObject("condition").getString("text");
                    icon = JO.getJSONObject("day").getJSONObject("condition").getString("icon");

                    getSetForecasts.add(new GetSetForecast(date, avgtemp_c, condition_text, icon));
                    count++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return getSetForecasts;
        }
    }
}