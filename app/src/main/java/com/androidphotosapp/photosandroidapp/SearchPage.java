package com.androidphotosapp.photosandroidapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import model.PhotoGridAdapterSearch;
import model.Photo;
import model.CustomSpinner;

public class SearchPage extends AppCompatActivity {
    private CustomSpinner spinner;
    private int spinnerIndexSelected;
    private String [] spinnerItems = {"Location", "Person"};
    private List<String> locationTags;
    private List<String> personTags;

    private Button addTagButton, searchButton;
    private EditText tagEntered;
    private TextView tagsSelected;

    private PhotoGridAdapterSearch imageAdapter;
    GridView gridViewForSearchResult;

    private List<Photo> searchResults = new ArrayList<Photo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);

        spinner = (CustomSpinner) findViewById(R.id.searchOptions);
        ArrayAdapter<CharSequence> itemAdapter = ArrayAdapter.createFromResource(this, R.array.search_options,android.R.layout.simple_spinner_item);
        itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(itemAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerIndexSelected = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tagEntered = (EditText) findViewById(R.id.tagValue);
        tagsSelected = (TextView) findViewById(R.id.tagsEnteredList);
        searchButton = (Button) findViewById(R.id.searchButton);

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
                String tagKey = spinnerItems[spinnerIndexSelected];

                tagsSelected.setText(currText + "\n" + tagKey + ": " + tagVal);

                if(spinnerIndexSelected == 0) locationTags.add(tagVal);
                else if(spinnerIndexSelected == 1) personTags.add(tagVal);

                searchButton.setVisibility(View.VISIBLE);

                tagEntered.setText("");
            }
        });

        gridViewForSearchResult = (GridView) findViewById(R.id.searchedPhotosGridView);
        imageAdapter = new PhotoGridAdapterSearch(this, searchResults);
        gridViewForSearchResult.setAdapter(imageAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchResults.clear();
                searchResults.addAll(Homepage.manager.getPhotosWithTags(locationTags, personTags));

                // TODO Populate grid view with search result
                gridViewForSearchResult = (GridView) findViewById(R.id.searchedPhotosGridView);
                imageAdapter.notifyDataSetChanged();
                gridViewForSearchResult.setAdapter(imageAdapter);
            }
        });
    }
}
