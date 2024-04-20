package model;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.androidphotosapp.photosandroidapp.R;

import java.util.List;

public class PhotoGridAdapterSearch extends BaseAdapter {

    private Context context;
    private List<Photo> searchResults;

    // Constructor
    public PhotoGridAdapterSearch(Context context, List<Photo> searchResults) {
        this.context = context;
        this.searchResults = searchResults;
    }

    // Method to get the view for each grid item
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the LayoutInflater service
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {
            // If convertView is null, inflate the layout for grid item
            gridView = inflater.inflate(R.layout.photos_search_list, null);

            // Get the ImageView in the grid item layout
            ImageView imageView = (ImageView) gridView.findViewById(R.id.searchedImageGridView);

            // Set the image for the ImageView using the photo's URI
            Uri imguri = Uri.parse(searchResults.get(position).getPhotoPath());
            imageView.setImageURI(imguri);

        } else {
            // If convertView is not null, reuse the existing view
            gridView = convertView;
        }

        return gridView;
    }

    // Method to get the count of items in the adapter
    @Override
    public int getCount() {
        return searchResults.size();
    }

    // Method to get the item at a specific position (not used)
    @Override
    public Object getItem(int position) {
        return null;
    }

    // Method to get the item ID of an item at a specific position (not used)
    @Override
    public long getItemId(int position) {
        return 0;
    }
}