package com.Backend_v10.JobApplication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication,Long>{
    @Transactional
    @Modifying
    @Query(value = "delete from job_applications ja where ja.job_id = ?1",nativeQuery = true)
    void DeleteApplicationsOfJob(@Param("id") Long id);
}
