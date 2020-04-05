package com.example.studygowhere.Control;

import android.content.Context;

/**
 * <h1>Worker controller</h1>
 * This is the general controller class that is responsible for instantiating all controller classes.
 * Boundary classes only need to call the respective method name and pass in corresponding values.
 * @author ILOVESSADMORE
 * @version 1.0
 */
public class Worker {
    /**
     * Instance variable to store the context of the boundary class.
     */
    Context context;


    /**
     * Constructor
     * @param context
     */
    public Worker(Context context) {
        this.context = context;
    }

    /**
     * This is a method to instantiate AccountWorker and execute it.
     * @param type type to operation
     * @param Username Username input
     * @param Password Password input
     */
    public void Login(String type, String Username, String Password)
    {
        AccountWorker bgw = new AccountWorker(context);
        bgw.execute(type, Username, Password);
    }

    /**
     * This is a method to instantiate AccountWorker and execute it.
     * @param type type of operation
     * @param str_name Username input
     * @param str_password Password input
     * @param str_email Email input
     * @param str_mobile Phone number input
     */
    public void Register(String type, String str_name, String str_password, String str_email, String str_mobile)
    {
        AccountWorker bgw = new AccountWorker(context);
        bgw.execute(type, str_name, str_password, str_email, str_mobile);
    }


    /**
     * This is a method to instantiate ReadReviewWorker and execute it.
     * @param studyAreaName name of Study Area
     */
    public void ReadReview(String studyAreaName)
    {
        ReadReviewWorker rrw = new ReadReviewWorker(context);
        rrw.execute(studyAreaName);
    }


    /**
     * This is a method to instantiate ReadMyReviewWorker and execute it.
     * @param username Username
     */
    public void ReadMyReview(String username)
    {
        ReadMyReviewWorker rmRW = new ReadMyReviewWorker(context);
        rmRW.execute(username);
    }


    /**
     * This is a method to instantiate AddReviewWorker and execute it.
     * @param username Username
     * @param review Content of the review
     * @param studyAreaName Name of the Study Area that is being reviewed on.
     * @param rating Rating given by the user
     */
    public void AddReview(String username, String review, String studyAreaName, String rating)
    {
        AddReviewWorker arw = new AddReviewWorker(context);
        arw.execute(username, review, studyAreaName, rating);
    }


    /**
     * This is a method to instantiate ReadBookmarkWorker and execute it.
     * @param username Username of the bookmark owner
     */
    public void ReadBookmark(String username)
    {
        ReadBookmarkWorker rbw = new ReadBookmarkWorker(context);
        rbw.execute(username);
    }


    /**
     * This is a method to instantiate CustomiseBookmarkWorker and execute it.
     * @param username Username
     * @param studyAreaName The name of the Study Area that is bookmarked.
     * @param type Type of the operation.
     */
    public void AddBookmark(String username, String studyAreaName, String type)
    {
        CustomiseBookmarkWorker bmw = new CustomiseBookmarkWorker(context);
        bmw.execute(username, studyAreaName, type);
    }


    /**
     * This is a method to instantiate CustomiseBookmarkWorker and execute it.
     * @param username Username
     * @param studyAreaName The name of the Study Area to be deleted from bookmark.
     * @param type Type of the operation.
     */
    public void DeleteBookmark(String username, String studyAreaName, String type)
    {
        CustomiseBookmarkWorker dbm = new CustomiseBookmarkWorker(context);
        dbm.execute(username, studyAreaName, type);
    }


    /**
     * This is a method to instantiate VerifyAccountWorker and execute it.
     * @param username Username
     * @param email Email
     * @param phone Phone
     */
    public void VerifyAccount(String username, String email, String phone)
    {
        VerifyAccountWorker vaw = new VerifyAccountWorker(context);
        vaw.execute(username, email, phone);
    }

    /**
     * This is a method to instantiate ResetPasswordWorker and execute it.
     * @param username Username
     * @param password New password to be reset.
     */
    public void ResetPassword(String username, String password)
    {
        ResetPasswordWorker rpw = new ResetPasswordWorker(context);
        rpw.execute(username, password);
    }

}
