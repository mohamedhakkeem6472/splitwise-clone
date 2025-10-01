package com.example.splitwise.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class GroupRequest {
    private String name;
    private List<Long> memberIds;
}
