package com.example.iotplatform.service;

import com.example.iotplatform.dto.ApiResponse;
import com.example.iotplatform.dto.DatasDTO;
import com.example.iotplatform.dto.PageResponse;
import com.example.iotplatform.entity.DataPoint;
import com.example.iotplatform.repository.DataPointRepository;
import com.example.iotplatform.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 传感数据Service
 * 用于处理传感数据的业务逻辑
 */
@Service
public class DataService {

    @Autowired
    private DataPointRepository dataPointRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    /**
     * 聚合查询传感数据
     * GET /devices/{deviceId}/Datas/Grouping
     */
    public ApiResponse<Map<String, Object>> getGroupedData(Long deviceId) {
        ApiResponse<Map<String, Object>> response = new ApiResponse<>();

        try {
            // 检查设备是否存在
            if (!deviceRepository.existsById(deviceId)) {
                response.setStatus(1);
                response.setStatusCode(0);
                response.setMsg("设备不存在");
                return response;
            }

            // 查询该设备的所有数据点
            List<DataPoint> dataPoints = dataPointRepository.findByDeviceId(deviceId);

            // 按API标签分组
            Map<String, List<DataPoint>> groupedByApiTag = new HashMap<>();
            for (DataPoint dataPoint : dataPoints) {
                groupedByApiTag.computeIfAbsent(dataPoint.getApiTag(), k -> new ArrayList<>())
                        .add(dataPoint);
            }

            // 构造返回结果
            Map<String, Object> resultObj = new HashMap<>();
            resultObj.put("Count", dataPoints.size());
            resultObj.put("DeviceId", deviceId);

            List<Map<String, Object>> dataPointsList = new ArrayList<>();
            for (Map.Entry<String, List<DataPoint>> entry : groupedByApiTag.entrySet()) {
                Map<String, Object> apiTagData = new HashMap<>();
                apiTagData.put("ApiTag", entry.getKey());

                List<Map<String, Object>> pointDTOs = new ArrayList<>();
                for (DataPoint dataPoint : entry.getValue()) {
                    Map<String, Object> pointDTO = new HashMap<>();
                    pointDTO.put("Value", dataPoint.getValue());
                    pointDTO.put("RecordTime", dataPoint.getRecordTime());
                    pointDTOs.add(pointDTO);
                }
                apiTagData.put("PointDTO", pointDTOs);
                dataPointsList.add(apiTagData);
            }
            resultObj.put("DataPoints", dataPointsList);

            response.setResultObj(resultObj);
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
     * 模糊查询传感数据
     * GET /devices/{deviceId}/Datas
     */
    public ApiResponse<Map<String, Object>> getData(Long deviceId, 
                                                   String apiTag, 
                                                   Integer pageIndex, 
                                                   Integer pageSize) {
        ApiResponse<Map<String, Object>> response = new ApiResponse<>();

        try {
            // 检查设备是否存在
            if (!deviceRepository.existsById(deviceId)) {
                response.setStatus(1);
                response.setStatusCode(0);
                response.setMsg("设备不存在");
                return response;
            }

            // 查询该设备的所有数据点
            List<DataPoint> dataPoints = dataPointRepository.findByDeviceId(deviceId);
            
            // 根据apiTag进行模糊过滤
            if (apiTag != null && !apiTag.isEmpty()) {
                dataPoints = dataPoints.stream()
                        .filter(dataPoint -> dataPoint.getApiTag().contains(apiTag))
                        .collect(java.util.stream.Collectors.toList());
            }

            // 按API标签分组
            Map<String, List<DataPoint>> groupedByApiTag = new HashMap<>();
            for (DataPoint dataPoint : dataPoints) {
                groupedByApiTag.computeIfAbsent(dataPoint.getApiTag(), k -> new ArrayList<>())
                        .add(dataPoint);
            }

            // 构造返回结果
            Map<String, Object> resultObj = new HashMap<>();
            resultObj.put("Count", dataPoints.size());
            resultObj.put("DeviceId", deviceId);

            List<Map<String, Object>> dataPointsList = new ArrayList<>();
            for (Map.Entry<String, List<DataPoint>> entry : groupedByApiTag.entrySet()) {
                Map<String, Object> apiTagData = new HashMap<>();
                apiTagData.put("ApiTag", entry.getKey());

                List<Map<String, Object>> pointDTOs = new ArrayList<>();
                for (DataPoint dataPoint : entry.getValue()) {
                    Map<String, Object> pointDTO = new HashMap<>();
                    pointDTO.put("Value", dataPoint.getValue());
                    pointDTO.put("RecordTime", dataPoint.getRecordTime());
                    pointDTOs.add(pointDTO);
                }
                apiTagData.put("PointDTO", pointDTOs);
                dataPointsList.add(apiTagData);
            }
            resultObj.put("DataPoints", dataPointsList);

            // 分页信息
            int totalCount = dataPoints.size();
            int pageCount = (int) Math.ceil((double) totalCount / pageSize);
            resultObj.put("PageCount", pageCount);
            resultObj.put("PageIndex", pageIndex);
            resultObj.put("RecordCount", totalCount);

            response.setResultObj(resultObj);
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
     * 上传传感数据
     * POST /devices/{deviceId}/Datas
     */
    public ApiResponse<Object> uploadData(Long deviceId, List<DatasDTO> datasDTOList) {
        ApiResponse<Object> response = new ApiResponse<>();

        try {
            // 检查设备是否存在
            if (!deviceRepository.existsById(deviceId)) {
                response.setStatus(1);
                response.setStatusCode(0);
                response.setMsg("设备不存在");
                return response;
            }

            // 保存所有数据点
            List<DataPoint> dataPointsToSave = new ArrayList<>();
            for (DatasDTO datasDTO : datasDTOList) {
                String apiTag = datasDTO.getApiTag();
                List<com.example.iotplatform.dto.PointDTO> pointDTOs = datasDTO.getPointDTO();

                for (com.example.iotplatform.dto.PointDTO pointDTO : pointDTOs) {
                    DataPoint dataPoint = new DataPoint();
                    dataPoint.setDeviceId(deviceId);
                    dataPoint.setApiTag(apiTag);
                    dataPoint.setValue(pointDTO.getValue());
                    dataPoint.setRecordTime(pointDTO.getRecordTime());
                    dataPointsToSave.add(dataPoint);
                }
            }

            dataPointRepository.saveAll(dataPointsToSave);

            response.setStatus(0);
            response.setStatusCode(1);
            response.setMsg("数据上传成功");

        } catch (Exception e) {
            response.setStatus(1);
            response.setStatusCode(0);
            response.setMsg("数据上传失败：" + e.getMessage());
        }

        return response;
    }
}
