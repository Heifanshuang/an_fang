package com.example.iotplatform.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * 策略动作实体类
 * 用于表示策略触发时执行的动作
 */
@Data
@Entity
@Table(name = "strategy_actions")
public class StrategyAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "action_id")
    private Long actionId;

    /**
     * 关联的策略ID
     */
    @Column(name = "strategy_id", nullable = false)
    private Long strategyId;

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
     * 设置值
     */
    @Column(name = "set_value")
    private String setValue;

    /**
     * 延迟执行时间（毫秒）
     */
    @Column
    private Integer delay;

    /**
     * 关联的策略
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "strategy_id", referencedColumnName = "strategy_id", insertable = false, updatable = false)
    private Strategy strategy;
}
