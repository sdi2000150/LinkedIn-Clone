package com.Backend_v10.Security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data   // Lombok annotation to create all the getters, setters, etc methods based on the fields
@AllArgsConstructor // Lombok annotation to create a constructor with all the arguments
@NoArgsConstructor  // Lombok annotation to create a constructor with no arguments
public class AuthRequest {  // Class to hold the login credentials

    // private String username;    // "Username" is the name
    private String email;
    private String password;    // "Password" is the password

}
