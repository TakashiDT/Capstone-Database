package com.example.capstone_project_redo.admin;

public class AdminModel {
    String FirstName, LastName, EmailAddress, activate, imageProof;

    AdminModel() {

    }

    public AdminModel(String firstName, String lastName, String emailAddress, String activate, String imageProof) {
        FirstName = firstName;
        LastName = lastName;
        EmailAddress = emailAddress;
        this.activate = activate;
        this.imageProof = imageProof;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }

    public String getImageProof() {
        return imageProof;
    }

    public void setImageProof(String imageProof) {
        this.imageProof = imageProof;
    }
}
