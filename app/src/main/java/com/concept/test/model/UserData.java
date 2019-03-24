package com.concept.test.model;

public class UserData {
    String username,password;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "name='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
