package com.todolist.dto;

import com.todolist.constant.Status;
import com.todolist.entity.Member;
import com.todolist.entity.TodoList;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class TodoFormDto {

    private Long id;

    @NotBlank(message = "제목은 필수 입력입니다")
    private String title;

    @NotBlank(message = "내용은 필수 입력입니다.")
    private String content;

    private String deadline;
    private boolean completed;
    private Status status;


    //modelMapper 사용
    private static ModelMapper modelMapper = new ModelMapper();

    public TodoList createTodoList(TodoFormDto todoFormDto) {
        TodoList todoList = new TodoList();
        todoList.setTitle(todoFormDto.getTitle());
        todoList.setContent(todoFormDto.getContent());

        LocalDateTime customLocalDateTime = LocalDateTime.parse(todoFormDto.getDeadline() + "T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        todoList.setDeadline(customLocalDateTime);
        todoList.setComplete(todoFormDto.isCompleted());
        todoList.setStatus(todoFormDto.getStatus());

        return todoList;
    }

    public static TodoFormDto of (TodoList todoList) {

        return modelMapper.map(todoList, TodoFormDto.class);
    }
}
