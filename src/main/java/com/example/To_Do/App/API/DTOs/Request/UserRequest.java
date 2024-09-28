package com.example.To_Do.App.API.DTOs.Request;

import lombok.Data;

import java.util.List;
@Data
public class UserRequest {
    private Long id;
    private String userName;
    private String password;
    private List<TaskRequest> taskRequests;
}
