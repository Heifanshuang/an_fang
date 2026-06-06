package com.example.iotplatform.repository;

import com.example.iotplatform.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    /**
     * 根据项目ID查询设备
     */
    List<Device> findByProjectId(Long projectId);

    /**
     * 模糊查询设备
     */
    @Query("SELECT d FROM Device d WHERE d.name LIKE %?1% OR d.tag LIKE %?1%")
    List<Device> findByKeyword(String keyword);

    /**
     * 根据设备ID列表查询设备
     */
    List<Device> findByDeviceIdIn(List<Long> deviceIds);

}