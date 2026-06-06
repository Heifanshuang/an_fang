package com.example.iotplatform.repository;

import com.example.iotplatform.entity.StrategyRunTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 策略运行时间Repository接口
 * 用于处理策略运行时间的CRUD操作
 */
@Repository
public interface StrategyRunTimeRepository extends JpaRepository<StrategyRunTime, Long> {
    
    /**
     * 根据策略ID查询策略运行时间列表
     * @param strategyId 策略ID
     * @return 策略运行时间列表
     */
    List<StrategyRunTime> findByStrategyId(Long strategyId);
}
