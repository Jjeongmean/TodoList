package com.todolist.repository;

import com.todolist.dto.TodoSearchDto;
import com.todolist.entity.Member;
import com.todolist.entity.TodoList;
import com.todolist.service.TodoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface TodoListRepository extends JpaRepository<TodoList, Long>,
        QuerydslPredicateExecutor<TodoList>, TodoListRepositoryCustom {


}
