package com.todolist.controller;

import com.todolist.dto.MemberFormDto;
import com.todolist.entity.Member;
import com.todolist.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;

    //로그인 화면 창 띄우기
    @GetMapping(value = "/members/login")
    public String loginMember() {
        return "member/memberLoginForm";
    }

    //회원가입 창 띄위기
    @GetMapping(value = "/members/new")
    public String memberForm(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/memberJoinForm";
    }

    //회원가입 처리
    @PostMapping(value = "/members/new")
    public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult,
                             Model model) {
        //유효성 에러 시에 가입페이지로 이동
        if (bindingResult.hasErrors()) return "member/memberJoinForm";

        //유효성검사 통과했다면 가입 진행한다
        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e) {
            //이미 가입했다면
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberJoinForm";
        }
        return "redirect:/"; //가입 완료 후 메인페이지로 이동
    }

    //로그인 실패
    @GetMapping(value = "/members/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인하세요.");
        return "member/memberLoginForm";
    }
}
