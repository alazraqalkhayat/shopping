package com.example.firebase_prorject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class storage_activity extends AppCompatActivity {


    private static final int IMAGES_ID = 1;

    private static final int secondID = 2;

    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private TextView mTextViewShowUploads;
    private EditText mEditTextFileName;
    private ImageView mImageView;
    private ProgressBar mProgressBar;

    private Uri mImageUri;


    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);


        mButtonChooseImage = findViewById(R.id.button_choose_image);
        mButtonUpload = findViewById(R.id.button_upload);
        mTextViewShowUploads = findViewById(R.id.text_view_show_uploads);
        mEditTextFileName = findViewById(R.id.edit_text_file_name);
        mImageView = findViewById(R.id.image_view);
        mProgressBar = findViewById(R.id.progress_bar);
/////////////////////////////////////////////////////////////


        ///initial the realTime database and the storage of firebase
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("images");


        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });



        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(getBaseContext(), "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }

            }
        });

        mTextViewShowUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),RetriveImagesActivity.class));
            }
        });
    }

    ///to open the files picker and chose the image
    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

//        startActivity(intent);

        startActivityForResult(intent, IMAGES_ID);
    }

    ///to check the data after coming from the filePicker
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGES_ID) {
            if(resultCode==RESULT_OK && data != null && data.getData() != null){
                mImageUri = data.getData();

                mImageView.setImageURI(mImageUri);
//                Picasso.get().load(item.getUri()).fit().centerCrop().into(imageViewHolder.image);

            }else if(resultCode ==RESULT_CANCELED){
                mImageView.setImageURI(Uri.parse(""));
                Toast.makeText(this, "your process was canceled ", Toast.LENGTH_SHORT).show();
            }


//            Picasso.with(this).load(mImageUri).into(mImageView);
        }
    }

    ///to get the file extension Example : jpg , png ...etc
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        ProgressDialog loading = ProgressDialog.show(this,"Loading",
                "Please wait until loading",true,true);


        ///check if the image is not null
        if (mImageUri != null) {

            ///the path that the image will be uploaded to
            StorageReference fileReference =
                    mStorageRef.child(System.currentTimeMillis()+"."+getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri)

                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    loading.dismiss();
                                    Toast.makeText(getBaseContext(), "Upload successful", Toast.LENGTH_LONG).show();

                                    loading.show();

//                            String uploadId = mDatabaseRef.push().getKey();
                                    Map<String,Object> map=new HashMap<>();

                                    map.put("name",mEditTextFileName.getText().toString());
                                    map.put("uri",uri.toString());

                                    mDatabaseRef.child(String.valueOf(System.currentTimeMillis())).updateChildren(map)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(getBaseContext(), "Upload to database successfully", Toast.LENGTH_LONG).show();

                                                    loading.dismiss();
                                                }
                                            });

                                }
                            });

                            ///push the data to the realTime database by DB_ID
//                            mDatabaseRef.child(uploadId).setValue(upload);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
//                            mProgressBar.setProgress((int) progress);
                        }
                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            String downloadUrl = task.getResult().getStorage().getDownloadUrl().toString();
                            System.out.println("asfasdf asdf "+downloadUrl);
                            Toast.makeText(getBaseContext(), "Image uploaded successfully to storage", Toast.LENGTH_SHORT).show();

                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
}