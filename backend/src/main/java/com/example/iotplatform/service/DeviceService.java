package com.example.iotplatform.service;

import com.example.iotplatform.dto.ApiResponse;
import com.example.iotplatform.dto.PageResponse;
import com.example.iotplatform.entity.Device;
import com.example.iotplatform.repository.DeviceRepository;
import com.example.iotplatform.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private SensorRepository sensorRepository;

    /**
     * 批量查询设备最新数据
     */
    public ApiResponse<List<Object>> getDeviceDatas(List<Long> deviceIds) {
        ApiResponse<List<Object>> response = new ApiResponse<>();
        
        try {
            List<Device> devices;
            
            if (deviceIds != null && !deviceIds.isEmpty()) {
                devices = deviceRepository.findByDeviceIdIn(deviceIds);
            } else {
                devices = deviceRepository.findAll();
            }
            
            // 构造返回数据（实际项目中应查询设备的最新传感器数据）
            List<Object> result = new ArrayList<>();
            for (Device device : devices) {
                result.add(device);
            }
            
            response.setResultObj(result);
            response.setStatus(0);
            response.setStatusCode(1);
            response.setMsg("查询成功");
            
        } catch (Exception e) {
            response.setStatus(1);
            response.setStatusCode(0);
            response.setMsg("查询失败：" + e.getMessage());
        }
        
        return response;
    }

    /**
     * 批量查询设备的在线状态
     */
    public ApiResponse<List<Object>> getDeviceStatus(List<Long> deviceIds) {
        ApiResponse<List<Object>> response = new ApiResponse<>();
        
        try {
            List<Device> devices;
            
            if (deviceIds != null && !deviceIds.isEmpty()) {
                devices = deviceRepository.findByDeviceIdIn(deviceIds);
            } else {
                devices = deviceRepository.findAll();
            }
            
            // 构造返回数据
            List<Object> result = new ArrayList<>();
            for (Device device : devices) {
                result.add(device);
            }
            
            response.setResultObj(result);
            response.setStatus(0);
            response.setStatusCode(1);
            response.setMsg("查询成功");
            
        } catch (Exception e) {
            response.setStatus(1);
            response.setStatusCode(0);
            response.setMsg("查询失败：" + e.getMessage());
        }
        
        return response;
    }

    /**
     * 查询单个设备
     */
    public ApiResponse<Device> getDeviceById(Long deviceId) {
        ApiResponse<Device> response = new ApiResponse<>();
        
        try {
            Device device = deviceRepository.findById(deviceId).orElse(null);
            
            if (device == null) {
                response.setStatus(1);
                response.setStatusCode(0);
                response.setMsg("设备不存在");
                return response;
            }
            
            response.setResultObj(device);
            response.setStatus(0);
            response.setStatusCode(1);
            response.setMsg("查询成功");
            
        } catch (Exception e) {
            response.setStatus(1);
            response.setStatusCode(0);
            response.setMsg("查询失败：" + e.getMessage());
        }
        
        return response;
    }

    /**
     * 模糊查询设备
     */
    public ApiResponse<PageResponse<Device>> getDevices(String keyword, Integer pageIndex, Integer pageSize) {
        ApiResponse<PageResponse<Device>> response = new ApiResponse<>();
        
        try {
            List<Device> devices;
            
            if (keyword != null && !keyword.isEmpty()) {
                devices = deviceRepository.findByKeyword(keyword);
            } else {
                devices = deviceRepository.findAll();
            }
            
            // 分页处理
            int start = (pageIndex - 1) * pageSize;
            int end = Math.min(start + pageSize, devices.size());
            List<Device> pageData = devices.subList(start, end);
            
            PageResponse<Device> pageResponse = new PageResponse<>();
            pageResponse.setPageSet(pageData);
            pageResponse.setPageCount((int) Math.ceil((double) devices.size() / pageSize));
            pageResponse.setPageIndex(pageIndex);
            pageResponse.setPageSize(pageSize);
            pageResponse.setRecordCount((long) devices.size());
            
            response.setResultObj(pageResponse);
            response.setStatus(0);
            response.setStatusCode(1);
            response.setMsg("查询成功");
            
        } catch (Exception e) {
            response.setStatus(1);
            response.setStatusCode(0);
            response.setMsg("查询失败：" + e.getMessage());
        }
        
        return response;
    }

    /**
     * 添加新设备
     */
    public ApiResponse<Long> createDevice(Device device) {
        ApiResponse<Long> response = new ApiResponse<>();
        
        try {
            // 设置创建时间（实际项目中应使用当前时间）
            device.setCreateDate("2024-01-06");
            device.setIsOnline(false);
            
            Device savedDevice = deviceRepository.save(device);
            
            response.setResultObj(savedDevice.getDeviceId());
            response.setStatus(0);
            response.setStatusCode(2);
            response.setMsg("创建成功");
            
        } catch (Exception e) {
            response.setStatus(1);
            response.setStatusCode(0);
            response.setMsg("创建失败：" + e.getMessage());
        }
        
        return response;
    }

    /**
     * 更新某个设备
     */
    public ApiResponse<Object> updateDevice(Long deviceId, Device device) {
        ApiResponse<Object> response = new ApiResponse<>();
        
        try {
            Device existingDevice = deviceRepository.findById(deviceId).orElse(null);
            
            if (existingDevice == null) {
                response.setStatus(1);
                response.setStatusCode(0);
                response.setMsg("设备不存在");
                return response;
            }
            
            // 更新设备信息
            existingDevice.setName(device.getName());
            existingDevice.setTag(device.getTag());
            existingDevice.setSecurityKey(device.getSecurityKey());
            existingDevice.setProtocol(device.getProtocol());
            existingDevice.setIsShare(device.getIsShare());
            existingDevice.setIsTrans(device.getIsTrans());
            existingDevice.setCoordinate(device.getCoordinate());
            
            deviceRepository.save(existingDevice);
            
            response.setStatus(0);
            response.setStatusCode(1);
            response.setMsg("更新成功");
            
        } catch (Exception e) {
            response.setStatus(1);
            response.setStatusCode(0);
            response.setMsg("更新失败：" + e.getMessage());
        }
        
        return response;
    }

    /**
     * 删除某个设备
     */
    public ApiResponse<Object> deleteDevice(Long deviceId) {
        ApiResponse<Object> response = new ApiResponse<>();
        
        try {
            // 删除设备前先删除关联的传感器
            sensorRepository.deleteByDevice_DeviceId(deviceId);
            
            // 删除设备
            deviceRepository.deleteById(deviceId);
            
            response.setStatus(0);
            response.setStatusCode(1);
            response.setMsg("删除成功");
            
        } catch (Exception e) {
            response.setStatus(1);
            response.setStatusCode(0);
            response.setMsg("删除失败：" + e.getMessage());
        }
        
        return response;
    }

}