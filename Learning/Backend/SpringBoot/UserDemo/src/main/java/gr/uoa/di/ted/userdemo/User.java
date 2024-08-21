package gr.uoa.di.ted.userdemo;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class User {

    private @Id @GeneratedValue Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    public User() {}

    public User(String fname, String lname, String username) {
        this.firstName = fname;
        this.lastName = lname;
        this.username = username;
    }
}
