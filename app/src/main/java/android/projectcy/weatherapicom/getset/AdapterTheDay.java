package android.projectcy.weatherapicom.getset;

import android.content.Context;
import android.projectcy.weatherapicom.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterTheDay extends RecyclerView.Adapter<AdapterTheDay.ViewHolder> {

    private List<GetSetTheDay> itemLists;
    private Context context;

    public AdapterTheDay(List<GetSetTheDay> itemLists, Context context){
        this.itemLists = itemLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_info_theday, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetSetTheDay getSetItem = itemLists.get(position);

        Glide.with(context).load("http:" + getSetItem.getIcon()).into(holder.imageView);
        holder.id_date.setText(getSetItem.getTime());
        holder.id_maxtemp_c.setText(getSetItem.getMax() + " °C");
        holder.id_mintemp_c.setText(getSetItem.getMin() + " °C");
        holder.id_avgtemp_c.setText(getSetItem.getAvg() + " °C");
        holder.id_max_wind_speed.setText(getSetItem.getMax_wind_speed());
        holder.id_total_precipitation.setText(getSetItem.getTotal_precipitation());
        holder.id_condition_text.setText(getSetItem.getCondition_text());
    }

    @Override
    public int getItemCount() {
        return itemLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView id_date, id_maxtemp_c, id_mintemp_c, id_avgtemp_c, id_max_wind_speed, id_total_precipitation, id_condition_text;
        public ImageView imageView;

        public ViewHolder(View view){
            super(view);

            id_date = (TextView) view.findViewById(R.id.id_date);
            id_maxtemp_c = (TextView) view.findViewById(R.id.id_maxtemp_c);
            id_mintemp_c = (TextView) view.findViewById(R.id.id_mintemp_c);
            id_avgtemp_c = (TextView) view.findViewById(R.id.id_avgtemp_c);
            id_max_wind_speed = (TextView) view.findViewById(R.id.id_max_wind_speed);
            id_total_precipitation = (TextView) view.findViewById(R.id.id_total_precipitation);
            id_condition_text = (TextView) view.findViewById(R.id.id_condition_text);
            imageView = (ImageView) view.findViewById(R.id.imageView);
        }
    }
}
