package com.Backend_v10.Jobs;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity             // This tells Hibernate to make a table out of this class
@Data               // Lombok annotation to create all the getters, setters, toString methods based on the fields
@AllArgsConstructor // Lombok annotation to create a constructor with all the arguments
@NoArgsConstructor  // Lombok annotation to create a constructor with no arguments
@Table(name = "Jobs")
public class Job{

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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long jobID;
    String title;
    WorkFields fieldOfWork; 
    Boolean fullTime;
    Regions region; 
    Integer numOfLikes;
    Experience levelOfExperience;
    Boolean needOfDegree;
    String otherRequirements;

    public Job(String title) {
        this.title = title;
    }
}
