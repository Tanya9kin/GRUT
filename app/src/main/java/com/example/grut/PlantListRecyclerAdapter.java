package com.example.grut;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import static android.support.v7.content.res.AppCompatResources.getDrawable;

public class PlantListRecyclerAdapter extends RecyclerView.Adapter<PlantListRecyclerAdapter.MyViewHolder> {

    Context context;
    List<Plant_item> mData;

    /**
     * For the smiley calculation
     */
    static final int DELTA_TEMP = 5;
    static final int DELTA_MOIST = 30;
    static final int DELTA_LIGHT = 20;

    static final int PLANT_HAPPY = 1;
    static final int PLANT_OK = 0;
    static final int PLANT_SAD = -1;

    static final int VALUE_TOO_HIGH = 1;
    static final int VALUE_OPTIMAL = 0;
    static final int VALUE_TOO_LOW = -1;

    public static int getCurrState(int currValue, int optimalValue, int delta){
        if((currValue >= optimalValue-delta) && (currValue <= optimalValue+delta)){
            return VALUE_OPTIMAL;
        } else if(currValue > optimalValue+delta){
            return VALUE_TOO_HIGH;
        } else{ //(currValue < optimalValue-delta)
            return VALUE_TOO_LOW;
        }
    }

    public static int isPlantHappy(int tempState, int moistState, int lightState){
        if(tempState == VALUE_OPTIMAL && moistState == VALUE_OPTIMAL && lightState == VALUE_OPTIMAL){
            return PLANT_HAPPY;
        } else if((tempState != VALUE_OPTIMAL && moistState == VALUE_OPTIMAL && lightState == VALUE_OPTIMAL) ||
                (tempState == VALUE_OPTIMAL && moistState != VALUE_OPTIMAL && lightState == VALUE_OPTIMAL) ||
                (tempState == VALUE_OPTIMAL && moistState == VALUE_OPTIMAL && lightState != VALUE_OPTIMAL)){
            return PLANT_OK;
        } else {
            return PLANT_SAD;
        }
    }

    private OnItemClickListener mlistener;

    public interface OnItemClickListener {
        void onItemClick(int position, View itemView);
        //boolean onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener = listener;
    }


    public PlantListRecyclerAdapter(Context context, List<Plant_item> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public PlantListRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.plant_item, parent,false);
        PlantListRecyclerAdapter.MyViewHolder vHolder = new PlantListRecyclerAdapter.MyViewHolder(v,mlistener);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int i) {
        Plant_item plantData = mData.get(i);
        holder.tv_plantName.setText(plantData.getName());

        if(plantData.getCurrTemp() == null || plantData.getCurrLight() == null || plantData.getCurrMoist() == null){
            holder.im_currTemp.setImageResource(R.drawable.ic_025_thinking);
            holder.im_currMoist.setImageResource(R.drawable.ic_025_thinking);
            holder.im_currLight.setImageResource(R.drawable.ic_025_thinking);
            holder.im_currState.setImageResource(R.drawable.ic_025_thinking);
            return;
        }
        int currTemp = plantData.getCurrTemp();
        int currLight = plantData.getCurrLight();
        int currMoist = plantData.getCurrMoist();

        int optTemp = plantData.getOptTemp();
        int optLight = plantData.getOptLight();
        int optMoist = plantData.getOptMoist();

        int tempEmoji = getCurrState(currTemp, optTemp, DELTA_TEMP);
        int moistEmoji = getCurrState(currMoist, optMoist, DELTA_MOIST);
        int lightEmoji = getCurrState(currLight, optLight, DELTA_LIGHT);

        //temp
        switch(tempEmoji){
            case VALUE_TOO_HIGH:
                holder.im_currTemp.setImageResource(R.drawable.ic_007_super_hot);
                break;
            case VALUE_OPTIMAL:
                holder.im_currTemp.setImageResource(R.drawable.ic_017_warm_1);
                break;
            case VALUE_TOO_LOW:
                holder.im_currTemp.setImageResource(R.drawable.ic_027_cold);
                break;
        }
        //soil moist
        switch(moistEmoji){
            case VALUE_TOO_HIGH:
                holder.im_currMoist.setImageResource(R.drawable.ic_025_flood);
                break;
            case VALUE_OPTIMAL:
                holder.im_currMoist.setImageResource(R.drawable.ic_020_drop);
                break;
            case VALUE_TOO_LOW:
                holder.im_currMoist.setImageResource(R.drawable.ic_050_drought);
                break;
        }
        //light
        switch(lightEmoji){
            case VALUE_TOO_HIGH:
                holder.im_currLight.setImageResource(R.drawable.ic_005_hot);
                break;
            case VALUE_OPTIMAL:
                holder.im_currLight.setImageResource(R.drawable.ic_044_sun);
                break;
            case VALUE_TOO_LOW:
                holder.im_currLight.setImageResource(R.drawable.ic_033_super_cloudy);
                break;
        }

        //set global plant state emoji
        int plantState = isPlantHappy(tempEmoji, moistEmoji, lightEmoji);

        switch(plantState){
            case PLANT_HAPPY:
                holder.im_currState.setImageResource(R.drawable.ic_006_full);
                break;
            case PLANT_OK:
                holder.im_currState.setImageResource(R.drawable.ic_004_expressionless);
                break;
            case PLANT_SAD:
                holder.im_currState.setImageResource(R.drawable.ic_003_dizzy);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static  class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_plantName;
        public ImageView im_currLight;
        public ImageView im_currMoist;
        public ImageView im_currTemp;
        public ImageView im_currState;

        public MyViewHolder(@NonNull final View itemView, final PlantListRecyclerAdapter.OnItemClickListener listener) {
            super(itemView);

            tv_plantName = itemView.findViewById(R.id.plant_name);
            im_currMoist = itemView.findViewById(R.id.plant_moist);
            im_currLight = itemView.findViewById(R.id.plant_sun);
            im_currTemp = itemView.findViewById(R.id.plant_temp);
            im_currState = itemView.findViewById(R.id.plant_state);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position,itemView);
                        }
                    }
                }
            });
        }
    }
}