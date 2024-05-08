package com.todolist.controller;

import com.todolist.dto.TodoFormDto;
import com.todolist.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class TodoListController {
    private final TodoService todoService;

    //등록 페이지
    @GetMapping(value = "/add")
    public String todoForm(Model model) {
        model.addAttribute("todoFormDto",new TodoFormDto());
        return "todolist/add";
    }

    @PostMapping("/todolist")
    public String todoNew(@Valid TodoFormDto todoFormDto, BindingResult bindingResult, Model model, Principal principal) {

        if (bindingResult.hasErrors()) return "redirect:/";
    }
}
