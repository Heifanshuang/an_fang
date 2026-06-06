package com.example.iotplatform.dto;

import lombok.Data;

import java.util.List;

/**
 * 数据上传DTO
 * 用于批量上传传感器数据
 */
@Data
public class DatasDTO {

    /**
     * API标签
     */
    private String apiTag;

    /**
     * 数据点列表
     */
    private List<PointDTO> pointDTO;
}
