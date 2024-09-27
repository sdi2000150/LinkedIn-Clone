package com.Backend_v10.UserConnection;

import java.util.List;

import javax.swing.text.StyledEditorKit.BoldAction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface UserConnectionRepository extends JpaRepository<UserConnection,Long>{

    @Query(value = "select  user_connection.user1 from user_connection  where user_connection.user2 = ?1", nativeQuery = true)
    List<String> findUsersRequestingMe(String email_requested);
    
    @Query(value = "select case when count(*) > 0 then true else false end from user_connection where user_connection.user1 = ?1 and user_connection.user2 = ?2", nativeQuery = true)
    Long CheckIfRequestExists(String email_did_request, String email_received_request);

    @Query(value = "select  user_connection.user2 from user_connection  where user_connection.user1 = ?1", nativeQuery = true)
    List<String> findUsersIRequested(String my_email);
    
    @Query(value = "select user_connection.connectionid from user_connection  where user_connection.user1 = ?1 and user_connection.user2 = ?2", nativeQuery = true)
    Long FindIdFromEmails(String myemail, String useremail); 

    @Transactional
    @Modifying
    @Query(value = "update user_connection  SET user1 = ?2 where user1 = ?1", nativeQuery = true)
    void ChangeUsers1WithEmail(String OldEmail, String NewEmail); 

    @Transactional
    @Modifying
    @Query(value = "update user_connection  SET user2 = ?2 where user2 = ?1", nativeQuery = true)
    void ChangeUsers2WithEmail(String OldEmail, String NewEmail); 

}