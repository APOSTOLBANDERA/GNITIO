package com.example.gnitio.service;

import com.example.gnitio.entity.ToDoEntity;
import com.example.gnitio.entity.UserEntity;
import com.example.gnitio.model.ToDo;
import com.example.gnitio.repository.ToDoRepo;
import com.example.gnitio.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToDoService {
    @Autowired
    private ToDoRepo toDoRepo;
    @Autowired
    private UserRepo userRepo;

    public ToDo createToDo(ToDoEntity toDo, Long userId){
        UserEntity user = userRepo.findById(userId).get();
        toDo.setUser(user);
        return ToDo.toModel(toDoRepo.save(toDo));
    }
    public ToDo comleteToDo(Long userId){
        ToDoEntity toDo = toDoRepo.findById(userId).get();
        toDo.setCompleted(!toDo.getCompleted());
        return ToDo.toModel(toDoRepo.save(toDo));
    }

}
