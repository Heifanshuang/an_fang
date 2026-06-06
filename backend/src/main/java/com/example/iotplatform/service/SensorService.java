package com.example.iotplatform.service;

import com.example.iotplatform.dto.ApiResponse;
import com.example.iotplatform.entity.Device;
import com.example.iotplatform.entity.Sensor;
import com.example.iotplatform.repository.DeviceRepository;
import com.example.iotplatform.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    /**
     * 查询单个传感器
     */
    public ApiResponse<Sensor> getSensorById(Long deviceId, String apiTag) {
        ApiResponse<Sensor> response = new ApiResponse<>();
        
        try {
            Sensor sensor = sensorRepository.findByDevice_DeviceIdAndApiTag(deviceId, apiTag);
            
            if (sensor == null) {
                response.setStatus(1);
                response.setStatusCode(0);
                response.setMsg("传感器不存在");
                return response;
            }
            
            response.setResultObj(sensor);
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
     * 模糊查询传感器
     */
    public ApiResponse<List<Sensor>> getSensors(Long deviceId, String keyword) {
        ApiResponse<List<Sensor>> response = new ApiResponse<>();
        
        try {
            List<Sensor> sensors;
            
            if (keyword != null && !keyword.isEmpty()) {
                sensors = sensorRepository.findByDevice_DeviceIdAndNameContaining(deviceId, keyword);
            } else {
                sensors = sensorRepository.findByDevice_DeviceId(deviceId);
            }
            
            response.setResultObj(sensors);
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
     * 添加新传感器
     */
    public ApiResponse<Long> createSensor(Long deviceId, Sensor sensor) {
        ApiResponse<Long> response = new ApiResponse<>();
        
        try {
            // 检查设备是否存在
            Device device = deviceRepository.findById(deviceId).orElse(null);
            
            if (device == null) {
                response.setStatus(1);
                response.setStatusCode(0);
                response.setMsg("设备不存在");
                return response;
            }
            
            // 设置传感器的设备和创建时间
            sensor.setDevice(device);
            sensor.setCreateDate("2024-01-06");
            
            Sensor savedSensor = sensorRepository.save(sensor);
            
            response.setResultObj(savedSensor.getId());
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
     * 更新某个传感器
     */
    public ApiResponse<Object> updateSensor(Long deviceId, String apiTag, Sensor sensor) {
        ApiResponse<Object> response = new ApiResponse<>();
        
        try {
            // 查找要更新的传感器
            Sensor existingSensor = sensorRepository.findByDevice_DeviceIdAndApiTag(deviceId, apiTag);
            
            if (existingSensor == null) {
                response.setStatus(1);
                response.setStatusCode(0);
                response.setMsg("传感器不存在");
                return response;
            }
            
            // 更新传感器信息
            existingSensor.setName(sensor.getName());
            existingSensor.setGroups(sensor.getGroups());
            existingSensor.setProtocol(sensor.getProtocol());
            existingSensor.setTransType(sensor.getTransType());
            existingSensor.setDataType(sensor.getDataType());
            existingSensor.setTypeAttrs(sensor.getTypeAttrs());
            existingSensor.setSensorType(sensor.getSensorType());
            existingSensor.setGroupId(sensor.getGroupId());
            existingSensor.setCoordinate(sensor.getCoordinate());
            existingSensor.setValue(sensor.getValue());
            existingSensor.setRecordTime(sensor.getRecordTime());
            existingSensor.setUnit(sensor.getUnit());
            existingSensor.setOperType(sensor.getOperType());
            existingSensor.setOperTypeAttrs(sensor.getOperTypeAttrs());
            existingSensor.setHttpIp(sensor.getHttpIp());
            existingSensor.setHttpPort(sensor.getHttpPort());
            existingSensor.setUserName(sensor.getUserName());
            existingSensor.setPassword(sensor.getPassword());
            existingSensor.setVideoStreamUrl(sensor.getVideoStreamUrl());
            existingSensor.setVideoStreamProtocol(sensor.getVideoStreamProtocol());
            existingSensor.setVideoStreamPort(sensor.getVideoStreamPort());
            existingSensor.setCtrlUrl(sensor.getCtrlUrl());
            
            sensorRepository.save(existingSensor);
            
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
     * 删除某个传感器
     */
    public ApiResponse<Object> deleteSensor(Long deviceId, String apiTag) {
        ApiResponse<Object> response = new ApiResponse<>();
        
        try {
            // 查找要删除的传感器
            Sensor sensor = sensorRepository.findByDevice_DeviceIdAndApiTag(deviceId, apiTag);
            
            if (sensor == null) {
                response.setStatus(1);
                response.setStatusCode(0);
                response.setMsg("传感器不存在");
                return response;
            }
            
            // 删除传感器
            sensorRepository.deleteByDevice_DeviceIdAndApiTag(deviceId, apiTag);
            
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