package com.example.afentanes.twitprinter;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static android.R.id.list;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_twits);
        mAdapter = new TwitsPrintedViewAdapter(getFileImages());
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }


    private Bitmap[] getFileImages(){
        ContextWrapper wrapper = new ContextWrapper(getApplicationContext());
        Bitmap [] images=null;
        File twitsDir = new File(wrapper.getDir(getResources().getString(R.string.twit_images_dir), MODE_PRIVATE), "");
        if(twitsDir.isDirectory()){
            File[] files = twitsDir.listFiles();
            images= new Bitmap[files.length];
            for(int i=0; i<files.length;i++){
                images[i]=getImage(files[i]);
            }
        }
        return images;
    }

    private Bitmap getImage(File file ){
        Bitmap b = null;
        try {
            b= BitmapFactory.decodeStream(new FileInputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }


    //if we would like to load the images  from bind holder in the twits adapter
   /* private String[] getFiles(){
        ContextWrapper wrapper = new ContextWrapper(getApplicationContext());
        String [] fileNames=null;
        File twitsDir = new File(wrapper.getDir(getResources().getString(R.string.twit_images_dir), MODE_PRIVATE), "");
        if(twitsDir.isDirectory()){
            File[] files = twitsDir.listFiles();
            fileNames = new String[files.length];
            for(int i=0; i<files.length;i++){
                fileNames[i]=files[i].getName();
            }

        }

        return fileNames;
    }*/



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_twits:
                //    loadImageFromStorage("");
                    return true;
                case R.id.navigation_printed:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
            }
            return false;
        }

    };

}
