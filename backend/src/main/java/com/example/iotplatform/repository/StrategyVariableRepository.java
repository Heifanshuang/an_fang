package com.example.iotplatform.repository;

import com.example.iotplatform.entity.StrategyVariable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 策略变量Repository接口
 * 用于处理策略变量的CRUD操作
 */
@Repository
public interface StrategyVariableRepository extends JpaRepository<StrategyVariable, Long> {
    
    /**
     * 根据策略ID查询策略变量列表
     * @param strategyId 策略ID
     * @return 策略变量列表
     */
    List<StrategyVariable> findByStrategyId(Long strategyId);
}
