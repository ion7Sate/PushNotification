package com.example.lab_1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.chip.Chip;

public class MainActivity extends AppCompatActivity {
    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_PERM_CODE1 = CAMERA_PERM_CODE;
    public static final int CAMERA_REQUEST_CODE = 102;
    EditText inputText;
    Button btnNotification;
    private Handler delayTime = new Handler();
    Button btnTakePicture, btnBackgroundColor;
    Button FrontCamera, BackCamera;
    RadioGroup radioGroupBox;
    int cameraId;
    MediaPlayer player;
    Button btnPlayMusic;
    boolean play;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputText = (EditText) findViewById(R.id.inputText);
        createNotificationChanel();
        btnNotification = (Button) findViewById(R.id.btn_notification);
        btnTakePicture = (Button) findViewById(R.id.btnTakePicture);
        radioGroupBox = (RadioGroup) findViewById(R.id.radioGroup);
        btnPlayMusic = findViewById(R.id.playMusic);
        String message = "here is your notification";
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "push_ntf_chanel")
                .setSmallIcon(R.drawable.ic_message)
                .setContentTitle("Laboratorul 1")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        final Runnable mToastRunnable = new Runnable() {
            @Override
            public void run() {
                notificationManager.notify(0, builder.build());
            }
        };
        player = MediaPlayer.create(this,R.raw.music);
        play = false;
        btnPlayMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!play){
                    player.start();
                    btnPlayMusic.setText("Pause Music");
                    play = true;
                }else{
                    player.pause();
                    btnPlayMusic.setText("Play Music");
                    play = false;
                }


            }
        });

       /* btnPlayMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    player.start();
                    btnPlayMusic.setText("Pause Music");
                } else {
                    player.pause();
                    btnPlayMusic.setText("Play Music");
                }
            }
        });*/






        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You will receive your notification in 10 seconds", Toast.LENGTH_SHORT).show();
                delayTime.postDelayed(mToastRunnable, 10000);

            }
        });

        FrontCamera = (Button) findViewById(R.id.radBtn_frontCamera);
        BackCamera = (Button) findViewById(R.id.radBtn_backCamera);

        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermissions();
            }
        });

        Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("message", message);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,
                0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
    }


    public void searchOnGoogle(View view){
        Uri uri = Uri.parse("http://www.google.com/search?q=" + inputText.getText().toString());
        Intent btn_searchIntent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(btn_searchIntent);
    }

    private void createNotificationChanel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "ntf_chanel_1";
            String description = "Push Notification chanel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("push_ntf_chanel", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
        private void askCameraPermissions() {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE1);
            }else{
                openCamera();
            }
        }
    private void openCamera() {

        int radioButtonId = radioGroupBox.getCheckedRadioButtonId();
        radioGroupBox.clearCheck();
        if(radioButtonId != -1 ) {
            RadioButton selected1 = findViewById(radioButtonId);


            if(selected1 == FrontCamera){
                cameraId = 0;

            } else if(selected1 == BackCamera) {
                cameraId = 1;

            }
            Intent intent = new Intent(MainActivity.this, activity_camera.class);
            intent.putExtra("cameraId", cameraId);
            startActivityForResult(intent, 0);

        }else {
            Toast.makeText(getApplicationContext(),"Select your Camera!!!",Toast.LENGTH_SHORT).show();
        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERM_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openCamera();
            }else{
                Toast.makeText(MainActivity.this, "Camera Permission is Required to use Camera!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE){
            Bitmap image = (Bitmap) data.getExtras().get("data");

        }
    }


}