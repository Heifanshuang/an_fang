package com.example.iotplatform.repository;

import com.example.iotplatform.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {

    /**
     * 根据设备ID和API标签查询传感器
     */
    Sensor findByDevice_DeviceIdAndApiTag(Long deviceId, String apiTag);

    /**
     * 根据设备ID查询所有传感器
     */
    List<Sensor> findByDevice_DeviceId(Long deviceId);

    /**
     * 根据设备ID和关键字模糊查询传感器
     */
    List<Sensor> findByDevice_DeviceIdAndNameContaining(Long deviceId, String keyword);

    /**
     * 根据设备ID删除所有传感器
     */
    void deleteByDevice_DeviceId(Long deviceId);

    /**
     * 根据设备ID和API标签删除传感器
     */
    void deleteByDevice_DeviceIdAndApiTag(Long deviceId, String apiTag);

    /**
     * 根据设备ID列表查询传感器
     */
    List<Sensor> findByDevice_DeviceIdIn(List<Long> deviceIds);

}