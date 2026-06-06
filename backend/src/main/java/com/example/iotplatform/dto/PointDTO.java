package com.example.iotplatform.dto;

import lombok.Data;

import java.util.Map;

/**
 * 数据点DTO
 * 用于表示单个传感器数据点的传输对象
 */
@Data
public class PointDTO {

    /**
     * 数据值
     */
    private Map<String, Object> value;

    /**
     * 记录时间
     */
    private String recordTime;
}
