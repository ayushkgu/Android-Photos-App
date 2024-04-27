package com.androidphotosapp.photosandroidapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Album;
import model.PhotoGridAdapter;
import model.PhotoGridAdapter2;
import model.Photo;
import model.AlbumManager;


public class SingleAlbumPage extends AppCompatActivity {

    private static List<Photo> photosInAlbum = new ArrayList<Photo>();
    private FloatingActionButton addPhotoBtn;
    private static final int READ_REQUEST_CODE = 42;
    private PhotoGridAdapter2 imageAdapter;
    private Button deletePhotoBtn, movePhotoBtn;
    final Context c = this;
    GridView gridview;

    private static final String TAG = "SingleAlbumPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_album_page);

        TextView AlbumNameText = (TextView) findViewById(R.id.albumName);
        AlbumNameText.setText(Homepage.manager.getCurrentAlbum().getAlbumTitle());

        // Clear the list of photos before populating it
        photosInAlbum.clear();

        // Populate the list of photos in the album
        populatePhotosList();

        gridview = (GridView) findViewById(R.id.gridView1);
        imageAdapter = new PhotoGridAdapter2(this, photosInAlbum);
        gridview.setAdapter(imageAdapter);

        addPhotoBtn = (FloatingActionButton) findViewById(R.id.addPhotoBtn);
        addPhotoBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                performFileSearch();

            }

            public void performFileSearch() {

                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

                intent.addCategory(Intent.CATEGORY_OPENABLE);

                intent.setType("image/*");

                startActivityForResult(intent, READ_REQUEST_CODE);
            }

        });

        deletePhotoBtn = (Button) findViewById(R.id.deletePhotoBtn);
        movePhotoBtn = (Button) findViewById(R.id.movePhotoBtn);
        deletePhotoBtn.setVisibility(View.INVISIBLE);
        movePhotoBtn.setVisibility(View.INVISIBLE);
        gridview.setLongClickable(true);


        gridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                deletePhotoBtn.setVisibility(View.VISIBLE);
                movePhotoBtn.setVisibility(View.VISIBLE);
                final int photoindex = i;
                final Photo photo_at_pos = Homepage.manager.getCurrentAlbum().getPhotos().get(photoindex);

                deletePhotoBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Homepage.manager.getCurrentAlbum().removePhoto(photoindex);

                        Toast.makeText(SingleAlbumPage.this, "Photo deleted successfully", Toast.LENGTH_SHORT).show();

                        try {
                            AlbumManager.serialize(Homepage.manager);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        gridview = (GridView) findViewById(R.id.gridView1);
                        populatePhotosList();
                        imageAdapter.notifyDataSetChanged();
                        gridview.setAdapter(imageAdapter);

                        deletePhotoBtn.setVisibility(View.INVISIBLE);
                        movePhotoBtn.setVisibility(View.INVISIBLE);

                    }
                });

                movePhotoBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                        View mView = layoutInflaterAndroid.inflate(R.layout.create_album, null);
                        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                        alertDialogBuilderUserInput.setView(mView);
                        TextView title = (TextView) mView.findViewById(R.id.title);
                        title.setText("Move Photo");
                        final EditText albumNameinDialog = (EditText) mView.findViewById(R.id.userInputDialog);
                        alertDialogBuilderUserInput
                                .setCancelable(false)
                                .setPositiveButton("Move", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        String albumname = albumNameinDialog.getText().toString();
                                        String photopath = photo_at_pos.getPhotoPath();

                                        String toastmsg = doesAlbumandPhotoexist(albumname, photopath);
                                        if(toastmsg != "good"){
                                            Toast.makeText(getApplicationContext(), toastmsg, Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                        int destinationalbumindex = 0;
                                        for(int i = 0; i < Homepage.manager.getAlbums().size(); i++){
                                            if(Homepage.manager.getAlbums().get(i).getAlbumTitle().equals(albumname)){
                                                destinationalbumindex = i;
                                            }
                                        }
                                        Homepage.manager.getAlbums().get(destinationalbumindex).addPhoto(photopath);

                                        Photo srcphoto = Homepage.manager.getCurrentAlbum().getPhotos().get(photoindex);
                                        Album destphotoalbum = Homepage.manager.getAlbums().get(destinationalbumindex);
                                        int destphotoindex = destphotoalbum.getPhotos().size() - 1;
                                        Photo destphoto = destphotoalbum.getPhotos().get(destphotoindex);

                                        for(int i = 0; i < srcphoto.getLocationTags().size(); i++){
                                            destphoto.addLocationTag(srcphoto.getLocationTags().get(i));
                                        }
                                        for(int i = 0; i < srcphoto.getPersonTags().size(); i++){
                                            destphoto.addPersonTag(srcphoto.getPersonTags().get(i));
                                        }

                                        Homepage.manager.getCurrentAlbum().removePhoto(photoindex);

                                        Toast.makeText(getApplicationContext(), "Photo Successfully Moved", Toast.LENGTH_SHORT).show();

                                        try {
                                            AlbumManager.serialize(Homepage.manager);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        gridview = (GridView) findViewById(R.id.gridView1);
                                        populatePhotosList();
                                        imageAdapter.notifyDataSetChanged();
                                        gridview.setAdapter(imageAdapter);

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


                        deletePhotoBtn.setVisibility(View.INVISIBLE);
                        movePhotoBtn.setVisibility(View.INVISIBLE);

                    }
                });

                return true;
            }
        });

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Photo photo = Homepage.manager.getCurrentAlbum().getPhotos().get(position);
                Homepage.manager.getCurrentAlbum().setCurrentPhoto(photo);

                Intent slideShowPageIntent = new Intent(SingleAlbumPage.this, Slideshow.class);
                slideShowPageIntent.putExtra("imagePosition", position);
                startActivity(slideShowPageIntent);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                String image_uristr = uri.toString();

                for(int i = 0; i < photosInAlbum.size(); i++){
                    if(image_uristr.equals(photosInAlbum.get(i).getPhotoPath())){
                        Toast.makeText(getApplicationContext(), "This photo already exists in the album", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                Homepage.manager.getCurrentAlbum().addPhoto(image_uristr);

                try {
                    AlbumManager.serialize(Homepage.manager);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                gridview = (GridView) findViewById(R.id.gridView1);
                populatePhotosList();
                imageAdapter.notifyDataSetChanged();
                gridview.setAdapter(imageAdapter);
            }
        }
    }



    private String doesAlbumandPhotoexist(String albumname, String photopath){
        for(int i = 0; i < Homepage.manager.getAlbums().size(); i++){
            if(albumname.equals(Homepage.manager.getAlbums().get(i).getAlbumTitle())){
                for(int j = 0; j < Homepage.manager.getAlbums().get(i).getPhotos().size(); j++){
                    if(Homepage.manager.getAlbums().get(i).getPhotos().get(j).getPhotoPath().equals(photopath)){
                        return "The photo already exists in the destination album";
                    }
                }
                return "good";
            }
        }
        return "Album with name entered does not exist. Please create an Album first";
    }

    private static void populatePhotosList() {
        photosInAlbum.clear();
        photosInAlbum.addAll(Homepage.manager.getCurrentAlbum().getPhotos());
    }


}
