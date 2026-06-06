package com.example.iotplatform.repository;

import com.example.iotplatform.entity.DataPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据点Repository接口
 * 用于处理数据点的CRUD操作
 */
@Repository
public interface DataPointRepository extends JpaRepository<DataPoint, Long> {

    /**
     * 根据设备ID和API标签查询数据点
     * @param deviceId 设备ID
     * @param apiTag API标签
     * @return 数据点列表
     */
    List<DataPoint> findByDeviceIdAndApiTag(Long deviceId, String apiTag);

    /**
     * 根据设备ID查询数据点
     * @param deviceId 设备ID
     * @return 数据点列表
     */
    List<DataPoint> findByDeviceId(Long deviceId);

    /**
     * 根据设备ID列表查询数据点
     * @param deviceIds 设备ID列表
     * @return 数据点列表
     */
    List<DataPoint> findByDeviceIdIn(List<Long> deviceIds);
}
