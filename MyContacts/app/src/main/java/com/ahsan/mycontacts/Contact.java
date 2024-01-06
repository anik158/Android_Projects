package com.ahsan.mycontacts;

public class Contact {
    private String name;
    private String email;
    private String phoneHome;
    private String phoneOffice;
    private String photo;

    public Contact(String name, String email, String phoneHome, String phoneOffice, String photo) {
        this.name = name;
        this.email = email;
        this.phoneHome = phoneHome;
        this.phoneOffice = phoneOffice;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneHome() {
        return phoneHome;
    }

    public String getPhoneOffice() {
        return phoneOffice;
    }

    public String getPhoto() {
        return photo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneHome(String phoneHome) {
        this.phoneHome = phoneHome;
    }

    public void setPhoneOffice(String phoneOffice) {
        this.phoneOffice = phoneOffice;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
