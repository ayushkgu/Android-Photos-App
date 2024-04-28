package model;

import java.io.*;
import java.util.*;

/**
 * Manages albums and provides methods for adding, removing, and retrieving albums and their photos.
 */
public class AlbumManager implements Serializable {

    /* Serialization stuff */
//    private static final long serialVersionUID = 6641189840742017975L;

    private List<Album> albums;
    private Album currentAlbum;

    /**
     * Constructor for AlbumManager.
     */
    public AlbumManager() {
        albums = new ArrayList<Album>();
    }

    /**
     * Adds an album to the manager.
     * @param album The album to add.
     */
    public void addAlbum(Album album) {
        albums.add(album);
    }

    /**
     * Removes an album from the manager.
     * @param index The index of the album to remove.
     */
    public void removeAlbum(int index) {
        albums.remove(index);
    }

    /**
     * Retrieves photos with specified tags from all albums.
     * @param locationTags The list of location tags to search for.
     * @param personTags The list of person tags to search for.
     * @return A list of photos matching the specified tags.
     */
    public List<Photo> getPhotosWithTags(List<String> locationTags, List<String> personTags){
        Set<Photo> resultSet = new HashSet<Photo>();
        List<Photo> resultList = new ArrayList<Photo>();

        for(Album album : this.albums){
            for(Photo photo : album.getPhotos()){
                boolean alreadyAdded = false;

                for(String searchLocationTag : locationTags) {
                    for(String photoLocationTag : photo.getLocationTags()) {
                        if (photoLocationTag.toLowerCase().contains(searchLocationTag.toLowerCase())){
                            resultSet.add(photo);
                            alreadyAdded = true;
                            break;
                        }
                    }
                    if(alreadyAdded){
                        break;
                    }
                }
                if(!alreadyAdded) {
                    for (String searchPersonTag : personTags) {
                        for(String photoPersonTag : photo.getPersonTags()) {
                            if (photoPersonTag.toLowerCase().contains(searchPersonTag.toLowerCase())) {
                                resultSet.add(photo);
                                break;
                            }
                        }
                        if(alreadyAdded){
                            break;
                        }
                    }
                }
            }
        }

        resultList.addAll(resultSet);
        return resultList;
    }


    public List<Photo> searchPhotosAND(List<String> locationTags, List<String> personTags) {
        Set<Photo> resultSet = new HashSet<Photo>();

        for (Album album : this.albums) {
            for (Photo photo : album.getPhotos()) {
                boolean allTagsMatch = true;

                // Check location tags
                for (String searchLocationTag : locationTags) {
                    boolean locationTagMatch = false;
                    for (String photoLocationTag : photo.getLocationTags()) {
                        if (photoLocationTag.toLowerCase().contains(searchLocationTag.toLowerCase())) {
                            locationTagMatch = true;
                            break;
                        }
                    }
                    if (!locationTagMatch) {
                        allTagsMatch = false;
                        break;
                    }
                }

                // Check person tags only if all location tags matched
                if (allTagsMatch) {
                    for (String searchPersonTag : personTags) {
                        boolean personTagMatch = false;
                        for (String photoPersonTag : photo.getPersonTags()) {
                            if (photoPersonTag.toLowerCase().contains(searchPersonTag.toLowerCase())) {
                                personTagMatch = true;
                                break;
                            }
                        }
                        if (!personTagMatch) {
                            allTagsMatch = false;
                            break;
                        }
                    }
                }

                // Add photo to results if all tags matched
                if (allTagsMatch) {
                    resultSet.add(photo);
                }
            }
        }

        List<Photo> resultList = new ArrayList<Photo>(resultSet);
        return resultList;
    }

    /**
     * Serializes the AlbumManager object.
     * @param userData The AlbumManager object to serialize.
     * @throws IOException If an I/O error occurs while writing the object.
     */
    public static void serialize(AlbumManager userData) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/data/data/com.androidphotosapp.photosandroidapp/files/albums.dat"));
        oos.writeObject(userData);
        oos.close();
    }

    /**
     * Deserializes the AlbumManager object.
     * @return The deserialized AlbumManager object.
     * @throws IOException If an I/O error occurs while reading the object.
     * @throws ClassNotFoundException If the class of a serialized object cannot be found.
     */
    public static AlbumManager deserialize() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/data/data/com.androidphotosapp.photosandroidapp/files/albums.dat"));
        AlbumManager userData = (AlbumManager) ois.readObject();
        ois.close();
        return userData;
    }

    /**
     * Gets the list of albums.
     * @return The list of albums.
     */
    public List<Album> getAlbums() {
        return albums;
    }

    /**
     * Gets the current album.
     * @return The current album.
     */
    public Album getCurrentAlbum() {
        return currentAlbum;
    }

    /**
     * Sets the current album.
     * @param currentAlbum The album to set as current.
     */
    public void setCurrentAlbum(Album currentAlbum) {
        this.currentAlbum = currentAlbum;
    }
}