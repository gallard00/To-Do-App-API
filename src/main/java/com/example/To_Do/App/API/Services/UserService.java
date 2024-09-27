package com.example.To_Do.App.API.Services;

import com.example.To_Do.App.API.DTOs.Request.UserRequest;
import com.example.To_Do.App.API.DTOs.Response.UserResponse;
import com.example.To_Do.App.API.Mappers.UserMapper;
import com.example.To_Do.App.API.Models.User;
import com.example.To_Do.App.API.Repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IUserRepository iUserRepository;
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
    public List<UserResponse> getAllUser(Long id)throws Exception{
        return iUserRepository.findAll().stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    //Actualiza un usuario
    public UserResponse updateUser (Long id, UserRequest userRequest) throws Exception{
        try {
            User user = findUserById(id);
            user.setUserName(userRequest.getUserName());
            user.setPassword(user.getPassword());

        }
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
    private User findUserById(Long id) throws Exception{
        return iUserRepository.findById(id)
                .orElseThrow(() -> new Exception("Error al encontrar el ID: " + id));
    }
}
