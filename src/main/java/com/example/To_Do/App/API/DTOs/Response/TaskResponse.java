package com.example.To_Do.App.API.DTOs.Response;

import lombok.Data;

@Data
public class TaskResponse {
    private Long id;
    private String description;
    private boolean complete;
    private UserResponse userResponse;
}
