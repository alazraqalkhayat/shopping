package com.example.firebase_prorject;

import android.content.Context;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Image_items_adapter extends RecyclerView.Adapter<Image_items_adapter.ImageViewHolder> {
    private Context context;
    private List<Image_items_model> image_items;

    public  class ImageViewHolder extends  RecyclerView.ViewHolder{
        public TextView name;
        public ImageView image;

        public ImageViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.image_items_name);
            image=itemView.findViewById(R.id.image_items_image);
        }
    }

    public Image_items_adapter(Context context, List<Image_items_model> image_items) {
        this.context = context;
        this.image_items = image_items;
    }


    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.image_items,viewGroup,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder imageViewHolder, int i) {
        Image_items_model item=image_items.get(i);
        imageViewHolder.name.setText(item.name);
        System.out.println("the image is "+ item.getUri());

//        imageViewHolder.image.setImageURI(Uri.parse(item.uri));
        Picasso.get().load(item.getUri()).fit().centerCrop().into(imageViewHolder.image);


    }

    @Override
    public int getItemCount() {
        return image_items.size();
    }



}
