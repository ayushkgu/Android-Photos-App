package model;

import java.io.*;
import java.util.*;

/**
 * Represents a photo album containing a collection of Photos.
 */
public class Album implements Serializable {

    /* Serialization */
//    private static final long serialVersionUID = 6641189840742017975L;

    // Title of the photo album
    private String albumTitle;

    // List to store the Photos in the album
    private List<Photo> photoList;

    // Currently selected Photo
    private Photo currentPhoto = null;

    /**
     * Constructs a new Album object with the given title.
     * @param title The title of the photo album
     */
    public Album(String title) {
        this.albumTitle = title;
        photoList = new ArrayList<Photo>();
    }

    /**
     * Adds a new Photo to the album.
     * @param path The file path of the Photo to add
     */
    public void addPhoto(String path) {
        Photo newPhoto = new Photo(path);
        photoList.add(newPhoto);
    }

    /**
     * Removes a Photo from the album at the specified index.
     * @param index The index of the Photo to remove
     */
    public void removePhoto(int index) {
        photoList.remove(index);
    }

    /**
     * Returns the title of the photo album.
     * @return The title of the photo album
     */
    public String getAlbumTitle() {
        return albumTitle;
    }

    /**
     * Sets the title of the photo album.
     * @param title The new title of the photo album
     */
    public void setAlbumTitle(String title) {
        this.albumTitle = title;
    }

    /**
     * Returns the list of Photos in the album.
     * @return The list of Photos in the album
     */
    public List<Photo> getPhotos() {
        return photoList;
    }

    /**
     * Returns the currently selected Photo.
     * @return The currently selected Photo
     */
    public Photo getCurrentPhoto() {
        return currentPhoto;
    }

    /**
     * Sets the currently selected Photo.
     * @param currentPhoto The Photo to set as currently selected
     */
    public void setCurrentPhoto(Photo currentPhoto) {
        this.currentPhoto = currentPhoto;
    }
}
