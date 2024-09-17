package com.Backend_v10.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Backend_v10.User.User;
import com.Backend_v10.User.UserRepository;
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

    @Transactional
    public void addArticle(User user, Article newArticle) {
        User userlocal = userRepo.findByEmail(user.getEmail()).get();
        userlocal.addArticle(newArticle);
        userRepo.save(userlocal);
    }

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
}