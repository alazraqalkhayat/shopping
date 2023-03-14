package com.example.firebase_prorject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RetriveImagesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private Image_items_adapter adapter;
    private List<Image_items_model> image_items_modelList;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrive_images);
        recyclerView=findViewById(R.id.recycleView);
//        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        image_items_modelList=new ArrayList<>();

        databaseReference= FirebaseDatabase.getInstance().getReference("images");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot d : snapshot.getChildren()){
                    image_items_modelList.add(d.getValue(Image_items_model.class));
                }
                adapter=new Image_items_adapter(getBaseContext(),image_items_modelList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RetriveImagesActivity.this, "the process is canceled", Toast.LENGTH_SHORT).show();
            }
        });

    }
}