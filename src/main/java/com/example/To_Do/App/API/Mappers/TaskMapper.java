package com.example.To_Do.App.API.Mappers;

import com.example.To_Do.App.API.DTOs.Request.TaskRequest;
import com.example.To_Do.App.API.DTOs.Response.TaskResponse;
import com.example.To_Do.App.API.DTOs.Response.UserResponse;
import com.example.To_Do.App.API.Models.Task;
import com.example.To_Do.App.API.Models.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskMapper {
    @Autowired
    private ModelMapper modelMapper;


    public Task toTask(TaskRequest taskRequest) {
        //Mapeo de Task
        Task task = modelMapper.map(taskRequest, Task.class);
        //Mapeo de User dentro de Task
        User user = modelMapper.map(taskRequest.getUserRequest(), User.class);

        task.setUser(user);
        return task;
    }

    public TaskResponse toTaskResponse (Task task) {
        //Mapeo de TaskResponse
        TaskResponse taskResponse = modelMapper.map(task, TaskResponse.class);
        taskResponse.setUserResponse(modelMapper.map(task.getUser(), UserResponse.class));
        return taskResponse;
    }
}
