package com.example.myparks.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myparks.R;
import com.example.myparks.modle.Images;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ImageSlider> {

    private List<Images> imagesList;
    private int pos;
    private TextView name_of_image ;


    public int getPos() {
        return pos;
    }


    public ViewPagerAdapter(List<Images> imagesList) {
        this.imagesList = imagesList;
    }

    @NonNull
    @Override
    public ImageSlider onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_pager_row,parent,false);





        return new ImageSlider(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSlider holder, int position) {

        Log.d("okok", "onBindViewHolder: " +imagesList.get(position).getTitle());
        Log.d("okok", "onBindViewHolder: " +position);
        String s = imagesList.get(position).getTitle();
        name_of_image.setText(s);

        Picasso.get()
                .load(imagesList.get(position).getUrl()) //
                .fit()
                .error(android.R.drawable.stat_notify_error) // image is its error
                .placeholder(android.R.drawable.stat_sys_download)  // image when is download
                .into(holder.imageView);

        //                .centerCrop()





    }


    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    public class ImageSlider extends RecyclerView.ViewHolder{

        public ImageView imageView;



        @SuppressLint("SetTextI18n")
        public ImageSlider(@NonNull View itemView) {
            super(itemView);
            // now we have image view
            name_of_image = itemView.findViewById(R.id.Text_of_image);
            imageView = itemView.findViewById(R.id.view_pager_imageview);

        }
    }

}
