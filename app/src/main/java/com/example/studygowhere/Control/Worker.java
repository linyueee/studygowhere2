package com.example.studygowhere.Control;

import android.content.Context;

import static com.example.studygowhere.Boundary.LoginActivity.getUn;

public class Worker {
    Context context;

    public Worker(Context context) {
        this.context = context;
    }

    public void Login(String type, String Username, String Password)
    {
        AccountWorker bgw = new AccountWorker(context);
        bgw.execute(type, Username, Password);
    }

    public void Register(String type, String str_name, String str_password, String str_email, String str_mobile)
    {
        AccountWorker bgw = new AccountWorker(context);
        bgw.execute(type, str_name, str_password, str_email, str_mobile);
    }

    public void ReadReview(String studyAreaName)
    {
        ReadReviewWorker rrw = new ReadReviewWorker(context);
        rrw.execute(studyAreaName);
    }

    public void ReadMyReview(String username)
    {
        ReadMyReviewWorker rmRW = new ReadMyReviewWorker(context);
        rmRW.execute(username);
    }

    public void AddReview(String username, String review, String studyAreaName, String rating)
    {
        AddReviewWorker arw = new AddReviewWorker(context);
        arw.execute(username, review, studyAreaName, rating);
    }

    public void ReadBookmark(String username)
    {
        ReadBookmarkWorker rbw = new ReadBookmarkWorker(context);
        rbw.execute(username);
    }

    public void AddBookmark(String username, String studyAreaName, String type)
    {
        CustomiseBookmarkWorker bmw = new CustomiseBookmarkWorker(context);
        bmw.execute(username, studyAreaName, type);
    }

    public void DeleteBookmark(String username, String studyAreaName, String type)
    {
        CustomiseBookmarkWorker dbm = new CustomiseBookmarkWorker(context);
        dbm.execute(username, studyAreaName, type);
    }


}
