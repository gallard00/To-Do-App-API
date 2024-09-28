package com.example.To_Do.App.API.Services;

import com.example.To_Do.App.API.DTOs.Request.TaskRequest;
import com.example.To_Do.App.API.DTOs.Response.TaskListResponse;
import com.example.To_Do.App.API.DTOs.Response.TaskResponse;
import com.example.To_Do.App.API.Mappers.TaskMapper;
import com.example.To_Do.App.API.Models.Task;
import com.example.To_Do.App.API.Models.User;
import com.example.To_Do.App.API.Repositories.ITaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class TaskService {
    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private ITaskRepository iTaskRepository;

    @Autowired
    private UserService userService;

    //Creamos un nuevo Task
    public Task newTask (TaskRequest taskRequest, User user)throws Exception{
        try {
            Task task = mapToTaskModel(taskRequest);

            task.setComplete(taskRequest.isComplete());
            task.setDescription(taskRequest.getDescription());
            task.setUser(user);

            return saveTask(task);
        } catch (Exception e) {
            throw new Exception("Error al crear una Task: " + e.getMessage());
        }
    }

    //Obtenemos una lista de Task
    public TaskListResponse listAllTask() throws Exception{
        try {
            List<Task> taskList = iTaskRepository.findAll();
            return mapToTaskResponse(taskList);
        } catch (Exception e) {
            throw new Exception("Error al listar Task: " + e.getMessage());
        }
    }

    //Obtenemos Task por id
    public TaskResponse getTaskById(Long id)throws Exception{
        try {
            return mapToTaskResponse(findTaskById(id));
        } catch (Exception e) {
            throw new Exception("Error al encontrar la task: " + e.getMessage());
        }
    }

    //Actualizamos la Task (Tarea)
    public TaskResponse updateTask(Long id, TaskRequest taskRequest) throws Exception{
        try {
            Task task = findTaskById(id);
            task.setDescription(taskRequest.getDescription());
            task.setComplete(taskRequest.isComplete());
            return mapToTaskResponse(saveTask(task));
        } catch (Exception e) {
            throw new Exception("Error al actualizar la Task: " + e.getMessage());
        }
    }

    //Eliminar una Task
    public boolean deleteTask(Long id)throws Exception{
        try {
            if (existingTaskById(id)){
                iTaskRepository.deleteById(id);
                return true;
            } else {
                throw new Exception("No se encotro el id:" + id);
            }
        } catch (Exception e) {
            throw new Exception("Error al borrar la Task: " + e.getMessage());
        }
    }

    //Utiliza el TaskMapper para pasar de Request a Model el Task
    private Task mapToTaskModel(TaskRequest taskRequest) {return taskMapper.toTask(taskRequest);}

    //Metodo que guarda el nuevo o actualizado Task
    private Task saveTask(Task task){return iTaskRepository.save(task);}

    //Metodo que mapea una lista de Task a una lista de TaskResponse
    private TaskListResponse mapToTaskResponse(List<Task> taskList) {
        List<TaskResponse> taskResponses = taskList.stream()
                .map(taskMapper::toTaskResponse)
                .collect(Collectors.toList());
        return new TaskListResponse(taskResponses);
    }

    //Metodo que utiliza el TaskMapper para pasar de Task(Model) a TaskResponse
    private TaskResponse mapToTaskResponse(Task task){return taskMapper.toTaskResponse(task); }

    //Metodo que encuentra una Task por id
    public Task findTaskById(Long id) {
        return iTaskRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Error al enmcontrar el Id" + id));
    }

    //Metodo que verifica si exite la Task por el id
    private Boolean existingTaskById (Long id) {return iTaskRepository.existsById(id);}

}
