package com.Backend_v10.User;
import java.time.LocalDate;
import java.util.Arrays;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;



@Entity
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long UserID;

    //rest of the fields (may be private)
    String Username;
    String Name;
    String Lastname;
    String Email;
    byte[] Photo;
    LocalDate BirthDate;
    byte[] CVFile;

    
    public User(String username, String name, String lastname, String email){
        this.Name = name;
        this.Lastname = lastname;
        this.Email = email;
        this.Username = username;
    }

    public void setUserID(Long userID) {
        UserID = userID;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPhoto(byte[] photo) {
        Photo = photo;
    }

    public void setBirthDate(LocalDate birthDate) {
        BirthDate = birthDate;
    }

    public void setCVFile(byte[] cVFile) {
        CVFile = cVFile;
    }

    public Long getUserID() {
        return UserID;
    }

    public String getUsername() {
        return Username;
    }

    public String getName() {
        return Name;
    }

    public String getLastname() {
        return Lastname;
    }

    public String getEmail() {
        return Email;
    }

    public byte[] getPhoto() {
        return Photo;
    }

    public LocalDate getBirthDate() {
        return BirthDate;
    }

    public byte[] getCVFile() {
        return CVFile;
    }

    @Override
    public String toString() {
        return "User [UserID=" + UserID + ", Username=" + Username + ", Name=" + Name + ", Lastname=" + Lastname
                + ", Email=" + Email + ", Photo=" + Arrays.toString(Photo) + ", BirthDate=" + BirthDate + ", CVFile="
                + Arrays.toString(CVFile) + "]";
    }

    




}
