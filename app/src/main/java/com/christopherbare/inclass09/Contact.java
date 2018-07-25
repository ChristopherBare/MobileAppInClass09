package com.christopherbare.inclass09;

import android.media.Image;

public class Contact {
    int picID;
    Image image;
    String name, phone, email;

    public Contact(String name, String phone, String email) {


        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Contact() {

    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPicID() {
        return picID;
    }

    public void setPicID(int picID) {
        this.picID = picID;
    }
}
