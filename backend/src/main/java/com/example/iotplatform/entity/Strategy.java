package com.example.iotplatform.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 策略实体类
 * 用于表示设备策略的基本信息
 */
@Data
@Entity
@Table(name = "strategies")
public class Strategy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "strategy_id")
    private Long strategyId;

    /**
     * 网关ID
     */
    @Column(name = "gateway_id")
    private Long gatewayId;

    /**
     * 网关名称
     */
    @Column(name = "gateway_name")
    private String gatewayName;

    /**
     * 策略类型
     */
    @Column
    private Integer kind;

    /**
     * 策略条件（表达式）
     */
    @Column
    private String condition;

    /**
     * 策略条件（中文描述）
     */
    @Column(name = "condition_cn")
    private String conditionCn;

    /**
     * 策略状态
     */
    @Column
    private Integer nullity;

    /**
     * 创建日期
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 创建用户ID
     */
    @Column(name = "create_user_id")
    private Long createUserId;

    /**
     * 项目ID
     */
    @Column(name = "project_id")
    private Long projectId;

    /**
     * 关联的策略变量列表
     */
    @OneToMany(mappedBy = "strategy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StrategyVariable> strategyVariableList = new ArrayList<>();

    /**
     * 关联的策略动作列表
     */
    @OneToMany(mappedBy = "strategy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StrategyAction> strategyActionList = new ArrayList<>();

    /**
     * 关联的策略运行时间列表
     */
    @OneToMany(mappedBy = "strategy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StrategyRunTime> strategyRunTimeList = new ArrayList<>();
}
