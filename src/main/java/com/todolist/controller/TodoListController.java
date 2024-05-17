package com.todolist.controller;

import com.todolist.dto.TodoFormDto;
import com.todolist.dto.TodoSearchDto;
import com.todolist.entity.TodoList;
import com.todolist.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class TodoListController {
    private final TodoService todoService;

    //등록 페이지
    @GetMapping(value = "/write")
    public String todoForm(Model model) {
        model.addAttribute("todoFormDto", new TodoFormDto());
        return "todolist/write";
    }

    //등록
    @PostMapping("/write")
    public String todoNew(@Valid TodoFormDto todoFormDto, BindingResult bindingResult, Model model, Principal principal) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("todoSearchDto", new TodoSearchDto());
            return "todolist/list";
        }

        try{
            todoService.saveTodoList(todoFormDto, principal.getName());
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage",
                    "등록 중 에러가 발생했습니다.");
            model.addAttribute("todoSearchDto", new TodoSearchDto());
            return "todolist/list";
        }
        return "redirect:/";
    }

    //게시물 리스트
    @GetMapping(value = {"/list", "/list/{page}"})
    public String todolistList(Model model, TodoSearchDto todoSearchDto,
                           @PathVariable(value = "page")Optional<Integer> page) {
        //페이징 객체 생성
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);

        Page<TodoList> todoLists = todoService.getListPage(todoSearchDto, pageable);

        //모델에 페이징 결과 추가
        model.addAttribute("todoLists", todoLists);
        model.addAttribute("todoSearchDto", todoSearchDto);
        model.addAttribute("maxPage", 5);

        return "todolist/list";
    }


    //게시물 상세 페이지
    @GetMapping("/detail/{todoListId}")
    public String todoDtl(Model model, @PathVariable("todoListId") Long todoListId){
        TodoFormDto todoList = todoService.getTodoListDtl(todoListId);
        model.addAttribute("todoList", todoList);
        return "todolist/detail";
    }

    // 수정 페이지 띄우기
    @GetMapping("/rewrite/{todoListId}")
    public String todoRewrite(Model model,
                              @PathVariable("todoListId") Long todoListId) {

        try {
            TodoFormDto todoFormDto = todoService.getTodoListDtl(todoListId);
            model.addAttribute("todoFormDto", todoFormDto);

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("todoFormDto", new TodoFormDto());
            return "todolist/rewrite";
        }

        return "todolist/rewrite";
    }

    //수정하기
    @PostMapping("/rewrite/{todoListId}")
    public String todoUpdate(@Valid TodoFormDto todoFormDto, Model model, BindingResult bindingResult,
                             @PathVariable("todoListId") Long todoListid) {
        if (bindingResult.hasErrors()) return "todolist/detail"; //유효성체크 걸리면

        TodoFormDto getTodoFormDto = todoService.getTodoListDtl(todoListid);

        try {
            todoService.updateList(todoFormDto);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage",
                    "수정 중 에러가 발생했습니다.");
            model.addAttribute("todoFormDto", getTodoFormDto);
            return "todolist/rewrite";
        }
        return "redirect:/";
    }


    //삭제하기
    @DeleteMapping("/todolist/{todoListId}/delete")
    public @ResponseBody ResponseEntity deleteTodo(@PathVariable("todoListId") Long todoListId,
                                                   Principal principal) {
        todoService.deleteList(todoListId);
        return new ResponseEntity<Long>(todoListId, HttpStatus.OK);

    }
}
