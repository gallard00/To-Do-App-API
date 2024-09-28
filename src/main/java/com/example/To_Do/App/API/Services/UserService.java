package com.example.To_Do.App.API.Services;

import com.example.To_Do.App.API.DTOs.Request.TaskRequest;
import com.example.To_Do.App.API.DTOs.Request.UserRequest;
import com.example.To_Do.App.API.DTOs.Response.UserResponse;
import com.example.To_Do.App.API.Mappers.UserMapper;
import com.example.To_Do.App.API.Models.Task;
import com.example.To_Do.App.API.Models.User;
import com.example.To_Do.App.API.Repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private TaskService taskService;

//Crear usuario
    public User createUser(UserRequest userRequest) throws Exception {
        try{
                return saveUser(mapToUser(userRequest));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

//Buscar usuario por id
    public UserResponse getUserById(Long id) throws Exception{
            User user = findUserById(id);
            return mapToUserResponse(user);
    }

//Trae una lista de usuarios
    public List<UserResponse> getAllUser()throws Exception{
        return iUserRepository.findAll().stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    //Actualiza un usuario
    public UserResponse updateUser (Long id, UserRequest userRequest) throws Exception{
        try {
            User existingUser = findUserById(id);
            existingUser.setUserName(userRequest.getUserName());
            existingUser.setPassword(existingUser.getPassword());
            updateUserTask(existingUser, userRequest.getTaskRequests());
            User updateUser = saveUser(existingUser);
            return mapToUserResponse(updateUser);
        }catch (Exception e){
            throw new Exception("Error al actualizar el user: " + e.getMessage(), e);
        }
    }

    public boolean deleteUser(Long id) throws Exception {
        try{
            User user = findUserById(id);
            iUserRepository.delete(user);
            return true;
        } catch(Exception exception){
            throw new Exception("Error al eliminar el user: " + exception.getMessage());
        }
    }

    //Metodo para actualizar Task de Users
    private void updateUserTask(User user, List<TaskRequest> taskRequests) throws Exception {
        Set<Long> newTaskIds = taskRequests.stream()
                .map(TaskRequest::getId)
                .collect(Collectors.toSet());
        //Elimina los Task que no estan en el User
        user.getTasks().removeIf(task -> !newTaskIds.contains(task.getId()));
        //Agrega las nuevas Task del User Que no estan en la lista
        for (TaskRequest taskRequest : taskRequests){
            if (user.getTasks().stream().noneMatch(task -> task.getId().equals(taskRequest.getId()))){
                user.getTasks().add(getTask(taskRequest.getId())); //Agregar nuevos Task
            }
        }
    }

    //Metodo que utiliza a TaskService para obtener a Task por id
    private Task getTask(Long id) throws Exception {
        return taskService.findTaskById(id);
    }

    //Metodo para guardar lo nuevo o actualizado de User
    private User saveUser (User user) {
        return iUserRepository.save(user);
    }

    //Metodo que usa userMapper para pasar de UserRequest a User
    private User mapToUser(UserRequest userRequest) {
        return userMapper.toUser(userRequest);
    }

    //Metodo que utiliza userMapper para pasar de Model a Response
    private UserResponse mapToUserResponse(User user) {
        return userMapper.toUserResponse(user);
    }

    //Metodo que busca User por id
    public User findUserById(Long userId) throws Exception{
        return iUserRepository.findById(userId)
                .orElseThrow(() -> new Exception("Error al encontrar el ID: " + userId));
    }
}
