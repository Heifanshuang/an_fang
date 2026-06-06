package com.example.iotplatform.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Map;

/**
 * 数据点实体类
 * 用于表示传感器的单个数据点
 */
@Data
@Entity
@Table(name = "data_points")
public class DataPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 数据值
     */
    @Column(columnDefinition = "json")
    private Map<String, Object> value;

    /**
     * 记录时间
     */
    @Column(name = "record_time", nullable = false)
    private String recordTime;

    /**
     * 关联的传感器API标签
     */
    @Column(name = "api_tag", nullable = false)
    private String apiTag;

    /**
     * 关联的设备ID
     */
    @Column(name = "device_id", nullable = false)
    private Long deviceId;
}
