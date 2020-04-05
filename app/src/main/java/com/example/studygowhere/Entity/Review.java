package com.example.studygowhere.Entity;


/**
 * <h1>Review Entity</h1>
 * This is a Review Entity class that specify the attributes of review object.
 * @author ILOVESSADMORE
 * @version 1.0
 */
public class Review {
    /**
     * Instance variable that stores the Username of the user that writes the review.
     */
    private String author;

    /**
     * Instance variable that stores the content of the review.
     */
    private String content;

    /**
     * Instance variable that stores the rating given in the review.
     */
    private String rating;

    /**
     * Instance variable that stores the name of the Study Area that is being reviewed on.
     */
    private String studyAreaName;

    /**
     * Constructor
     * @param author Username of the user that writes the review.
     * @param content content of the review.
     * @param rating rating given in the review.
     * @param studyAreaName StudyArea name that is being reviewed on.
     */
    public Review(String author, String content, String rating, String studyAreaName) {
        this.author = author;
        this.content = content;
        this.rating = rating;
        this.studyAreaName = studyAreaName;
    }


    /**
     * Getter method of instance variable Author
     * @return
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Setter method of instance variable Author
     * @param author username of the user who writes the review.
     */
    public void setAuthor(String author) {
        this.author = author;
    }


    /**
     * Getter method of instance variable Content
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * Setter method of instance variable Content
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }


    /**
     * Getter method of instance variable Rating
     * @return rating
     */
    public String getRating() {
        return rating;
    }

    /**
     * Setter method of instance variable Rating
     * @param rating rating to be given in the review/
     */
    public void setRating(String rating) {
        this.rating = rating;
    }


    /**
     * Getter method of instance variable StudyAreaName
     * @return the name of the Study Area that is being reviewed on
     */
    public String getStudyAreaName() {
        return studyAreaName;
    }

}
