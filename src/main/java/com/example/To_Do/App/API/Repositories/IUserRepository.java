package com.example.To_Do.App.API.Repositories;

import com.example.To_Do.App.API.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {
}
