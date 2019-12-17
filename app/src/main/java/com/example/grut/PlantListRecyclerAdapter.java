package com.example.grut;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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

    public PlantListRecyclerAdapter(Context context, List<Plant_item> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.plant_item, parent,false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        holder.tv_plantName.setText(mData.get(i).getName());
        //holder.im_currTemp.setImageDrawable(getDrawable(R.drawable.ic_020_thermometer));
        //TODO add the images dynamically according to stats
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static  class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_plantName;
        private ImageView im_currSun;
        private ImageView im_currMoist;
        private ImageView im_currTemp;
        private ImageView im_currState;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_plantName = itemView.findViewById(R.id.plant_name);
            im_currMoist = itemView.findViewById(R.id.plant_moist);
            im_currSun = itemView.findViewById(R.id.plant_sun);
            im_currTemp = itemView.findViewById(R.id.plant_temp);
            im_currState = itemView.findViewById(R.id.plant_state);
        }
    }
}

//
//public class PlantListRecyclerAdapter extends RecyclerView.Adapter<PlantListRecyclerAdapter.ViewHolder> {
//
//    private OnItemClickListener mlistener;
//
//    public interface OnItemClickListener {
//        void onNameClick(int position, View itemView);
//        //boolean onLongClick(int position);
//    }
//
//    public void setOnItemClickListener(OnItemClickListener listener){
//        mlistener = listener;
//    }
//
//
//    Context context;
//    public class MyViewHolder extends RecyclerView.ViewHolder{
//
//        public TextView tv_itemName;
//        public ImageView iv_drop1;
//        public ImageView iv_drop2;
//        public ImageView iv_drop3;
//
//        public MyViewHolder(@NonNull final View itemView, final PlantListRecyclerAdapter.OnItemClickListener listener) {
//            super(itemView);
//            tv_itemName = itemView.findViewById(R.id.plant_name);
//            iv_drop1 = itemView.findViewById(R.id.plant_drop_1);
//            iv_drop2 = itemView.findViewById(R.id.plant_drop_2);
//            iv_drop3 = itemView.findViewById(R.id.plant_drop_3);
//
////            itemView.setOnLongClickListener(new View.OnLongClickListener() {
////                @Override
////                public boolean onLongClick(View v) {
////                    if(listener != null){
////                        int position = getAdapterPosition();
////                        if(position != RecyclerView.NO_POSITION){
////                            listener.onLongClick(position);
////                            return true;
////                        }
////                    }
////                    return false;
////                }
////            });
//            tv_itemName.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(listener != null){
////                        int position = getAdapterPosition();
////                        if(position != RecyclerView.NO_POSITION){
////                            listener.onPlusClick(position,itemView);
////                        }
//                    }
//                }
//            });
//        }
//    }
//
////    @NonNull
//    //@Override
////    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
////        context = viewGroup.getContext();
////        View view = LayoutInflater.from(context).inflate(R.layout.plant_item, viewGroup, false);
////        PlantListRecyclerAdapter.MyViewHolder holder = new PlantListRecyclerAdapter.MyViewHolder(view, mlistener);
////        return holder;
////    }
//
//    //@Override
//    public void onBindViewHolder(@NonNull PlantListRecyclerAdapter.MyViewHolder viewHolder, int i) {
//
//    }
//
//    //@Override
//    public int getItemCount() {
//        return 0;
//    }
//}
