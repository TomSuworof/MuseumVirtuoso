package com.salat.config;

public class Configs {
    private static final String url = "jdbc:virtuoso://localhost:1111/DATABASE=DB";
    private static final String username = "dba";
    private static final String password = "1";

    public static String getUrl() {
        return url;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }
}
