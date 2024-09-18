package com.Backend_v10.UserConnection;

import java.util.List;

import javax.swing.text.StyledEditorKit.BoldAction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserConnectionRepository extends JpaRepository<UserConnection,Long>{

    @Query(value = "select  user_connection.user1 from user_connection  where user_connection.user2 = ?1", nativeQuery = true)
    List<String> findUsersRequestingMe(String email_requested);
    
    @Query(value = "select case when count(req) > 0 then true else false end from user_connection where user_connection.user1 = ?1 and user_connection.user2 = ?2", nativeQuery = true)
    Boolean CheckIfRequestExists(String email_did_request, String email_received_request);

    @Query(value = "select  user_connection.user2 from user_connection  where user_connection.user1 = ?1", nativeQuery = true)
    List<String> findUsersIRequested(String my_email);
    
     @Query(value = "delete from user_connection  where user_connection.user1 = ?1 and user_connection.user2 = ?2", nativeQuery = true)
     List<String> DeleteRequest(String user1, String user2);
}