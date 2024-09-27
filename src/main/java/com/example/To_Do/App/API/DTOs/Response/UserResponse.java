package com.example.To_Do.App.API.DTOs.Response;

import lombok.Data;

import java.util.List;

@Data
public class UserResponse {
    private Long id;
    private String userName;
    private String password;
    private TaskListResponse tasks;
}
