package com.example.To_Do.App.API.Services;

import com.example.To_Do.App.API.Mappers.TaskMapper;
import com.example.To_Do.App.API.Repositories.ITaskRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskService {
    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private ITaskRepository iTaskRepository;
}
