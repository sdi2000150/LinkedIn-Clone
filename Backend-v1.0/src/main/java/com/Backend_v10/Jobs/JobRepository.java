package com.Backend_v10.Jobs;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface JobRepository extends JpaRepository<Job,Long>{

}
