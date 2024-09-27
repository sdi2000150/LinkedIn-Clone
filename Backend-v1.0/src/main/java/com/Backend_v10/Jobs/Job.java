package com.Backend_v10.Jobs;

import java.util.ArrayList;
import java.util.HashSet;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;

import com.Backend_v10.Comments.Comment;
import com.Backend_v10.JobApplication.JobApplication;
import com.Backend_v10.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import static jakarta.persistence.FetchType.LAZY;


@Entity             // This tells Hibernate to make a table out of this class
@Data               // Lombok annotation to create all the getters, setters, toString methods based on the fields
@NoArgsConstructor  // Lombok annotation to create a constructor with no arguments
@Table(name = "Jobs")
@JsonIgnoreProperties({"JobApplications"})
public class Job{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long jobID;
    String title;
    Boolean needOfDegree;
    Integer salary;
    Boolean fullTime;
    String otherRequirements;

    @OneToMany  //no cascade here, cascade for jobApplication is in the User
    @JoinColumn(name = "job_id")
    @JsonIgnore
    List<JobApplication> JobApplications;

    @ManyToOne
    @JoinColumn(name = "job_user_id",referencedColumnName = "UserID")
    User Owner;
    //simple constuctor for testing
    public Job(String title) {
        this.title = title;
        this.JobApplications = new ArrayList<>();
    }

    //all args contructor
    public Job(String title, Boolean needOfDegree, String otherRequirements, Integer salary, Boolean fullTime) {
        this.title = title;
        this.needOfDegree = needOfDegree;
        this.otherRequirements = otherRequirements;
        this.salary = salary;
        this.fullTime = fullTime;
        this.JobApplications = new ArrayList<>();
    }

    public void addJobApplication(JobApplication jobApplication) {
        this.JobApplications.add(jobApplication);
        jobApplication.setJob(this);
    }

    @Override
    public String toString() {
        return "Job [jobID=" + jobID + ", title=" + title + ", needOfDegree=" + needOfDegree + ", salary=" + salary
                + ", fullTime=" + fullTime + ", otherRequirements=" + otherRequirements + "]";
    }


}
