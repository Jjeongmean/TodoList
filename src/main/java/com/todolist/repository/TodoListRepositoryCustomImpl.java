package com.todolist.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.todolist.dto.TodoSearchDto;
import com.todolist.entity.QTodoList;
import com.todolist.entity.TodoList;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;

public class TodoListRepositoryCustomImpl implements TodoListRepositoryCustom {
    private JPAQueryFactory queryFactory;

    public TodoListRepositoryCustomImpl (EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    // 현재 날짜로부터 이전날짜를 구해주는 메소드
    private BooleanExpression regDtsAfter(String searchDateType) {
        LocalDateTime dateTime = LocalDateTime.now(); //현재 날짜와 시간

        if (StringUtils.equals("all", searchDateType) || searchDateType == null)
            return null;
        else if (StringUtils.equals("1d", searchDateType))
            dateTime = dateTime.minusDays(1); //1일전
        else if (StringUtils.equals("1w", searchDateType))
            dateTime = dateTime.minusWeeks(1); //1주전
        else if (StringUtils.equals("1m", searchDateType))
            dateTime = dateTime.minusMonths(1); //1달전
        else if (StringUtils.equals("6m", searchDateType))
            dateTime = dateTime.minusMonths(6); //6달전

        return QTodoList.todoList.regTime.after(dateTime);
    }


    @Override
    public Page<TodoList> getTodoListPage(TodoSearchDto todoSearchDto, Pageable pageable) {
        return null;
    }
}
