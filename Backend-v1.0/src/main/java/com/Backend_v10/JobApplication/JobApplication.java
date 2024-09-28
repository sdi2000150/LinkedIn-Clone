package com.Backend_v10.JobApplication;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.Backend_v10.Articles.Article;
import com.Backend_v10.Comments.Comment;
import com.Backend_v10.Jobs.Job;
import com.Backend_v10.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Data
@Table (name = "JobApplications")
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"job"})
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long jobApplicationID;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @Override
    public String toString() {
        return "JobApplication [jobApplicationID=" + jobApplicationID + ", user=" + user + ", job=" + job + "]";
    }

}