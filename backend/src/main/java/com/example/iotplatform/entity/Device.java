package com.example.iotplatform.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "devices")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private Long deviceId;

    @Column(nullable = false)
    private String name;

    @Column
    private String tag;

    @Column(name = "security_key")
    private String securityKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column
    private String protocol;

    @Column(name = "is_online")
    private Boolean isOnline;

    @Column(name = "last_online_ip")
    private String lastOnlineIp;

    @Column(name = "last_online_time")
    private String lastOnlineTime;

    @Column
    private String coordinate;

    @Column(name = "create_date")
    private String createDate;

    @Column(name = "is_share")
    private Boolean isShare;

    @Column(name = "is_trans")
    private Boolean isTrans;

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Sensor> sensors;

}