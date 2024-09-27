package com.example.To_Do.App.API.Mappers;

import com.example.To_Do.App.API.DTOs.Request.TaskRequest;
import com.example.To_Do.App.API.DTOs.Request.UserRequest;
import com.example.To_Do.App.API.DTOs.Response.TaskListResponse;
import com.example.To_Do.App.API.DTOs.Response.TaskResponse;
import com.example.To_Do.App.API.DTOs.Response.UserResponse;
import com.example.To_Do.App.API.Models.Task;
import com.example.To_Do.App.API.Models.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserMapper {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TaskMapper taskMapper;

    public User toUser (UserRequest userRequest) {
        User user = User.builder()
                .userName(userRequest.getUserName())
                .password(userRequest.getPassword())
                .build();
        for (TaskRequest taskRequest : userRequest.getTaskRequests()) {
             Task task = taskMapper.toTask(taskRequest);
             user.getTasks().add(task);
        }
        return user;
    }

    public UserResponse toUserResponse (User user) {
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);

        TaskListResponse tasks = new TaskListResponse( new ArrayList<>());

        for (Task task : user.getTasks() ) {
            TaskResponse taskResponse = taskMapper.toTaskResponse(task);
            tasks.getTasks().add(taskResponse);
        }

        userResponse.setTasks(tasks);
        return userResponse;

    }
}
