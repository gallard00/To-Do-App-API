package com.example.To_Do.App.API.Controllers;

import com.example.To_Do.App.API.DTOs.Request.UserRequest;
import com.example.To_Do.App.API.DTOs.Response.UserResponse;
import com.example.To_Do.App.API.Models.User;
import com.example.To_Do.App.API.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/to-do-app/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) throws Exception{
        userService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/getOne/{id}")
    @ResponseBody
    public ResponseEntity<?> getUserById(@PathVariable Long id) throws Exception{
        UserResponse userResponse = userService.getUserById(id);
        if(userResponse != null){
            return ResponseEntity.status(HttpStatus.OK).body(userResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id not found");
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllInscriptionPeriod() throws Exception{
        List<UserResponse> userResponseList = userService.getAllUser();
        return ResponseEntity.status(HttpStatus.OK).body(userResponseList);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest)
            throws Exception{
        UserResponse updateUserResponse = userService.updateUser(id, userRequest);
        if(updateUserResponse != null){
            return ResponseEntity.status(HttpStatus.OK).body(updateUserResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) throws Exception{
        boolean deleted = userService.deleteUser(id);
        if(deleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id not found");
        }
    }
}
