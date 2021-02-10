package com.example.codemagictest;

public class Session {
    private static int userId=-1;

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        Session.userId = userId;
    }

}



