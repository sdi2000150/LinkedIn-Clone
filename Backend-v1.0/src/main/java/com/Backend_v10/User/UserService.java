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
        user.addArticle(newArticle);
        userRepo.save(user);
    }

    @Transactional
    public void addComment(Article article, User user, Comment newComment) {
        article.addComment(newComment);
        user.addComment(newComment);
        
        commentRepo.save(newComment);   //save the comment first to avoid duplication
        articleRepo.save(article);
        userRepo.save(user);
    }

    @Transactional
    public void addJob(User user, Job newJob) {
        user.addJob(newJob);
        userRepo.save(user);
    }

    @Transactional
    public void addJobApplication(Job job, User user, JobApplication newJobApplication) {
        job.addJobApplication(newJobApplication);
        user.addJobApplication(newJobApplication);

        jobApplicationRepo.save(newJobApplication);   //save the job application first to avoid duplication
        jobRepo.save(job);
        userRepo.save(user);
    }

    @Transactional
    public void addContact(User user1, User user2) {
        user1.addContact(user2);
        userRepo.save(user1);
        userRepo.save(user2);
    }
}