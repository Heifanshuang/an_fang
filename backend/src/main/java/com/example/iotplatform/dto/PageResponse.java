package com.example.iotplatform.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageResponse<T> {
    private List<T> pageSet;
    private Integer pageCount;
    private Integer pageIndex;
    private Integer pageSize;
    private Long recordCount;
}