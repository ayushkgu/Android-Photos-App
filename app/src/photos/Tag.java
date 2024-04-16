

package app;
import java.io.Serializable;
import java.util.List;


/**
 * The Tag class represents a tag that can be associated with photos in the application.
 * It contains information about the name and value of the tag, as well as the list of photos
 * to which the tag is associated.
 *
 * @author Yash Shah
 * @version 1.0
 */
public class Tag implements Serializable {

    /** The name of the tag. */
    public String name;

    /** The value of the tag. */
    public String value;

    /** The list of photos associated with this tag. */
    List<Photo> photos;

    /**
     * Constructor to create a Tag object with a given name, value, and list of photos.
     *
     * @param name The name of the tag.
     * @param value The value of the tag.
     * @param photos The list of photos associated with this tag.
     */
    public Tag(String name, String value, List<Photo> photos) {
        this.name = name;
        this.value = value;
        this.photos = photos;
    }

    /**
     * Add a photo to the list of photos associated with this tag.
     *
     * @param photo The photo to add.
     */
    public void addPhoto(Photo photo) {
        this.photos.add(photo);
    }

    /**
     * Remove a photo from the list of photos associated with this tag.
     *
     * @param photo The photo to remove.
     */
    public void removePhoto(Photo photo) {
        photos.remove(photo);
    }

    /**
     * Get the name of the tag.
     *
     * @return The name of the tag.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the value of the tag.
     *
     * @return The value of the tag.
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Get a string representation of the tag.
     *
     * @return A string containing the name and value of the tag.
     */
    @Override
    public String toString() {
        return this.name + ": " + this.value;
    }
}