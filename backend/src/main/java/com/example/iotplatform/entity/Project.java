package com.example.iotplatform.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;

    @Column(nullable = false)
    private String name;

    @Column
    private String industry;

    @Column(name = "net_work_kind")
    private String netWorkKind;

    @Column(name = "project_tag")
    private String projectTag;

    @Column(name = "create_date")
    private String createDate;

    @Column
    private String remark;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Device> devices;

}