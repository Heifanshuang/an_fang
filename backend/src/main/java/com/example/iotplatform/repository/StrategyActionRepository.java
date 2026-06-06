package com.example.iotplatform.repository;

import com.example.iotplatform.entity.StrategyAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 策略动作Repository接口
 * 用于处理策略动作的CRUD操作
 */
@Repository
public interface StrategyActionRepository extends JpaRepository<StrategyAction, Long> {
    
    /**
     * 根据策略ID查询策略动作列表
     * @param strategyId 策略ID
     * @return 策略动作列表
     */
    List<StrategyAction> findByStrategyId(Long strategyId);
}
