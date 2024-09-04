package com.Backend_v10.Jobs;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;




@Entity
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
        Tripoli,
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
    public enum Expirience{
        Junior,
        Mid,
        Senior;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long JobID;
    String Title;
    WorkFields FieldOfWork; 
    Boolean FullTime;
    Regions Region; 
    Integer NumOfLikes;
    Expirience LevelOfExpirience;
    Boolean NeedOfDegree;
    String OtherRequirements;


    public Job(String title, WorkFields field){
        this.Title = title;
        this.FieldOfWork = field;
    }
}
