package android.projectcy.weatherapicom.getset;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
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

public class AdapterForecast extends RecyclerView.Adapter<AdapterForecast.ViewHolder> {

    private List<GetSetForecast> itemLists;
    private Context context;

    public AdapterForecast(List<GetSetForecast> itemLists, Context context){
        this.itemLists = itemLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_info_forecast, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetSetForecast getSetItem = itemLists.get(position);

        Glide.with(context).load("http:" + getSetItem.getIcon()).into(holder.imageView);
        holder.id_date.setText(getSetItem.getDate());
        holder.id_avgtemp_c.setText(getSetItem.getAvgtemp_c() + " °C");
        holder.id_condition_text.setText(getSetItem.getCondition_text());
    }

    @Override
    public int getItemCount() {
        return itemLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView id_date, id_avgtemp_c, id_condition_text;
        public ImageView imageView;

        public ViewHolder(View view){
            super(view);

            id_date = (TextView) view.findViewById(R.id.id_date);
            id_avgtemp_c = (TextView) view.findViewById(R.id.id_avgtemp_c);
            id_condition_text = (TextView) view.findViewById(R.id.id_condition_text);
            imageView = (ImageView) view.findViewById(R.id.imageView);
        }
    }
}
