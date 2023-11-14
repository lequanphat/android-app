package com.example.mystories;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class SecondFragment extends Fragment {
    private RecyclerView recyclerView;
    private ImageView imageView;
    private ImageButton saveButton;
    private Bitmap image = null;
    private EditText titleEdt, contentEdt;
    private static DatabaseHelper db;
    private MyViewModel myViewModel;
    private Button loadImageBtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(getContext());
        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
    }
    public void initView(View root){
        imageView = root.findViewById(R.id.image);
        saveButton = root.findViewById(R.id.saveBtn);
        titleEdt = root.findViewById(R.id.title_edt);
        contentEdt = root.findViewById(R.id.content_edt);
        loadImageBtn = root.findViewById(R.id.loadImage);
    }
    public void initEvent(View root){
        loadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntentLauncher.launch(takePictureIntent);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),image.toString(),Toast.LENGTH_SHORT).show();
                String title = titleEdt.getText().toString().trim();
                String content = contentEdt.getText().toString().trim();
                Story story = new Story(title, content, image, getCurrentTime());
                db.save(story);
                myViewModel.setData(story);
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Activity activity = getActivity();
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.second_fragment, container, false);
        initView(root);
        initEvent(root);
        return root;
    }
    public ActivityResultLauncher<Intent> cameraIntentLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if (data != null) {
                            image = (Bitmap) data.getExtras().get("data");
                            imageView.setImageBitmap(image);
                            saveImageToStorage(image);
                        }
                    }
                }
            });

    private String saveImageToStorage(Bitmap bitmapImage) {
        String savedImagePath = null;

        // Tạo thư mục để lưu trữ ảnh
        String imageFileName = "IMG_" + System.currentTimeMillis() + ".jpg";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera");

        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        try {
            File imageFile = new File(storageDir, imageFileName);
            savedImagePath = imageFile.getAbsolutePath();

            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            Toast.makeText(getContext(), "ok", Toast.LENGTH_SHORT).show();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return savedImagePath;
    }
    public String getCurrentTime(){
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(currentDate);
    }
}