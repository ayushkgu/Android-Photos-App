package app;

import java.io.File;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * The Photo class represents a photograph in the application.
 * It contains information such as the file path, tags, creation date, caption, and last modified date.
 *
 *
 * @author Ayush Gupta
 * @version 1.0
 */

public class Photo implements Serializable {
    private static final long serialVersionUID = 5547980979143814855L;

    public File file;
    public String path;
    public List<Tag> tags;
    public Calendar calendar;
    public String caption;
    public Date lastModifiedDate;

    /**
     * Constructor to create a new Photo object.
     *
     * @param file The file
     * @param tags The list of tags
     */
    public Photo(File file, List<Tag> tags) {
        this.file = file;
        this.path = this.file.getPath();
        this.lastModifiedDate = new Date(this.file.lastModified());
        this.tags = tags;
    }

    /**
     * Get the file of the photograph.
     *
     * @return The file object of of the photograph.
     */
    public File getFile() {
        return this.file;
    }

    /**
     * Get the path of the photograph file.
     *
     * @return The path of the photograph file.
     */
    public String getPath() {
        return this.path;
    }

    /**
     * Get the list of tags associated with the photograph.
     *
     * @return The list of tags associated with the photograph.
     */
    public List<Tag> getTags() {
        return this.tags;
    }

    /**
     * Get the creation date of the photograph.
     *
     * @return The creation date of the photograph.
     */
    public Date getDate() {
        return this.lastModifiedDate;
    }

    /**
     * Get the calendar representing the creation date of the photograph.
     *
     * @return The calendar representing the creation date of the photograph.
     */
    public Calendar getCalendar() {
        return this.calendar;
    }

    /**
     * Add a tag to the photograph.
     *
     * @param tag The tag to add.
     */
    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    /**
     * Remove a tag from the photograph.
     *
     * @param tag The tag to remove.
     */
    public void removeTag(Tag tag) {
        this.tags.remove(tag);
    }

    /**
     * Set the caption of the photograph.
     *
     * @param caption The caption of the photograph.
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

    /**
     * Get the caption of the photograph.
     *
     * @return The caption of the photograph.
     */
    public String getCaption() {
        return this.caption;
    }

    public boolean hasTagValuePair(String name, String value) {
        for (Tag tag : this.tags) {
            if (tag.getName().equals(name) && tag.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

}