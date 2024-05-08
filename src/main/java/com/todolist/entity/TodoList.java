package com.todolist.entity;

import com.todolist.dto.TodoFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "todolist")
@Getter
@Setter
@ToString
public class TodoList {
    @Id
    @Column(name = "todolist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //식별자

    @Column(nullable = false)
    private String title; //제목

    private String content; //내용

    private LocalDateTime createDate; //등록일

    private LocalDateTime deadline; //마감기한

    private boolean complete; //완료

    private String status; //진행상태(여부) yes or no

    //할일에 해당하는 회원
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") //FK
    private Member member;


    public void updateTodoList(TodoFormDto todoFormDto) {
        this.content = todoFormDto.getContent();
        this.title = todoFormDto.getTitle();
        this.deadline = todoFormDto.getDeadline();
    }
}
