package com.example.tanthinh.local4fun.models;

import android.content.Context;

public final class Singleton {
    private static Singleton singleton;

    public User loginUser;
    /**
     * Initialize singleton instance.
     * @return
     */
    public static synchronized Singleton initInstance() {
        if (singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }

    public static Singleton getInstance() {
        return singleton;
    }


    public void signOutAndDestroyDatabase() {
        this.loginUser = null;
    }
}
