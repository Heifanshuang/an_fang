package com.example.iotplatform.controller;

import com.example.iotplatform.dto.ApiResponse;
import com.example.iotplatform.dto.DatasDTO;
import com.example.iotplatform.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 传感数据Controller
 * 用于处理传感数据相关的HTTP请求
 */
@RestController
@RequestMapping("/devices")
public class DataController {

    @Autowired
    private DataService dataService;

    /**
     * 聚合查询传感数据
     * GET /devices/{deviceId}/Datas/Grouping
     */
    @GetMapping("/{deviceId}/Datas/Grouping")
    public ApiResponse<Map<String, Object>> getGroupedData(@PathVariable Long deviceId) {
        return dataService.getGroupedData(deviceId);
    }

    /**
     * 模糊查询传感数据
     * GET /devices/{deviceId}/Datas
     */
    @GetMapping("/{deviceId}/Datas")
    public ApiResponse<Map<String, Object>> getData(
            @PathVariable Long deviceId,
            @RequestParam(required = false) String apiTag,
            @RequestParam(defaultValue = "1") Integer pageIndex,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return dataService.getData(deviceId, apiTag, pageIndex, pageSize);
    }

    /**
     * 上传传感数据
     * POST /devices/{deviceId}/Datas
     */
    @PostMapping("/{deviceId}/Datas")
    public ApiResponse<Object> uploadData(
            @PathVariable Long deviceId,
            @RequestBody List<DatasDTO> datasDTOList) {
        return dataService.uploadData(deviceId, datasDTOList);
    }
}
