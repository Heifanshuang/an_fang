package com.example.iotplatform.service;

import com.example.iotplatform.dto.ApiResponse;
import com.example.iotplatform.dto.PageResponse;
import com.example.iotplatform.entity.Device;
import com.example.iotplatform.entity.Project;
import com.example.iotplatform.entity.Sensor;
import com.example.iotplatform.repository.DeviceRepository;
import com.example.iotplatform.repository.ProjectRepository;
import com.example.iotplatform.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private DeviceRepository deviceRepository;
    
    @Autowired
    private SensorRepository sensorRepository;

    /**
     * 查询单个项目
     */
    public ApiResponse<Project> getProjectById(Long projectId) {
        ApiResponse<Project> response = new ApiResponse<>();
        
        try {
            Project project = projectRepository.findById(projectId).orElse(null);
            
            if (project == null) {
                response.setStatus(1);
                response.setStatusCode(0);
                response.setMsg("项目不存在");
                return response;
            }
            
            response.setResultObj(project);
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
     * 模糊查询项目
     */
    public ApiResponse<PageResponse<Project>> getProjects(String keyword, Integer pageIndex, Integer pageSize) {
        ApiResponse<PageResponse<Project>> response = new ApiResponse<>();
        
        try {
            List<Project> projects;
            
            if (keyword != null && !keyword.isEmpty()) {
                projects = projectRepository.findByKeyword(keyword);
            } else {
                projects = projectRepository.findAll();
            }
            
            // 分页处理
            int start = (pageIndex - 1) * pageSize;
            int end = Math.min(start + pageSize, projects.size());
            List<Project> pageData = projects.subList(start, end);
            
            PageResponse<Project> pageResponse = new PageResponse<>();
            pageResponse.setPageSet(pageData);
            pageResponse.setPageCount((int) Math.ceil((double) projects.size() / pageSize));
            pageResponse.setPageIndex(pageIndex);
            pageResponse.setPageSize(pageSize);
            pageResponse.setRecordCount((long) projects.size());
            
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
     * 新增项目
     */
    public ApiResponse<Long> createProject(Project project) {
        ApiResponse<Long> response = new ApiResponse<>();
        
        try {
            // 设置创建时间（实际项目中应使用当前时间）
            project.setCreateDate("2024-01-06");
            
            Project savedProject = projectRepository.save(project);
            
            response.setResultObj(savedProject.getProjectId());
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
     * 更新项目
     */
    public ApiResponse<Integer> updateProject(Long projectId, Project project) {
        ApiResponse<Integer> response = new ApiResponse<>();
        
        try {
            Project existingProject = projectRepository.findById(projectId).orElse(null);
            
            if (existingProject == null) {
                response.setStatus(1);
                response.setStatusCode(0);
                response.setMsg("项目不存在");
                return response;
            }
            
            // 更新项目信息
            existingProject.setName(project.getName());
            existingProject.setIndustry(project.getIndustry());
            existingProject.setNetWorkKind(project.getNetWorkKind());
            existingProject.setProjectTag(project.getProjectTag());
            existingProject.setRemark(project.getRemark());
            
            projectRepository.save(existingProject);
            
            response.setResultObj(1);
            response.setStatus(0);
            response.setStatusCode(2);
            response.setMsg("更新成功");
            
        } catch (Exception e) {
            response.setStatus(1);
            response.setStatusCode(0);
            response.setMsg("更新失败：" + e.getMessage());
        }
        
        return response;
    }

    /**
     * 删除项目
     */
    public ApiResponse<Object> deleteProjects(List<Long> projectIds) {
        ApiResponse<Object> response = new ApiResponse<>();
        
        try {
            // 删除项目
            projectRepository.deleteAllById(projectIds);
            
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

    /**
     * 查询项目下所有设备的传感器
     */
    public ApiResponse<List<Sensor>> getProjectSensors(Long projectId) {
        ApiResponse<List<Sensor>> response = new ApiResponse<>();
        
        try {
            // 查询项目是否存在
            Project project = projectRepository.findById(projectId).orElse(null);
            if (project == null) {
                response.setStatus(1);
                response.setStatusCode(0);
                response.setMsg("项目不存在");
                return response;
            }
            
            // 查询项目下所有设备
            List<Device> devices = deviceRepository.findByProjectId(projectId);
            
            // 如果没有设备，返回空列表
            if (devices.isEmpty()) {
                response.setResultObj(List.of());
                response.setStatus(0);
                response.setStatusCode(1);
                response.setMsg("查询成功");
                return response;
            }
            
            // 查询所有设备的传感器
            List<Sensor> sensors = sensorRepository.findByDeviceIdIn(
                devices.stream().map(Device::getDeviceId).collect(Collectors.toList())
            );
            
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

}