package com.example.iotplatform.repository;

import com.example.iotplatform.entity.Strategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 策略Repository接口
 * 用于处理策略的CRUD操作
 */
@Repository
public interface StrategyRepository extends JpaRepository<Strategy, Long> {
    /**
     * 根据项目ID查询策略列表
     * @param projectId 项目ID
     * @return 策略列表
     */
    List<Strategy> findByProjectId(Long projectId);
}
