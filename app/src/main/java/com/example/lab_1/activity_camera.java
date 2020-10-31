package com.example.lab_1;



import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

public class activity_camera extends AppCompatActivity {

    Button back_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        back_main = (Button) findViewById(R.id.back_button);

        int cameraId = getIntent().getIntExtra("cameraId", 0);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        switch (cameraId) {
            case 0:
                intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                startActivityForResult(intent,0);
                break;
            case 1:
                startActivityForResult(intent,0);
                break;
            default:
                break;
        }

        back_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_camera.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");

        Intent intent = new Intent(activity_camera.this, view_photo_activity.class);
        intent.putExtra("bitmap", bitmap);
        startActivity(intent);
    }

}