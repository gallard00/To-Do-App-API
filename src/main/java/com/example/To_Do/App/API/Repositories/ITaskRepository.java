package com.example.To_Do.App.API.Repositories;

import com.example.To_Do.App.API.Models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITaskRepository extends JpaRepository< Task, Long> {
}
