package com.example.iotplatform.controller;

import com.example.iotplatform.dto.ApiResponse;
import com.example.iotplatform.dto.PageResponse;
import com.example.iotplatform.entity.Sensor;
import com.example.iotplatform.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    /**
     * 查询单个传感器
     * GET /devices/{deviceId}/Sensors/{apiTag}
     */
    @GetMapping("/{deviceId}/Sensors/{apiTag}")
    public ApiResponse<Sensor> getSensorById(@PathVariable Long deviceId, @PathVariable String apiTag) {
        return sensorService.getSensorById(deviceId, apiTag);
    }

    /**
     * 模糊查询传感器
     * GET /devices/{deviceId}/Sensors
     */
    @GetMapping("/{deviceId}/Sensors")
    public ApiResponse<List<Sensor>> getSensors(
            @PathVariable Long deviceId,
            @RequestParam(required = false) String keyword) {
        return sensorService.getSensors(deviceId, keyword);
    }

    /**
     * 添加新传感器
     * POST /devices/{deviceId}/Sensors
     */
    @PostMapping("/{deviceId}/Sensors")
    public ApiResponse<Long> createSensor(@PathVariable Long deviceId, @RequestBody Sensor sensor) {
        return sensorService.createSensor(deviceId, sensor);
    }

    /**
     * 更新某个传感器
     * PUT /devices/{deviceId}/Sensors/{apiTag}
     */
    @PutMapping("/{deviceId}/Sensors/{apiTag}")
    public ApiResponse<Object> updateSensor(
            @PathVariable Long deviceId,
            @PathVariable String apiTag,
            @RequestBody Sensor sensor) {
        return sensorService.updateSensor(deviceId, apiTag, sensor);
    }

    /**
     * 删除某个传感器
     * DELETE /devices/{deviceId}/Sensors/{apiTag}
     */
    @DeleteMapping("/{deviceId}/Sensors/{apiTag}")
    public ApiResponse<Object> deleteSensor(@PathVariable Long deviceId, @PathVariable String apiTag) {
        return sensorService.deleteSensor(deviceId, apiTag);
    }

}