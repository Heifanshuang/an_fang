package com.example.iotplatform.controller;

import com.example.iotplatform.dto.ApiResponse;
import com.example.iotplatform.dto.PageResponse;
import com.example.iotplatform.entity.Device;
import com.example.iotplatform.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    /**
     * 批量查询设备最新数据
     * GET /Devices/Datas
     */
    @GetMapping("/Datas")
    public ApiResponse<List<Object>> getDeviceDatas(@RequestParam(required = false) List<Long> deviceIds) {
        return deviceService.getDeviceDatas(deviceIds);
    }

    /**
     * 批量查询设备的在线状态
     * GET /Devices/Status
     */
    @GetMapping("/Status")
    public ApiResponse<List<Object>> getDeviceStatus(@RequestParam(required = false) List<Long> deviceIds) {
        return deviceService.getDeviceStatus(deviceIds);
    }

    /**
     * 查询单个设备
     * GET /Devices/{deviceId}
     */
    @GetMapping("/{deviceId}")
    public ApiResponse<Device> getDeviceById(@PathVariable Long deviceId) {
        return deviceService.getDeviceById(deviceId);
    }

    /**
     * 模糊查询设备
     * GET /Devices
     */
    @GetMapping
    public ApiResponse<PageResponse<Device>> getDevices(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") Integer pageIndex,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return deviceService.getDevices(keyword, pageIndex, pageSize);
    }

    /**
     * 添加新设备
     * POST /Devices
     */
    @PostMapping
    public ApiResponse<Long> createDevice(@RequestBody Device device) {
        return deviceService.createDevice(device);
    }

    /**
     * 更新某个设备
     * PUT /Devices/{deviceId}
     */
    @PutMapping("/{deviceId}")
    public ApiResponse<Object> updateDevice(@PathVariable Long deviceId, @RequestBody Device device) {
        return deviceService.updateDevice(deviceId, device);
    }

    /**
     * 删除某个设备
     * DELETE /Devices/{deviceId}
     */
    @DeleteMapping("/{deviceId}")
    public ApiResponse<Object> deleteDevice(@PathVariable Long deviceId) {
        return deviceService.deleteDevice(deviceId);
    }

}