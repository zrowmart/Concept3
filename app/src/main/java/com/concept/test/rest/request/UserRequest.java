package com.concept.test.rest.request;

public class UserRequest {
    String age,email,password,city,state,gender,autoId;

    public UserRequest() {
    }
    public UserRequest(String age, String email, String password) {
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAutoId() {
        return autoId;
    }

    public void setAutoId(String autoId) {
        this.autoId = autoId;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                ", email='" + email + '\'' +
                ", age='" + age + '\'' +
                ", gender='" + gender + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", password='" + password + '\'' +
                ",autoId='"+autoId+'\''+
                '}';
    }
}
