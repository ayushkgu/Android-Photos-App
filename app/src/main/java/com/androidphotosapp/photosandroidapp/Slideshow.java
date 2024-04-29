package com.androidphotosapp.photosandroidapp;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Album;
import model.CustomSpinner;
import model.Photo;
import model.AlbumManager;

public class Slideshow extends AppCompatActivity {
    private CustomSpinner spinner;
    private int selectedSpinnerIndex;
    private EditText tagEntered;
    private Button addTagButton, backwardBtn, forwardBtn;
    private ListView tagsList;
    private ArrayAdapter<String> tagsAdapter;
    private static List<String> allTags = new ArrayList<String>();
    private TextView tagText;
    private String [] itemsInSpinner = {"Location", "Person"};
    private Button deleteTagButton;

    private static Album currentAlbum = null;
    private static int currentIndex;
    private static List<Photo> photosInAlbum = new ArrayList<Photo>();
    ImageView imageOnSlideShow;
    private int imagePosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slideshow);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Intent slideShowPageIntent = getIntent();
        this.imagePosition = slideShowPageIntent.getIntExtra("imagePosition", -1);

        imageOnSlideShow = (ImageView) findViewById(R.id.imageView);
        if(this.imagePosition >= 0){
            Album currentAlbum = Homepage.manager.getCurrentAlbum();
            Photo currentPhoto = currentAlbum.getPhotos().get(imagePosition);
            Uri imgUri = Uri.parse(currentPhoto.getPhotoPath());
            imageOnSlideShow.setImageURI(imgUri);

        }
        else{
            Toast.makeText(getApplicationContext(), "Image Not Found because it's position < 0", Toast.LENGTH_LONG).show();
        }

        backwardBtn = (Button) findViewById(R.id.backwardBtn);
        forwardBtn = (Button) findViewById(R.id.forwardBtn);



        backwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photosInAlbum.clear();
                for(int i = 0; i < Homepage.manager.getCurrentAlbum().getPhotos().size(); i++) {
                    photosInAlbum.add(Homepage.manager.getCurrentAlbum().getPhotos().get(i));
                }

                if(currentAlbum != Homepage.manager.getCurrentAlbum()) {
                    currentIndex = imagePosition;
                    currentAlbum = Homepage.manager.getCurrentAlbum();
                }

                currentIndex--;

                if(currentIndex == -1) {
                    currentIndex = Homepage.manager.getCurrentAlbum().getPhotos().size()-1;
                }

                Homepage.manager.getCurrentAlbum().setCurrentPhoto(Homepage.manager.getCurrentAlbum().getPhotos().get(currentIndex));

                Album currentAlbum = Homepage.manager.getCurrentAlbum();
                Photo currentPhoto = currentAlbum.getPhotos().get(currentIndex);
                imageOnSlideShow.setImageURI(Uri.parse(currentPhoto.getPhotoPath()));

                tagsList = (ListView) findViewById(R.id.tagsOfPhotoSlideshow);
                allTags.clear();
                allTags.addAll(Homepage.manager.getCurrentAlbum().getCurrentPhoto().getAllTags());
                tagsAdapter.notifyDataSetChanged();
                tagsList.setAdapter(tagsAdapter);
            }
        });

        forwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                photosInAlbum.clear();

                for(int i = 0; i < Homepage.manager.getCurrentAlbum().getPhotos().size(); i++) {
                    photosInAlbum.add(Homepage.manager.getCurrentAlbum().getPhotos().get(i));
                }

                if(currentAlbum != Homepage.manager.getCurrentAlbum()) {
                    currentIndex = imagePosition;
                    currentAlbum = Homepage.manager.getCurrentAlbum();
                }

                currentIndex++;

                if(currentIndex == Homepage.manager.getCurrentAlbum().getPhotos().size()) {
                    currentIndex = 0;
                }

                Photo newPhoto = Homepage.manager.getCurrentAlbum().getPhotos().get(currentIndex);
                Homepage.manager.getCurrentAlbum().setCurrentPhoto(newPhoto);

                Album currentAlbum = Homepage.manager.getCurrentAlbum();
                Photo currentPhoto = currentAlbum.getPhotos().get(currentIndex);
                Uri imgUri = Uri.parse(currentPhoto.getPhotoPath());
                imageOnSlideShow.setImageURI(imgUri);

                tagsList = (ListView) findViewById(R.id.tagsOfPhotoSlideshow);
                allTags.clear();
                allTags.addAll(Homepage.manager.getCurrentAlbum().getCurrentPhoto().getAllTags());
                tagsAdapter.notifyDataSetChanged();
                tagsList.setAdapter(tagsAdapter);

            }
        });



        tagsList = (ListView) findViewById(R.id.tagsOfPhotoSlideshow);
        allTags = Homepage.manager.getCurrentAlbum().getCurrentPhoto().getAllTags();
        tagsAdapter = new ArrayAdapter<String>(this, R.layout.photos_tags_list, R.id.tagOfPhotosSlideshow, allTags);
        tagsList.setAdapter(tagsAdapter);

        spinner = (CustomSpinner) findViewById(R.id.addTagOptions);
        ArrayAdapter<CharSequence> itemAdapter = ArrayAdapter.createFromResource(this, R.array.search_options,android.R.layout.simple_spinner_item);
        itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(itemAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedSpinnerIndex = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tagEntered = (EditText) findViewById(R.id.addTagValue);
        addTagButton = (Button) findViewById(R.id.addTagButtonSlideshow);

        addTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tagVal = tagEntered.getText().toString();
                if(tagVal.isEmpty()){
                    Toast.makeText(Slideshow.this, "Tag can not be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                String selectedTagType = itemsInSpinner[selectedSpinnerIndex];
                if (tagAlreadyExists(tagVal, selectedTagType)) {
                    Toast.makeText(Slideshow.this, selectedTagType + " tag already exists in the album", Toast.LENGTH_SHORT).show();
                    return;
                }

                Photo currentPhoto = Homepage.manager.getCurrentAlbum().getCurrentPhoto();

                if(selectedSpinnerIndex == 0){
                    currentPhoto.addLocationTag(tagVal);
                }
                else if(selectedSpinnerIndex == 1){
                    currentPhoto.addPersonTag(tagVal);
                }

                tagEntered.setText("");

                try {
                    AlbumManager.serialize(Homepage.manager);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                tagsList = (ListView) findViewById(R.id.tagsOfPhotoSlideshow);
                allTags.clear();
                allTags.addAll(Homepage.manager.getCurrentAlbum().getCurrentPhoto().getAllTags());
                tagsAdapter.notifyDataSetChanged();
                tagsList.setAdapter(tagsAdapter);
            }
        });


        deleteTagButton = (Button) findViewById(R.id.deleteTagButtonSlideshow);

        tagsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                deleteTagButton.setVisibility(View.VISIBLE);
                final String selectedTag = tagsList.getItemAtPosition(i).toString();
                deleteTagButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String[] splitList = selectedTag.split(": ");
                        String tagKey = splitList[0];
                        String tagVal = splitList[1];
                        Photo currentPhoto = Homepage.manager.getCurrentAlbum().getCurrentPhoto();

                        if(tagKey.toLowerCase().equals(itemsInSpinner[0].toLowerCase())){
                            currentPhoto.removeLocationTag(tagVal);
                        }
                        else if(tagKey.toLowerCase().equals(itemsInSpinner[1].toLowerCase())){
                            currentPhoto.removePersonTag(tagVal);
                        }

                        try {
                            AlbumManager.serialize(Homepage.manager);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        tagsList = (ListView) findViewById(R.id.tagsOfPhotoSlideshow);
                        allTags.clear();
                        allTags.addAll(Homepage.manager.getCurrentAlbum().getCurrentPhoto().getAllTags());
                        tagsAdapter.notifyDataSetChanged();
                        tagsList.setAdapter(tagsAdapter);
                    }
                });


            }
        });
        
    }

    private boolean tagAlreadyExists(String tagVal, String tagType) {
        List<String> allTags = Homepage.manager.getCurrentAlbum().getCurrentPhoto().getAllTags();
        for (String tag : allTags) {
            // Split the tag to separate the tag key and value
            String[] splitTag = tag.split(": ");
            if (splitTag.length == 2) {
                String tagKey = splitTag[0];
                String tagValue = splitTag[1];
                // Check if the tag value matches the provided tag value
                if (tagType.equalsIgnoreCase("Location") && tagKey.equalsIgnoreCase("Location") && tagValue.equalsIgnoreCase(tagVal)) {
                    // Found a matching location tag
                    return true;
                } else if (tagType.equalsIgnoreCase("Person") && tagKey.equalsIgnoreCase("Person") && tagValue.equalsIgnoreCase(tagVal)) {
                    // Found a matching person tag
                    return true;
                }
            }
        }
        return false;
    }
    
}
