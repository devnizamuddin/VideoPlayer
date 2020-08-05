package com.e.videoplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static int VIDEO_PERMISSION_REQUEST_CODE = 1;

    RecyclerView video_list_rv;
    VideoAdapter videoAdapter;
    File directory;
    public static ArrayList<File> fileArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        video_list_rv = findViewById(R.id.video_list_rv);

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this, RecyclerView.VERTICAL, false);
        video_list_rv.setLayoutManager(layoutManager);

        directory = new File("/mnt/");

         checkPermissionForVideo();

    }

    private void checkPermissionForVideo() {

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            //Video Permission Is Already Granted

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        VIDEO_PERMISSION_REQUEST_CODE);
            }
        } else {
            //Video Permission is not granted.
            //Request for permission

            getFile(directory);
            videoAdapter = new VideoAdapter(getApplicationContext(), fileArrayList);
            video_list_rv.setAdapter(videoAdapter);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            //if permission accepted
            getFile(directory);
            videoAdapter = new VideoAdapter(getApplicationContext(), fileArrayList);
            video_list_rv.setAdapter(videoAdapter);
        } else {
            Toast.makeText(this, "Please Accept the permission", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<File> getFile(File directory) {

        File listFiles[] = directory.listFiles();

        if (listFiles != null && listFiles.length > 0) {

            //Directory Found

            for (int i = 0; i < listFiles.length; i++) {

                //========================Adding File to List===================//

                if (listFiles[i].isDirectory()) {

                    //if this is directory call get file recursively

                    getFile(listFiles[i]);

                } else {

                    //If this is a file

                    if (listFiles[i].getName().endsWith(".mp4")) {

                        //MP4 file
                       fileArrayList.add(listFiles[i]);
                    }
                    else {
                        //this is not mp4 file
                    }
                }

            }
        }
        else {
            //Directory Not Found
        }

        return fileArrayList;
    }
}