package com.example.iotplatform.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * 策略变量实体类
 * 用于表示策略的条件变量
 */
@Data
@Entity
@Table(name = "strategy_variables")
public class StrategyVariable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "strategy_variable_id")
    private Long strategyVariableId;

    /**
     * 关联的策略ID
     */
    @Column(name = "strategy_id", nullable = false)
    private Long strategyId;

    /**
     * 网关ID
     */
    @Column(name = "gateway_id")
    private Long gatewayId;

    /**
     * 网关设备ID
     */
    @Column(name = "gateway_device_id")
    private Long gatewayDeviceId;

    /**
     * 网关设备名称
     */
    @Column(name = "gateway_device_name")
    private String gatewayDeviceName;

    /**
     * API标签
     */
    @Column(name = "api_tag")
    private String apiTag;

    /**
     * 操作符
     */
    @Column
    private Integer operator;

    /**
     * 右值（比较值）
     */
    @Column(name = "right_value")
    private String rightValue;

    /**
     * 逻辑关系（AND/OR）
     */
    @Column(name = "and_or")
    private Integer andOr;

    /**
     * 变量类型
     */
    @Column
    private Integer kind;

    /**
     * 关联的策略
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "strategy_id", referencedColumnName = "strategy_id", insertable = false, updatable = false)
    private Strategy strategy;
}
