package com.todolist.service;

import com.todolist.dto.TodoFormDto;
import com.todolist.dto.TodoSearchDto;
import com.todolist.entity.Member;
import com.todolist.entity.TodoList;
import com.todolist.repository.MemberRepository;
import com.todolist.repository.TodoListRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoService {
    private final TodoListRepository todoListRepository;
    private final MemberRepository memberRepository;

    //등록하기
    public Long saveTodoList(TodoFormDto todoFormDto, String email) throws Exception {
        // TodoListFormDto를 TodoList 엔티티로 변환
        TodoList todoList  = todoFormDto.createTodoList();
        Member member = memberRepository.findByEmail(email);
        todoList.setMember(member);

        // todoListRepository를 사용하여 엔티티 저장
        todoListRepository.save(todoList);
        return todoList.getId();
    }

    //불러오기
    @Transactional(readOnly = true)
    public TodoFormDto getTodoListDtl(Long todoListId) {
        TodoList todoList = todoListRepository.findById(todoListId).orElseThrow(EntityNotFoundException::new);
        TodoFormDto todoFormDto = TodoFormDto.of(todoList);

        return todoFormDto;
    }

    //글 수정하기
    public Long updateTodoList(TodoFormDto todoFormDto) throws Exception {
        //엔티티 수정
        TodoList todoList = todoListRepository.findById(todoFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);

        todoList.updateTodoList(todoFormDto);

        return todoList.getId();
    }

    //가져오기
    @Transactional(readOnly = true)
    public Page<TodoList> getListPage(TodoSearchDto todoSearchDto, Pageable pageable){
        Page<TodoList> todoListPage = todoListRepository.getTodoListPage(todoSearchDto, pageable);
        return todoListPage;
    }

    //수정
    public Long updateList(TodoFormDto todoFormDto) {
        TodoList todoList = todoListRepository.findById(todoFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        todoList.updateTodoList(todoFormDto);

        return todoList.getId();
    }

    //글 삭제
    public void deleteList(Long todoListId) {
        TodoList todoList = todoListRepository.findById(todoListId)
                .orElseThrow(EntityNotFoundException::new);

        todoListRepository.delete(todoList);
    }
}
