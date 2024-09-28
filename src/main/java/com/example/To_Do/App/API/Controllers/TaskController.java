package com.example.To_Do.App.API.Controllers;

import com.example.To_Do.App.API.DTOs.Request.TaskRequest;
import com.example.To_Do.App.API.DTOs.Response.TaskListResponse;
import com.example.To_Do.App.API.DTOs.Response.TaskResponse;
import com.example.To_Do.App.API.Models.User;
import com.example.To_Do.App.API.Services.TaskService;
import com.example.To_Do.App.API.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/to-do-app/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> newTask(@RequestBody TaskRequest taskRequest) throws Exception{
        User user = userService.findUserById(taskRequest.getUserRequest().getId());
        taskService.newTask(taskRequest, user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/getBy/{id}")
    @ResponseBody
    public ResponseEntity<?> getTask(@PathVariable Long id) throws Exception{
        TaskResponse taskResponse = taskService.getTaskById(id);
        if(taskResponse != null){
            return ResponseEntity.status(HttpStatus.OK).body(taskResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id not found");
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllTask() throws Exception{
        TaskListResponse taskListResponse = taskService.listAllTask();
        return ResponseEntity.status(HttpStatus.OK).body(taskListResponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody TaskRequest taskRequest)
            throws Exception {
        TaskResponse updateTaskResponse = taskService.updateTask(id, taskRequest);
        if(updateTaskResponse != null){
            return ResponseEntity.status(HttpStatus.OK).body(updateTaskResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTask (@PathVariable Long id) throws Exception {
        boolean deleted = taskService.deleteTask(id);
        if(deleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Task as Deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id not found");
        }
    }
}
