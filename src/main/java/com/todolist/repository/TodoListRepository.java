package com.todolist.repository;

import com.todolist.dto.TodoSearchDto;
import com.todolist.entity.Member;
import com.todolist.entity.TodoList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoListRepository extends JpaRepository<TodoList, Long> {
    List<TodoList> findByMember(Member member);


    Page<TodoList> getTodoListPage(TodoSearchDto todoSearchDto, Pageable pageable);
}
