package com.example.project1;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ImageViewHolder> {



    private int[] images;
    String[] allProducts = {"Black_Hoodie","Grey_Hoodie","Green_Hoodie",
            "Brown_Sweatshirt","Black_Sweatshirt","White_Sweatshirt",
            "White_Sleeves","Brown_Sleeves","Black_Sleeves",
            "White_Tee","Black_Tee","Gold_Tee"};
    public RecyclerAdapter(int[] images){
        this.images = images;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout,parent,false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view);

        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, int position) {
        int images_id = images[position];
        holder.Images.setImageResource(images_id);
        holder.ImageTxt.setText(allProducts[position]);


    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView Images;
        TextView ImageTxt;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            Images = itemView.findViewById(R.id.album);
            ImageTxt = itemView.findViewById(R.id.album_title);

        }
    }

}
