package com.androidphotosapp.photosandroidapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import model.Album;
import model.PhotoGridAdapterSearch;
import model.Photo;
import model.CustomSpinner;

public class SearchPage extends AppCompatActivity {
    private CustomSpinner spinner;
    private int selectedSpinnerIndex;
    private String [] itemsInSpinner = {"Location", "Person"};
    public List<String> locationTags;


    public List<String> personTags;

    private Button addTagButton, searchButton, searchButtonAND;
    private AutoCompleteTextView tagEntered;
    private TextView tagsSelected;

    private PhotoGridAdapterSearch imgAdapter;
    GridView gridViewForSearchResult;
    private ArrayAdapter<String> locationAdapter;
    private ArrayAdapter<String> personAdapter;

    public List<Photo> searchResults = new ArrayList<Photo>();


    private void collectallTagsList() {
        HashSet<String> locTags = new HashSet<>();
        HashSet<String> perTags = new HashSet<>();

        // Assuming there is a method to get all albums
        for (Album album : Homepage.manager.getAlbums()) {
            for (Photo photo : album.getPhotos()) {
                locTags.addAll(photo.getLocationTags());
                perTags.addAll(photo.getPersonTags());
            }
        }

        locationTags.clear();
        locationTags.addAll(locTags);
        personTags.clear();
        personTags.addAll(perTags);

        // Update adapters with the new tag lists
        updateAdapters();
    }

    private void updateAdapters() {
        locationAdapter.clear();
        locationAdapter.addAll(locationTags);
        locationAdapter.notifyDataSetChanged();

        personAdapter.clear();
        personAdapter.addAll(personTags);
        personAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        locationTags = new ArrayList<>();
        personTags = new ArrayList<>();
//        locationTags.add("new york");
        collectallTagsList();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);
        spinner = (CustomSpinner) findViewById(R.id.searchOptions);

        ArrayAdapter<CharSequence> itemAdapter = ArrayAdapter.createFromResource(this, R.array.search_options,android.R.layout.simple_spinner_item);
        itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(itemAdapter);

        locationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, locationTags);
        personAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, personTags);

        tagEntered = (AutoCompleteTextView) findViewById(R.id.tagValue);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedSpinnerIndex = i;
                if (selectedSpinnerIndex == 0) {
                    tagEntered.setAdapter(locationAdapter);
                } else if (selectedSpinnerIndex == 1) {
                    tagEntered.setAdapter(personAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tagEntered = (AutoCompleteTextView) findViewById(R.id.tagValue);
        tagsSelected = (TextView) findViewById(R.id.tagsEnteredList);
        searchButton = (Button) findViewById(R.id.searchButton);
        searchButtonAND = (Button) findViewById(R.id.searchButtonAND);


        addTagButton = (Button) findViewById(R.id.addTagButton);
        locationTags = new ArrayList<String>();
        personTags = new ArrayList<String>();

        addTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tagVal = tagEntered.getText().toString();
                if(tagVal.isEmpty()){
                    Toast.makeText(SearchPage.this, "Tag cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                String currText = tagsSelected.getText().toString();
                String tagKey = itemsInSpinner[selectedSpinnerIndex];

                tagsSelected.setText(currText + "\n" + tagKey + ": " + tagVal);

                if(selectedSpinnerIndex == 0) locationTags.add(tagVal);
                else if(selectedSpinnerIndex == 1) personTags.add(tagVal);

                searchButton.setVisibility(View.VISIBLE);
                searchButtonAND.setVisibility(View.VISIBLE);


                tagEntered.setText("");
            }
        });

        gridViewForSearchResult = (GridView) findViewById(R.id.searchedPhotosGridView);
        imgAdapter = new PhotoGridAdapterSearch(this, searchResults);
        gridViewForSearchResult.setAdapter(imgAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchResults.clear();
                searchResults.addAll(Homepage.manager.getPhotosWithTags(locationTags, personTags));

                gridViewForSearchResult = (GridView) findViewById(R.id.searchedPhotosGridView);
                imgAdapter.notifyDataSetChanged();
                gridViewForSearchResult.setAdapter(imgAdapter);
            }
        });
        searchButtonAND.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchResults.clear();
                searchResults.addAll(Homepage.manager.searchPhotosAND(locationTags, personTags));

                gridViewForSearchResult = (GridView) findViewById(R.id.searchedPhotosGridView);
                imgAdapter.notifyDataSetChanged();
                gridViewForSearchResult.setAdapter(imgAdapter);
            }
        });


    }
}
