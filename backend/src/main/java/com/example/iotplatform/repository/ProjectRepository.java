package com.example.iotplatform.repository;

import com.example.iotplatform.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    /**
     * 模糊查询项目
     */
    @Query("SELECT p FROM Project p WHERE p.name LIKE %?1% OR p.projectTag LIKE %?1%")
    List<Project> findByKeyword(String keyword);

}