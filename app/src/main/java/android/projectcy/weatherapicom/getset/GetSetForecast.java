package android.projectcy.weatherapicom.getset;

public class GetSetForecast {
    private String date, avgtemp_c, condition_text, icon;

    public GetSetForecast(String date, String avgtemp_c, String condition_text, String icon) {
        this.date = date;
        this.avgtemp_c = avgtemp_c;
        this.condition_text = condition_text;
        this.icon = icon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAvgtemp_c() {
        return avgtemp_c;
    }

    public void setAvgtemp_c(String avgtemp_c) {
        this.avgtemp_c = avgtemp_c;
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
