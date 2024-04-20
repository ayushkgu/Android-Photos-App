package model;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.androidphotosapp.photosandroidapp.R;

import java.util.List;

/**
 * Adapter class for displaying photos in a grid view.
 */
public class PhotoGridAdapter extends BaseAdapter {

    private Context activityContext;
    private List<Photo> albumPhotos;

    /**
     * Constructor for PhotoGridAdapter.
     * @param context The context of the activity.
     * @param photosInAlbum The list of photos in the album.
     */
    public PhotoGridAdapter(Context context, List<Photo> photosInAlbum) {
        activityContext = context;
        albumPhotos = photosInAlbum;
    }

    /**
     * Gets the number of items in the adapter.
     * @return The number of photos in the album.
     */
    public int getCount() {
        return albumPhotos.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    /**
     * Creates a new ImageView for each item referenced by the Adapter.
     * @param position The position of the item in the adapter.
     * @param convertView The old view to reuse, if possible.
     * @param parent The parent ViewGroup that this view will eventually be attached to.
     * @return The View corresponding to the data at the specified position.
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        ImageView thumbnailView = null;
        if (convertView == null) {
            imageView = new ImageView(activityContext);
            imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(10, 10, 10, 10);
            thumbnailView = imageView.findViewById(R.id.grid_item_image);
        } else {
            imageView = (ImageView) convertView;
        }

        Uri imageUri = Uri.parse(albumPhotos.get(position).getPhotoPath());
        thumbnailView.setImageURI(imageUri);
        return imageView;
    }

}