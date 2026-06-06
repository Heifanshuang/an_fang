package com.example.iotplatform.dto;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private T resultObj;
    private Integer status;
    private Integer statusCode;
    private String msg;
    private Object errorObj;
}