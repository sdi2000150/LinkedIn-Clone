package com.Backend_v10.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Backend_v10.User.User;
import com.Backend_v10.User.UserRepository;
import com.Backend_v10.UserConnection.UserConnection;
import com.Backend_v10.UserConnection.UserConnectionRepository;
import com.Backend_v10.Jobs.Job;
import com.Backend_v10.JobApplication.JobApplication;
import com.Backend_v10.Articles.Article;
import com.Backend_v10.Comments.Comment;

import com.Backend_v10.Comments.CommentRepository;
import com.Backend_v10.Articles.ArticleRepository;
import com.Backend_v10.JobApplication.JobApplicationRepository;
import com.Backend_v10.Jobs.JobRepository;

// To encapsulate the method calls + repository saves
@Service
public class UserService {


    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private ArticleRepository articleRepo;

    @Autowired
    private JobApplicationRepository jobApplicationRepo;

    @Autowired
    private JobRepository jobRepo;

    @Autowired
    private UserConnectionRepository ConnRepo;

    @Transactional
    public void addArticle(User user, Article newArticle) {
        User userlocal = userRepo.findByEmail(user.getEmail()).get();
        userlocal.addArticle(newArticle);
        newArticle.setDateTime_of_Creation(LocalDateTime.now());
        newArticle.setOwner(user);
        userRepo.save(userlocal);
    }
    //maybe add save on article entity!!!!

    @Transactional
    public void addComment(Article article, User user, Comment newComment) {
        Article articlelocal = articleRepo.findById(article.getArticleID()).get();
        User userlocal = userRepo.findByEmail(user.getEmail()).get();
        articlelocal.addComment(newComment);
        userlocal.addComment(newComment);
        
        commentRepo.save(newComment);   //save the comment first to avoid duplication
        articleRepo.save(articlelocal);
        userRepo.save(userlocal);
    }

    @Transactional
    public void addJob(User user, Job newJob) {
        User userlocal = userRepo.findByEmail(user.getEmail()).get();
        newJob.setOwner(userlocal);
        userlocal.addJob(newJob);
        userRepo.save(userlocal);
    }

    @Transactional
    public void addJobApplication(Job job, User user, JobApplication newJobApplication) {
        Job joblocal = jobRepo.findById(job.getJobID()).get();
        User userlocal = userRepo.findByEmail(user.getEmail()).get();
        joblocal.addJobApplication(newJobApplication);
        userlocal.addJobApplication(newJobApplication);

        jobApplicationRepo.save(newJobApplication);   //save the job application first to avoid duplication
        jobRepo.save(joblocal);
        userRepo.save(userlocal);
    }

    @Transactional
    public void addContact(User user1, User user2) {
        User userlocal1 = userRepo.findByEmail(user1.getEmail()).get();
        User userlocal2 = userRepo.findByEmail(user2.getEmail()).get();
        userlocal1.addContact(userlocal2);
        userRepo.save(userlocal1);
        userRepo.save(userlocal2);
    }

    @Transactional
    public List<Article> return_articles_of_contacts(String email){
        Optional<User> user = userRepo.findByEmail(email);
        System.out.println("here");
        List<Long> articles_of_contactsids = userRepo.findContactArticles(user.get().getUserID());
        List<Article> articles_of_contacts = new ArrayList<>();
        for(int i = 0; i < articles_of_contactsids.size(); i++){
            Optional<Article> a = articleRepo.findById(articles_of_contactsids.get(i));
            System.out.println(a);
            articles_of_contacts.add(a.get());
            //articles_of_contacts.add
        }
        System.out.println(articles_of_contacts);
        return articles_of_contacts;
    }
    
    @Transactional
    public List<Article> return_my_articles(String email){
        Optional<User> user = userRepo.findByEmail(email);
        System.out.println("here");
        List<Long> articles_of_contactsids = userRepo.findMyArticles(user.get().getUserID());
        List<Article> articles_of_contacts = new ArrayList<>();
        for(int i = 0; i < articles_of_contactsids.size(); i++){
            Optional<Article> a = articleRepo.findById(articles_of_contactsids.get(i));
            System.out.println(a);
            articles_of_contacts.add(a.get());
            //articles_of_contacts.add
        }
        System.out.println(articles_of_contacts);
        return articles_of_contacts;
    }


    //AddRequest
    @Transactional
    public void addRequestPair(String myemail, String useremail){
        UserConnection newRequest = new UserConnection();
        newRequest.setUser1(myemail);
        newRequest.setUser2(useremail);
        ConnRepo.save(newRequest);
    }

    //Delete Received Request
    @Transactional
    public void DeleteRec(String user1,String user2){
        Long idToDelete = this.ConnRepo.FindIdFromEmails(user1, user2);
        this.ConnRepo.deleteById(idToDelete);
    }   


    @Transactional
    public void  DeleteConnection(String email1, String email2){
        Optional<User> u1 = this.userRepo.findByEmail(email1);
        Optional<User> u2 = this.userRepo.findByEmail(email2);
        System.out.println("CHECKKK" + u1.get().getMyContacts().size());
        u1.get().getMyContacts().remove(u2.get());
        u2.get().getMyContacts().remove(u1.get());


    }


    //Find What Kind of Connection relates 2 Users
    @Transactional
    public String Identify_Connection(String Myemail,String Useremail){
        //Check if User is a Contact
        Optional<User> Me = this.userRepo.findByEmail(Myemail);
        List<User> myContacts = Me.get().getMyContacts();
        for(User u: myContacts){
            if( u.getEmail().equals(Useremail) == true){
                    //User is Contact
                    return "Connected";
                }
            }
        //return "here?????";

        //Check if We Have sent a Request to User
        if(this.ConnRepo.CheckIfRequestExists(Myemail, Useremail) == 1L){
            return "Request Sent";
        }
        //Check if We Have Received a Request from User
        else if(this.ConnRepo.CheckIfRequestExists(Useremail, Myemail) == 1L)
            return "Got Request";
        else
            return "Sent Request";
        
    }

}