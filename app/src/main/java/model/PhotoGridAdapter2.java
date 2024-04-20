package model;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidphotosapp.photosandroidapp.R;
import com.androidphotosapp.photosandroidapp.Homepage;

import java.io.File;
import java.util.*;

public class PhotoGridAdapter2 extends BaseAdapter {

    private Context context;
    private List<Photo> photosInAlbum;

    // Constructor
    public PhotoGridAdapter2(Context context, List<Photo> photosInAlbum) {
        this.context = context;
        this.photosInAlbum = photosInAlbum;
    }

    // Method to get the view for each grid item
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the LayoutInflater service
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {
            // If convertView is null, inflate the layout for grid item
            gridView = new View(context);
            gridView = inflater.inflate(R.layout.photos_album_list, null);

            // Get the ImageView in the grid item layout
            ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_item_image);

            // Set the image for the ImageView using the photo's URI
            Uri imguri = Uri.parse(photosInAlbum.get(position).getPhotoPath());
            imageView.setImageURI(imguri);
        } else {
            // If convertView is not null, reuse the existing view
            gridView = (View) convertView;
        }

        return gridView;
    }

    // Method to get the count of items in the adapter
    @Override
    public int getCount() {
        return photosInAlbum.size();
    }

    // Method to get the item at a specific position
    @Override
    public Object getItem(int position) {
        return null;
    }

    // Method to get the item ID of an item at a specific position
    @Override
    public long getItemId(int position) {
        return 0;
    }
}
