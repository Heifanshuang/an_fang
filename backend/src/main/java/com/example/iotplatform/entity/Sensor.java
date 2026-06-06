package com.example.iotplatform.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Map;

@Data
@Entity
@Table(name = "sensors")
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "api_tag", nullable = false)
    private String apiTag;

    @Column
    private Integer groups;

    @Column
    private Integer protocol;

    @Column(nullable = false)
    private String name;

    @Column(name = "create_date")
    private String createDate;

    @Column(name = "trans_type")
    private Integer transType;

    @Column(name = "data_type")
    private Integer dataType;

    @Column(name = "type_attrs", columnDefinition = "json")
    private Map<String, Object> typeAttrs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @Column(name = "sensor_type", nullable = false)
    private String sensorType;

    @Column(name = "group_id")
    private Long groupId;

    @Column
    private String coordinate;

    @Column(columnDefinition = "json")
    private Map<String, Object> value;

    @Column(name = "record_time")
    private String recordTime;

    // 传感器特有字段
    @Column
    private String unit;

    // 执行器特有字段
    @Column(name = "oper_type")
    private Integer operType;

    @Column(name = "oper_type_attrs", columnDefinition = "json")
    private Map<String, Object> operTypeAttrs;

    // 摄像头特有字段
    @Column(name = "http_ip")
    private String httpIp;

    @Column(name = "http_port")
    private Integer httpPort;

    @Column(name = "user_name")
    private String userName;

    @Column
    private String password;

    @Column(name = "video_stream_url")
    private String videoStreamUrl;

    @Column(name = "video_stream_protocol")
    private String videoStreamProtocol;

    @Column(name = "video_stream_port")
    private String videoStreamPort;

    @Column(name = "ctrl_url")
    private String ctrlUrl;

}