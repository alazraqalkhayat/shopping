package com.example.firebase_prorject;

import android.content.Context;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Image_items_adapter extends RecyclerView.Adapter<Image_items_adapter.ImageViewHolder> {
    private Context context;
    private List<Image_items_model> image_items;



    public Image_items_adapter(Context context, List<Image_items_model> image_items) {
        this.context = context;
        this.image_items = image_items;
    }




    public  class ImageViewHolder extends  RecyclerView.ViewHolder{
        public TextView name;
        public ImageView image;

        public ImageViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.image_items_name);
            image=itemView.findViewById(R.id.image_items_image);

        }
    }


    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View cardView= LayoutInflater.from(context).inflate(R.layout.image_items,viewGroup,false);
        return new ImageViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder imageViewHolder, int i) {
        Image_items_model currentItem=image_items.get(i);


        imageViewHolder.name.setText(currentItem.getName());
        System.out.println("the name is "+ currentItem.getName());
//        imageViewHolder.image.setImageURI(Uri.parse(currentItem.uri));
        Picasso.get().load(currentItem.getUri()).fit().centerInside().into(imageViewHolder.image);
        System.out.println("the image is "+ currentItem.getUri());



    }

    @Override
    public int getItemCount() {

        return image_items.size();
    }



}
