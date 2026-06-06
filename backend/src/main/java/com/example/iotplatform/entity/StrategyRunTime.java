package com.example.iotplatform.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * 策略运行时间实体类
 * 用于表示策略的运行时间配置
 */
@Data
@Entity
@Table(name = "strategy_run_times")
public class StrategyRunTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "run_time_id")
    private Long runTimeId;

    /**
     * 关联的策略ID
     */
    @Column(name = "strategy_id", nullable = false)
    private Long strategyId;

    /**
     * 运行周期（1: 一次性, 2: 每天, 3: 每周, 4: 每月）
     */
    @Column
    private Integer period;

    /**
     * 运行星期/日期
     */
    @Column
    private Integer day;

    /**
     * 运行时间
     */
    @Column
    private Date time;

    /**
     * 关联的策略
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "strategy_id", referencedColumnName = "strategy_id", insertable = false, updatable = false)
    private Strategy strategy;
}
