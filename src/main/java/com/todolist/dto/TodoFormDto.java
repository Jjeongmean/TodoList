package com.todolist.dto;

import com.todolist.entity.Member;
import com.todolist.entity.TodoList;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class TodoFormDto {

    private Long id;
    private String title;
    private String content;

    private String createDate;
    private LocalDateTime deadline;
    private boolean completed;
    private String status;

    private Member member;

    //modelMapper 사용
    private static ModelMapper modelMapper = new ModelMapper();

    public TodoList createTodoList() {
        return modelMapper.map(this, TodoList.class);
    }

    public static TodoFormDto of (TodoList todoList) {

        return modelMapper.map(todoList, TodoFormDto.class);
    }
}
