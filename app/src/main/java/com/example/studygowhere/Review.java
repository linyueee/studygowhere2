package com.example.studygowhere;

public class Review {
    private String author;
    private String content;
    private String rating;
    private String studyAreaName;


    public Review(String author, String content, String rating, String studyAreaName) {
        this.author = author;
        this.content = content;
        this.rating = rating;
        this.studyAreaName = studyAreaName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getStudyAreaName() {
        return studyAreaName;
    }

    public void setStudyAreaName(String studyAreaName) {
        this.studyAreaName = studyAreaName;
    }
}
