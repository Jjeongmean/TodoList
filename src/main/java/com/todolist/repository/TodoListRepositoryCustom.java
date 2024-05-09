package com.todolist.repository;

import com.todolist.dto.TodoSearchDto;
import com.todolist.entity.TodoList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TodoListRepositoryCustom {
    Page<TodoList> getTodoListPage(TodoSearchDto todoSearchDto, Pageable pageable);

}
