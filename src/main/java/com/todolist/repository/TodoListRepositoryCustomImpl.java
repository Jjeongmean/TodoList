package com.todolist.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.todolist.constant.Status;
import com.todolist.dto.TodoSearchDto;
import com.todolist.entity.QTodoList;
import com.todolist.entity.TodoList;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

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

    //상태를 전체로 했을때 null이 들어있으므로 처리를 한번해준다
    private BooleanExpression searchStatusEq(Status searchStatus) {
        return searchStatus == null ? null : QTodoList.todoList.status.eq(Status.valueOf(String.valueOf(searchStatus)));
    }


    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        if (StringUtils.equals("title", searchBy)) { //제목으로 검색시
            return QTodoList.todoList.title.like("%" + searchQuery + "%");
        } else if (StringUtils.equals("content", searchBy)) {
            return QTodoList.todoList.content.like("%" + searchQuery + "%"); //내용으로 검색시
        }
        return null;
    }

    @Override
    public Page<TodoList> getTodoListPage(TodoSearchDto todoSearchDto, Pageable pageable) {
        /*
         * select * from
         * where title like %검색어%
         * order by board_id desc;
         */

        List<TodoList> content = queryFactory
                .selectFrom(QTodoList.todoList)
                .where(QTodoList.todoList.title.like("%" + todoSearchDto.getSearchQuery() + "%"))
                .orderBy(QTodoList.todoList.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.select(Wildcard.count).from(QTodoList.todoList)
                .where(QTodoList.todoList.title.like("%" + todoSearchDto.getSearchQuery() + "%"))
                .fetchOne();

        return new PageImpl<>(content,pageable,total);
    }
}
