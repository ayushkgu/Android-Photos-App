package model;

import java.io.*;
import java.util.*;

/**
 * Represents a photo object.
 */
public class Photo implements Serializable {

    /* Serialization stuff */
    private static final long serialVersionUID = 6641189840742017975L;

    private String photoPath;
    private String fileName;
    private List<String> personTags;
    private List<String> locationTags;


    /**
     * Constructor for Photo.
     * @param photoPath The path of the photo on the device.
     */
    public Photo(String photoPath) {
        this.photoPath = photoPath;
        this.fileName = photoPath.substring(photoPath.lastIndexOf('/')+1);
        personTags = new ArrayList<String>();
        locationTags = new ArrayList<String>();
    }

    /**
     * Adds a person tag to the photo.
     * @param personTag The person tag to add.
     */
    public void addPersonTag(String personTag){
        this.personTags.add(personTag.toLowerCase());
    }

    /**
     * Removes a person tag from the photo.
     * @param personTag The person tag to remove.
     */
    public void removePersonTag(String personTag){
        this.personTags.remove(personTag.toLowerCase());
    }

    /**
     * Adds a location tag to the photo.
     * @param locationTag The location tag to add.
     */
    public void addLocationTag(String locationTag){
        this.locationTags.add(locationTag.toLowerCase());
    }

    /**
     * Removes a location tag from the photo.
     * @param locationTag The location tag to remove.
     */
    public void removeLocationTag(String locationTag){
        this.locationTags.remove(locationTag.toLowerCase());
    }

    /**
     * Gets all tags associated with the photo.
     * @return A list of all tags.
     */
    public List<String> getAllTags(){
        List<String> allTags = new ArrayList<String>();

        for(String locationTag : this.getLocationTags()){
            allTags.add("Location: " + locationTag);
        }
        for(String personTag : this.getPersonTags()){
            allTags.add("Person: " + personTag);
        }

        return allTags;
    }

    /**
     * Overrides the equals method to compare photo objects.
     * @param obj The object to compare.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj){
        if(obj == this){
            return true;
        }

        if(!(obj instanceof Photo)){
            return false;
        }

        Photo photo = (Photo) obj;
        return photo.getPhotoPath().equals(this.getPhotoPath());
    }

    /**
     * Overrides the hashCode method.
     * @return The hash code value for the photo.
     */
    @Override
    public int hashCode(){
        return 17 * 11 + this.getPhotoPath().hashCode();
    }

    /**
     * Gets the path of the photo.
     * @return The path of the photo.
     */
    public String getPhotoPath() {
        return photoPath;
    }

    /**
     * Gets the file name of the photo.
     * @return The file name of the photo.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Gets the list of person tags associated with the photo.
     * @return The list of person tags.
     */
    public List<String> getPersonTags() {
        return personTags;
    }

    /**
     * Gets the list of location tags associated with the photo.
     * @return The list of location tags.
     */
    public List<String> getLocationTags() {
        return locationTags;
    }
}
