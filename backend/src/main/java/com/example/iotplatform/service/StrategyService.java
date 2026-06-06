package com.example.iotplatform.service;

import com.example.iotplatform.dto.ApiResponse;
import com.example.iotplatform.dto.PageResponse;
import com.example.iotplatform.entity.Strategy;
import com.example.iotplatform.repository.StrategyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 策略Service
 * 用于处理策略的业务逻辑
 */
@Service
public class StrategyService {

    @Autowired
    private StrategyRepository strategyRepository;

    /**
     * 查询单个策略
     * GET /Strategys/{strategyId}
     */
    public ApiResponse<Strategy> getStrategyById(Long strategyId) {
        ApiResponse<Strategy> response = new ApiResponse<>();

        try {
            Strategy strategy = strategyRepository.findById(strategyId).orElse(null);

            if (strategy == null) {
                response.setStatus(1);
                response.setStatusCode(0);
                response.setMsg("策略不存在");
                return response;
            }

            response.setResultObj(strategy);
            response.setStatus(0);
            response.setStatusCode(1);
            response.setMsg("查询成功");

        } catch (Exception e) {
            response.setStatus(1);
            response.setStatusCode(0);
            response.setMsg("查询失败：" + e.getMessage());
        }

        return response;
    }

    /**
     * 查询策略列表
     * GET /Strategys
     */
    public ApiResponse<PageResponse<Strategy>> getStrategies(
            String keyword,
            Long projectId,
            Integer pageIndex,
            Integer pageSize) {
        ApiResponse<PageResponse<Strategy>> response = new ApiResponse<>();

        try {
            // 根据条件查询策略
            List<Strategy> strategies;
            if (projectId != null) {
                // 根据项目ID查询
                strategies = strategyRepository.findByProjectId(projectId);
            } else {
                // 查询所有策略
                strategies = strategyRepository.findAll();
            }
            
            // 根据关键字过滤
            if (keyword != null && !keyword.isEmpty()) {
                strategies = strategies.stream()
                        .filter(strategy -> strategy.getName() != null && strategy.getName().contains(keyword) ||
                                strategy.getConditionCn() != null && strategy.getConditionCn().contains(keyword))
                        .collect(java.util.stream.Collectors.toList());
            }

            // 分页处理
            int start = (pageIndex - 1) * pageSize;
            int end = Math.min(start + pageSize, strategies.size());
            List<Strategy> pageData = strategies.subList(start, end);

            PageResponse<Strategy> pageResponse = new PageResponse<>();
            pageResponse.setPageSet(pageData);
            pageResponse.setPageCount((int) Math.ceil((double) strategies.size() / pageSize));
            pageResponse.setPageIndex(pageIndex);
            pageResponse.setPageSize(pageSize);
            pageResponse.setRecordCount((long) strategies.size());

            response.setResultObj(pageResponse);
            response.setStatus(0);
            response.setStatusCode(1);
            response.setMsg("查询成功");

        } catch (Exception e) {
            response.setStatus(1);
            response.setStatusCode(0);
            response.setMsg("查询失败：" + e.getMessage());
        }

        return response;
    }

    /**
     * 新增策略
     * POST /Strategys
     */
    public ApiResponse<Long> createStrategy(Strategy strategy) {
        ApiResponse<Long> response = new ApiResponse<>();

        try {
            // 设置创建时间
            strategy.setCreateDate(new Date());

            Strategy savedStrategy = strategyRepository.save(strategy);

            response.setResultObj(savedStrategy.getStrategyId());
            response.setStatus(0);
            response.setStatusCode(2);
            response.setMsg("创建成功");

        } catch (Exception e) {
            response.setStatus(1);
            response.setStatusCode(0);
            response.setMsg("创建失败：" + e.getMessage());
        }

        return response;
    }

    /**
     * 更新策略
     * PUT /Strategys/{strategyId}
     */
    public ApiResponse<Object> updateStrategy(Long strategyId, Strategy strategy) {
        ApiResponse<Object> response = new ApiResponse<>();

        try {
            Strategy existingStrategy = strategyRepository.findById(strategyId).orElse(null);

            if (existingStrategy == null) {
                response.setStatus(1);
                response.setStatusCode(0);
                response.setMsg("策略不存在");
                return response;
            }

            // 更新策略信息
            existingStrategy.setGatewayId(strategy.getGatewayId());
            existingStrategy.setGatewayName(strategy.getGatewayName());
            existingStrategy.setKind(strategy.getKind());
            existingStrategy.setCondition(strategy.getCondition());
            existingStrategy.setConditionCn(strategy.getConditionCn());
            existingStrategy.setNullity(strategy.getNullity());
            existingStrategy.setProjectId(strategy.getProjectId());

            strategyRepository.save(existingStrategy);

            response.setStatus(0);
            response.setStatusCode(1);
            response.setMsg("更新成功");

        } catch (Exception e) {
            response.setStatus(1);
            response.setStatusCode(0);
            response.setMsg("更新失败：" + e.getMessage());
        }

        return response;
    }

    /**
     * 删除策略
     * DELETE /Strategys
     */
    public ApiResponse<Object> deleteStrategies(List<Long> strategyIds) {
        ApiResponse<Object> response = new ApiResponse<>();

        try {
            // 删除多个策略
            strategyRepository.deleteAllById(strategyIds);

            response.setStatus(0);
            response.setStatusCode(1);
            response.setMsg("删除成功");

        } catch (Exception e) {
            response.setStatus(1);
            response.setStatusCode(0);
            response.setMsg("删除失败：" + e.getMessage());
        }

        return response;
    }
}
