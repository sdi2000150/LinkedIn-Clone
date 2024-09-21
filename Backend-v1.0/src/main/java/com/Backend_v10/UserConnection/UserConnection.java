package com.Backend_v10.UserConnection;

import com.Backend_v10.User.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserConnection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long connectionID;

    @JoinColumn(name = "DidRequest_id")
    private String user1;

    @JoinColumn(name = "GotRequested_id")
    private String user2;

    private boolean pendingRequest;

    @Override
    public String toString() {
        return "UserConnection [connectionID=" + connectionID + ", user1=" + user1 + ", user2=" + user2
                + ", pendingRequest=" + pendingRequest + "]";
    }

    
}
