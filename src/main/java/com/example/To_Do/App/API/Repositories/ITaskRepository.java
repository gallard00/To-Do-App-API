package com.example.To_Do.App.API.Repositories;

import com.example.To_Do.App.API.Models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITaskRepository extends JpaRepository< Task, Long> {
}
