package com.example.myparks.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myparks.R;
import com.example.myparks.modle.Park;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

public class ParkRecyclerViewAdapter extends RecyclerView.Adapter<ParkRecyclerViewAdapter.ViewHolder> {

    private final List<Park> parkList;
    private final OnParkClickListener parkClickListener;

    public ParkRecyclerViewAdapter(List<Park> parkList ,OnParkClickListener parkClickListener) {
        this.parkList = parkList;
        this.parkClickListener = parkClickListener;

    }


    @NonNull
    @Override
    public ParkRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.park_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Park park = parkList.get(position);

        holder.parkName.setText(park.getName());
        holder.parkType.setText(park.getDesignation());
        holder.parkState.setText(park.getStates());
        Random rand = new Random();


        if (park.getImages().size() > 0){
            int n = 0;//rand.nextInt(park.getImages().size()-1);

            Picasso.get()
                    .load(park.getImages().get(n).getUrl()) // gte the url from json
                    .placeholder(android.R.drawable.stat_sys_download)  // image when is download
                    .error(android.R.drawable.stat_notify_error) // image is its error
                    .resize(100,100)// the size of the image
                    .into(holder.parkImage); // to what place to put it\

        }



    }

    // return the size of the list
    @Override
    public int getItemCount() {
        return parkList.size();
    }

    // from the park row layout
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView parkImage;
        public TextView parkName;
        public TextView parkType;
        public TextView parkState;
        OnParkClickListener onParkClickListener;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parkImage = itemView.findViewById(R.id.row_park_Imageview);
            parkName = itemView.findViewById(R.id.row_park_name_textview);
            parkType = itemView.findViewById(R.id.row_park_type_textview);
            parkState = itemView.findViewById(R.id.row_park_state_textview);
            this.onParkClickListener = parkClickListener;
            itemView.setOnClickListener(this); // to the ParkClickListener .. what the class implements

        }

        @Override
        public void onClick(View view) {
            Park currPark = parkList.get(getAdapterPosition()); // say witch row we tap
            onParkClickListener.onParkClick(currPark); // what we need to pass
        }
    }
}
