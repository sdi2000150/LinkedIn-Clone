package com.Backend_v10.Jobs;

import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.ArrayList;

import com.Backend_v10.User.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;



@Entity             // This tells Hibernate to make a table out of this class
@Data               // Lombok annotation to create all the getters, setters, toString methods based on the fields
@AllArgsConstructor // Lombok annotation to create a constructor with all the arguments
@NoArgsConstructor  // Lombok annotation to create a constructor with no arguments
@Table(name = "Jobs")
public class Job{



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long jobID;
    String title;
    Boolean needOfDegree;
    String otherRequirements;
    Integer numOfLikes;
    Boolean fullTime;

    @Enumerated(EnumType.STRING)
    WorkFields fieldOfWork; 

    @Enumerated(EnumType.STRING)
    Regions region; 

    @Enumerated(EnumType.STRING)
    Experience levelOfExperience;


    @ManyToMany(mappedBy = "appliedJobs")
    @JsonManagedReference // Manages applied jobs
    private List<User> applicants = new ArrayList<>(); // Users who applied for this job

    //simple constuctor for testing
    public Job(String title) {
        this.title = title;
    }

    public enum WorkFields{
        Ecomomics,
        Agriculture,
        Health,
        Consruction,
        Marketing,
        Art,
        Law,
        Diplomacy,
        Teaching,
        ComputerScience,
        Psycology,
        Medicine;
    }

    public enum Regions{
        Athens,
        Thessaloniki,
        Patra,
        Volos,
        Trikala,
        Chania,
        Herakleion,
        Rhodes,
        Karditsa,
        Larissa,
        Nauplio,
        Syros,
        Mykonos,
        Kimi;
    }
    public enum Experience{
        Junior,
        Mid,
        Senior;
    }
}
