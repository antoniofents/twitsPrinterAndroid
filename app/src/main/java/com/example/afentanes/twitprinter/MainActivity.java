package com.example.afentanes.twitprinter;

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
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.io.File;
import java.io.FileInputStream;

public class MainActivity extends AppCompatActivity {


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

    @Override
    protected void onRestart() {
        super.onRestart();
        initRecyclerView();
        initListTwits();
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_twits);
        getFileImages();
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

    private void initListTwits() {

        ListView listView = (ListView) findViewById(R.id.twits_available);
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(listView.getContext(), android.R.layout.simple_list_item_1, null,
                TwitsDbReader.TWITS_BASIC_PROJECTION, new int[]{android.R.id.text1}, 0);
        listView.setAdapter(simpleCursorAdapter);
        simpleCursorAdapter.changeCursor(TwitsDbReader.getTwits(this));


    }



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_twits:
                    mRecyclerView.setVisibility(View.GONE);
                    initListTwits();
                    findViewById(R.id.twits_available).setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_printed:
                    initRecyclerView();
                    findViewById(R.id.twits_available).setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        }

    };

}
