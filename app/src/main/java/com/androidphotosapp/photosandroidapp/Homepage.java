package com.androidphotosapp.photosandroidapp;

import android.content.Context;
import android.content.Intent;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.view.LayoutInflater;
import androidx.appcompat.app.AlertDialog;
import android.widget.EditText;
import android.content.DialogInterface;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.*;

import model.Album;
import model.CustomSpinner;
import model.AlbumManager;


public class Homepage extends AppCompatActivity {

    private FloatingActionButton addAlbumBtn;
    private CustomSpinner spinner;
    private Button searchButton;
    final Context c = this;
    String albumName;

    public static AlbumManager manager = new AlbumManager();
    File albumsfile = new File("/data/data/com.androidphotosapp.photosandroidapp/files/albums.dat");
    ListView testList;
    private static List<String> albums = new ArrayList<String>();
    private ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            manager = AlbumManager.deserialize();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);


        if(!albumsfile.exists()) {
            Context context = this;
            File file = new File(context.getFilesDir(), "albums.dat");
            try {
                file.createNewFile();
            } catch (IOException e) {

            }
        }

        populateAlbumList();
        testList = (ListView) findViewById(R.id.albumsList);
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.album_list_view, R.id.textView, albums);
        testList.setAdapter(arrayAdapter);


        addAlbumBtn = (FloatingActionButton) findViewById(R.id.addAlbumBtn);
        addAlbumBtn.setOnClickListener (new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.create_album, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);
                final EditText albumNamefromDialog = (EditText) mView.findViewById(R.id.userInputDialog);

                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                String albumName = albumNamefromDialog.getText().toString();
                                albumName = albumNamefromDialog.getText().toString();

                                for(int i = 0; i < manager.getAlbums().size(); i++){
                                    if(manager.getAlbums().get(i).getAlbumTitle().equals(albumName)){
                                        Toast.makeText(getApplicationContext(), "Duplicate Album Names Not Allowed! Try Another Name", Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                }

                                Album newAlbum = new Album(albumName);
                                manager.addAlbum(newAlbum);

                                try {
                                    AlbumManager.serialize(manager);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                testList = (ListView) findViewById(R.id.albumsList);
                                populateAlbumList();
                                arrayAdapter.notifyDataSetChanged();
                                testList.setAdapter(arrayAdapter);

                            }
                        })

                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }
        });

        spinner = (CustomSpinner) findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> itemAdapter = ArrayAdapter.createFromResource(this, R.array.album_options,android.R.layout.simple_spinner_item);
        itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setPrompt("Select One:");
        spinner.setAdapter(itemAdapter);
        spinner.setVisibility(View.INVISIBLE);
        testList.setLongClickable(true);


        testList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                spinner.setVisibility(View.VISIBLE);

                final int indexOfAlbum = i;

                spinner.setOnItemSelectedEvenIfUnchangedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        if(i == 1) {

                            LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                            View mView = layoutInflaterAndroid.inflate(R.layout.create_album, null);
                            AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                            alertDialogBuilderUserInput.setView(mView);
                            TextView title = (TextView) mView.findViewById(R.id.title);
                            title.setText("Rename Album");
                            final EditText albumNameinDialog = (EditText) mView.findViewById(R.id.userInputDialog);
                            albumNameinDialog.setText(manager.getAlbums().get(indexOfAlbum).getAlbumTitle());
                            alertDialogBuilderUserInput
                                    .setCancelable(false)
                                    .setPositiveButton("Rename", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogBox, int id) {
                                            String newname = albumNameinDialog.getText().toString();
                                            manager.getAlbums().get(indexOfAlbum).setAlbumTitle(newname);

                                            //serialize and refresh list
                                            try {
                                                AlbumManager.serialize(manager);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }

                                            testList = (ListView) findViewById(R.id.albumsList);
                                            populateAlbumList();
                                            arrayAdapter.notifyDataSetChanged();
                                            testList.setAdapter(arrayAdapter);


                                            Toast.makeText(Homepage.this, "Album Successfully Renamed", Toast.LENGTH_SHORT).show();

                                        }
                                    })

                                    .setNegativeButton("Cancel",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialogBox, int id) {
                                                    dialogBox.cancel();
                                                }
                                            });

                            AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                            alertDialogAndroid.show();

                        }

                        else if(i == 2) {
                            manager.removeAlbum(indexOfAlbum);
                            try {
                                AlbumManager.serialize(manager);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            testList = (ListView) findViewById(R.id.albumsList);
                            populateAlbumList();
                            arrayAdapter.notifyDataSetChanged();
                            testList.setAdapter(arrayAdapter);
                            Toast.makeText(Homepage.this, "Album Successfully Deleted", Toast.LENGTH_SHORT).show();
                        }

                        spinner.setVisibility(View.INVISIBLE); //hide the spinner
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                return true;
            }
        });



        testList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Album album = manager.getAlbums().get(i);
                manager.setCurrentAlbum(album);

                Intent singlealbum = new Intent(Homepage.this, SingleAlbumPage.class);
                startActivity(singlealbum);

            }
        });

        searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchPage = new Intent(Homepage.this, SearchPage.class);
                startActivity(searchPage);
            }
        });

    }

    private static void populateAlbumList() {
        albums.clear();

        for(int i = 0; i < manager.getAlbums().size(); i++) {
            albums.add(manager.getAlbums().get(i).getAlbumTitle());
        }
    }

}
