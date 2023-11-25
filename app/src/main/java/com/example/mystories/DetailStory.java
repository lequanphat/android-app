package com.example.mystories;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DetailStory extends AppCompatActivity{
    private static DatabaseHelper db;
    private EditText titleEdt, contentEdt;
    private ImageView imageView;

    private ImageButton updateBtn;
    private Button loadImageBtn;
    private Bitmap imageData;
    private String idData;
    private MyViewModel myViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(this);
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        setContentView(R.layout.activity_detail_story);
        initViews();
        getDataFromRoot();
        initEvents();
    }
    @SuppressLint("WrongViewCast")
    public void initViews(){
        titleEdt =findViewById(R.id.title_edt);
        contentEdt =findViewById(R.id.content_edt);
        imageView = findViewById(R.id.image);
        updateBtn = findViewById(R.id.updateButton);
        loadImageBtn = findViewById(R.id.loadImage);
    }
    public void initEvents(){
        updateBtn.setOnClickListener( v -> {
                handleStoryUpdate();
        });
        loadImageBtn.setOnClickListener(v -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntentLauncher.launch(takePictureIntent);
        });


    }
    public void getDataFromRoot(){
        Intent intent = getIntent();
        Bundle bundle =  intent.getExtras();
        String title = bundle.getString("title");
        String content = bundle.getString("content");
        Bitmap rootImage = intent.getParcelableExtra("image");
        titleEdt.setText(title);
        contentEdt.setText(content);
        imageView.setImageBitmap(rootImage);
        imageData = rootImage;
        idData = bundle.getString("id");
    }
    public void handleStoryUpdate(){
        String title = titleEdt.getText().toString().trim();
        String content = contentEdt.getText().toString().trim();
        if(handleCheckStoryUpdated(title,content)){
            Story story = new Story(idData, title, content, imageData, "");
            db.update(story);
            myViewModel.setData(story);
            finish();
        }
    }
    public boolean handleCheckStoryUpdated(String title, String content){
        if(title.equals("")){
            MyDialog.showMessage(this, "Vui lòng đặt tiêu đề cho story!");
            return false;
        }
        if(content.equals("")){
            MyDialog.showMessage(this, "Vui lòng viết nội dung cho story!");
            return false;
        }
        return true;
    }
    public ActivityResultLauncher<Intent> cameraIntentLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if (data != null) {
                            imageData = (Bitmap) data.getExtras().get("data");
                            imageView.setImageBitmap(imageData);
                            saveImageToStorage(imageData);
                        }
                    }
                }
            });

    private void saveImageToStorage(Bitmap bitmapImage) {
        String imageFileName = "IMG_" + System.currentTimeMillis() + ".jpg";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera");

        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        try {
            File imageFile = new File(storageDir, imageFileName);
            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}