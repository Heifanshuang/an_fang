package com.example.iotplatform.controller;

import com.example.iotplatform.dto.ApiResponse;
import com.example.iotplatform.dto.PageResponse;
import com.example.iotplatform.entity.Strategy;
import com.example.iotplatform.service.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 策略Controller
 * 用于处理策略相关的HTTP请求
 */
@RestController
@RequestMapping("/Strategys")
public class StrategyController {

    @Autowired
    private StrategyService strategyService;

    /**
     * 查询单个策略
     * GET /Strategys/{strategyId}
     */
    @GetMapping("/{strategyId}")
    public ApiResponse<Strategy> getStrategyById(@PathVariable Long strategyId) {
        return strategyService.getStrategyById(strategyId);
    }

    /**
     * 查询策略列表
     * GET /Strategys
     */
    @GetMapping
    public ApiResponse<PageResponse<Strategy>> getStrategies(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long projectId,
            @RequestParam(defaultValue = "1") Integer pageIndex,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return strategyService.getStrategies(keyword, projectId, pageIndex, pageSize);
    }

    /**
     * 新增策略
     * POST /Strategys
     */
    @PostMapping
    public ApiResponse<Long> createStrategy(@RequestBody Strategy strategy) {
        return strategyService.createStrategy(strategy);
    }

    /**
     * 更新策略
     * PUT /Strategys/{strategyId}
     */
    @PutMapping("/{strategyId}")
    public ApiResponse<Object> updateStrategy(
            @PathVariable Long strategyId,
            @RequestBody Strategy strategy) {
        return strategyService.updateStrategy(strategyId, strategy);
    }

    /**
     * 删除策略
     * DELETE /Strategys
     */
    @DeleteMapping
    public ApiResponse<Object> deleteStrategies(@RequestBody List<Long> strategyIds) {
        return strategyService.deleteStrategies(strategyIds);
    }
}
