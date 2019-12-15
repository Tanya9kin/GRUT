package com.example.grut;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PlantListRecyclerAdapter extends RecyclerView.Adapter<PlantListRecyclerAdapter.ViewHolder> {

    private OnItemClickListener mlistener;

    public interface OnItemClickListener {
        void onNameClick(int position, View itemView);
        //boolean onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener = listener;
    }


    Context context;
    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_itemName;
        public ImageView iv_drop1;
        public ImageView iv_drop2;
        public ImageView iv_drop3;

        public ViewHolder(@NonNull final View itemView, final PlantListRecyclerAdapter.OnItemClickListener listener) {
            super(itemView);
            tv_itemName = itemView.findViewById(R.id.plant_name);
            iv_drop1 = itemView.findViewById(R.id.plant_drop_1);
            iv_drop2 = itemView.findViewById(R.id.plant_drop_2);
            iv_drop3 = itemView.findViewById(R.id.plant_drop_3);

//            itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    if(listener != null){
//                        int position = getAdapterPosition();
//                        if(position != RecyclerView.NO_POSITION){
//                            listener.onLongClick(position);
//                            return true;
//                        }
//                    }
//                    return false;
//                }
//            });
            tv_itemName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
//                        int position = getAdapterPosition();
//                        if(position != RecyclerView.NO_POSITION){
//                            listener.onPlusClick(position,itemView);
//                        }
                    }
                }
            });
        }
    }

    @NonNull
    //@Override
    public PlantListRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.plant_item, viewGroup, false);
        PlantListRecyclerAdapter.ViewHolder holder = new PlantListRecyclerAdapter.ViewHolder(view, mlistener);
        return holder;
    }

    //@Override
    public void onBindViewHolder(@NonNull PlantListRecyclerAdapter.ViewHolder viewHolder, int i) {

    }

    //@Override
    public int getItemCount() {
        return 0;
    }
}
