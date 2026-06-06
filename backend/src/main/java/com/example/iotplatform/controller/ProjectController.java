package com.example.iotplatform.controller;

import com.example.iotplatform.dto.ApiResponse;
import com.example.iotplatform.dto.PageResponse;
import com.example.iotplatform.entity.Project;
import com.example.iotplatform.entity.Sensor;
import com.example.iotplatform.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    /**
     * 查询单个项目
     * GET /Projects/{projectId}
     */
    @GetMapping("/{projectId}")
    public ApiResponse<Project> getProjectById(@PathVariable Long projectId) {
        return projectService.getProjectById(projectId);
    }

    /**
     * 模糊查询项目
     * GET /Projects
     */
    @GetMapping
    public ApiResponse<PageResponse<Project>> getProjects(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") Integer pageIndex,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return projectService.getProjects(keyword, pageIndex, pageSize);
    }

    /**
     * 新增项目
     * POST /Projects
     */
    @PostMapping
    public ApiResponse<Long> createProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }

    /**
     * 更新项目
     * PUT /Projects/{projectId}
     */
    @PutMapping("/{projectId}")
    public ApiResponse<Integer> updateProject(@PathVariable Long projectId, @RequestBody Project project) {
        return projectService.updateProject(projectId, project);
    }

    /**
     * 删除项目
     * DELETE /Projects
     */
    @DeleteMapping
    public ApiResponse<Object> deleteProjects(@RequestBody List<Long> projectIds) {
        return projectService.deleteProjects(projectIds);
    }

    /**
     * 查询项目下所有设备的传感器
     * GET /Projects/{projectId}/Sensors
     */
    @GetMapping("/{projectId}/Sensors")
    public ApiResponse<List<Sensor>> getProjectSensors(@PathVariable Long projectId) {
        return projectService.getProjectSensors(projectId);
    }

}