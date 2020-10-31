package com.example.lab_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

public class view_photo_activity extends AppCompatActivity {

    ImageView _img_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo);

        _img_view = (ImageView) findViewById(R.id.img_camera1);

        if(getIntent().hasExtra("bitmap")) {
            Bitmap bitmap = (Bitmap) getIntent().getParcelableExtra("bitmap");
            _img_view.setImageBitmap(bitmap);
        }

    }
}