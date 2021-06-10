package android.projectcy.weatherapicom;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {

    TextView id_region, id_country, id_wind_kph, id_pressure_mb, id_precip_mm, id_humidity, id_cloud, id_gust_kph, id_condition_text, id_temp_c, id_last_updated, id_air_quality;
    Button button_forecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id_region = findViewById(R.id.id_region);
        id_country = findViewById(R.id.id_country);
        id_wind_kph = findViewById(R.id.id_wind_kph);
        id_pressure_mb = findViewById(R.id.id_pressure_mb);
        id_precip_mm = findViewById(R.id.id_precip_mm);
        id_humidity = findViewById(R.id.id_humidity);
        id_cloud = findViewById(R.id.id_cloud);
        id_gust_kph = findViewById(R.id.id_gust_kph);
        id_condition_text = findViewById(R.id.id_condition_text);
        id_temp_c = findViewById(R.id.id_temp_c);
        id_last_updated = findViewById(R.id.id_last_updated);
        id_air_quality = findViewById(R.id.id_air_quality);
        button_forecast = findViewById(R.id.button_forecast);
        button_forecast.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, ForecastActivity.class));
        });

        new GetDataTask().execute();

    }

    class GetDataTask extends AsyncTask<String, Void, String> {

        Setting setting = new Setting();
        String json_url;
        URL url;

        @Override
        protected void onPreExecute() {
            json_url = setting.current;
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
                        URLEncoder.encode("aqi","UTF-8")+"="+ URLEncoder.encode("yes","UTF-8");
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
            try {
                JSONObject jsonObject = new JSONObject(result);
                String region, country, wind_kph, pressure_mb, precip_mm, humidity, cloud, gust_kph, condition_text, temp_c, last_updated;
                region = jsonObject.getJSONObject("location").getString("region");
                country = jsonObject.getJSONObject("location").getString("country");
                wind_kph = jsonObject.getJSONObject("current").getString("wind_kph");
                pressure_mb = jsonObject.getJSONObject("current").getString("pressure_mb");
                precip_mm = jsonObject.getJSONObject("current").getString("precip_mm");
                humidity = jsonObject.getJSONObject("current").getString("humidity");
                cloud = jsonObject.getJSONObject("current").getString("cloud");
                gust_kph = jsonObject.getJSONObject("current").getString("gust_kph");
                condition_text = jsonObject.getJSONObject("current").getJSONObject("condition").getString("text");
                temp_c = jsonObject.getJSONObject("current").getString("temp_c");
                last_updated = jsonObject.getJSONObject("current").getString("last_updated");

                id_region.setText(region);
                id_country.setText(country);
                id_wind_kph.setText(id_wind_kph.getText().toString() + wind_kph);
                id_pressure_mb.setText(id_pressure_mb.getText().toString() + pressure_mb);
                id_precip_mm.setText(id_precip_mm.getText().toString() + precip_mm);
                id_humidity.setText(id_humidity.getText().toString() + humidity);
                id_cloud.setText(id_cloud.getText().toString() + cloud);
                id_gust_kph.setText(id_gust_kph.getText().toString() + gust_kph);
                id_condition_text.setText(id_condition_text.getText().toString() + condition_text);
                id_temp_c.setText(id_temp_c.getText().toString() + temp_c + " °C");
                id_last_updated.setText(id_last_updated.getText().toString() + last_updated);
                id_air_quality.setText(id_air_quality.getText().toString()
                        + "\n- co : " + jsonObject.getJSONObject("current").getJSONObject("air_quality").getString("co")
                        + "\n- no2 : " + jsonObject.getJSONObject("current").getJSONObject("air_quality").getString("no2")
                        + "\n- o3 : " + jsonObject.getJSONObject("current").getJSONObject("air_quality").getString("o3")
                        + "\n- so2 : " + jsonObject.getJSONObject("current").getJSONObject("air_quality").getString("so2")
                        + "\n- pm2_5 : " + jsonObject.getJSONObject("current").getJSONObject("air_quality").getString("pm2_5")
                        + "\n- pm10 : " + jsonObject.getJSONObject("current").getJSONObject("air_quality").getString("pm10")
                        + "\n- us-epa-index : " + jsonObject.getJSONObject("current").getJSONObject("air_quality").getString("us-epa-index")
                        + "\n- gb-defra-index : " + jsonObject.getJSONObject("current").getJSONObject("air_quality").getString("gb-defra-index")
                        );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}