package android.projectcy.weatherapicom.getset;

public class GetSetTheDay {
    private String time, max, min, avg, max_wind_speed, total_precipitation, condition_text, icon;

    public GetSetTheDay(String time, String max, String min, String avg, String max_wind_speed, String total_precipitation, String condition_text, String icon) {
        this.time = time;
        this.max = max;
        this.min = min;
        this.avg = avg;
        this.max_wind_speed = max_wind_speed;
        this.total_precipitation = total_precipitation;
        this.condition_text = condition_text;
        this.icon = icon;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getAvg() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg = avg;
    }

    public String getMax_wind_speed() {
        return max_wind_speed;
    }

    public void setMax_wind_speed(String max_wind_speed) {
        this.max_wind_speed = max_wind_speed;
    }

    public String getTotal_precipitation() {
        return total_precipitation;
    }

    public void setTotal_precipitation(String total_precipitation) {
        this.total_precipitation = total_precipitation;
    }

    public String getCondition_text() {
        return condition_text;
    }

    public void setCondition_text(String condition_text) {
        this.condition_text = condition_text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
