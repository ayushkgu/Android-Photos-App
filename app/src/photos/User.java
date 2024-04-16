
package app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * The User class represents a user in the application.
 * It contains information about the user's username, their albums, and their tags.
 * Users can create, delete, and rename albums, as well as add and delete tags.
 *
 * @author Yash Shah
 * @version 1.0
 */

public class User implements Serializable {

    /** The username of the user. */
    public String userName;

    /** The list of albums belonging to the user. */
    public ArrayList<Album> allAlbums;

    /** The list of tags associated with the user's photos. */
    public List<Tag> tags;

    /**
     * Constructor to create a User object
     *
     * @param userName The username of the user.
     * @param tags The list of tags associated with the user's photos.
     */
    public User(String userName, List<Tag> tags) {
        this.userName = userName;
        this.allAlbums = new ArrayList<Album>();
        this.tags = tags;
    }

    /**
     * Constructor to create a User object with a given username and list of albums.
     *
     * @param userName The username of the user.
     * @param allAlbums The list of albums belonging to the user.
     */
    public User(String userName, ArrayList<Album> allAlbums) {
        this.userName = userName;
        this.allAlbums = allAlbums;
        this.tags = new ArrayList<Tag>();
    }

    /**
     * Constructor to create a User object with a given username.
     *
     * @param userName The username of the user.
     */
    public User(String userName) {
        this.userName = userName;
        this.allAlbums = new ArrayList<Album>();
        this.tags = new ArrayList<Tag>();
    }

    /**
     * Get the username of the user.
     *
     * @return The username of the user.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Add an album to the list of albums.
     *
     * @param album The album to add.
     */
    public void addAlbum(Album album) {
        this.allAlbums.add(album);
    }

    /**
     * Get all the albums belonging to the user.
     *
     * @return The list of albums belonging to the user.
     */
    public ArrayList<Album> getAllAlbums() {
        return this.allAlbums;
    }

    /**
     * Get all the tags associated with the user's photos.
     *
     * @return The list of tags associated with the user's photos.
     */
    public List<Tag> getAllTags() {
        return this.tags;
    }

    /**
     * Add a tag to the list of tags associated with the user's photos.
     *
     * @param tag The tag to add.
     */
    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    /**
     * Delete a tag from the list of tags associated with the user's photos.
     *
     * @param tag The tag to delete.
     */
    public void deleteTag(Tag tag) {
        this.tags.remove(tag);
    }



    /**
     * @return String
     */
    @Override
    public String toString() {
        return this.getUserName();
    }

    /**
     * Create a new album and add it to the list of albums belonging to the user.
     *
     * @param album The new album to create.
     */
    public void newAlbum(Album album) {
        boolean exists = false;
        if (album != null) {
            for (int i = 0; i < allAlbums.size(); i++) {
                if (allAlbums.get(i).name.equalsIgnoreCase(album.name)) {
                    exists = true;
                    break;
                }
            }
        }
        if (!exists) {
            allAlbums.add(album);
        }
    }

    /**
     * Rename an existing album belonging to the user.
     *
     * @param album The album to rename.
     * @param newName The new name for the album.
     */
    public void renameAlbum(Album album, String newName) {
        if (album != null) {
            for (int i = 0; i < allAlbums.size(); i++) {
                if (allAlbums.get(i).name.equalsIgnoreCase(album.name)) {
                    album.name = newName;
                    break;
                }
            }
        }
    }

    /**
     * Delete an existing album belonging to the user.
     *
     * @param album The album to delete.
     */
    public void deleteAlbum(Album album) {
        if (album != null) {
            for (int i = 0; i < allAlbums.size(); i++) {
                if (allAlbums.get(i).equals(album)) {
                    allAlbums.remove(i);
                    return;
                }
            }
        }
    }

    /**
     * Get an album belonging to the user by its index.
     *
     * @param index The index of the album.
     * @return The album at the specified index.
     */
    public Album getAlbum(int index) {
        return this.allAlbums.get(index);
    }
}