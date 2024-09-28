package com.example.To_Do.App.API.DTOs.Request;

import lombok.Data;

@Data
public class TaskRequest {
    private Long id;
    private String description;
    private boolean complete;
    private UserRequest userRequest;
}
