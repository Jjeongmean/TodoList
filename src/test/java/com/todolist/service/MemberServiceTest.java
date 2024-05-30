package com.todolist.service;

import com.todolist.dto.MemberFormDto;
import com.todolist.entity.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
//@Rollback(false)
@TestPropertySource(locations = "classpath:application-test.properties")
public class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember() {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test5@gmail.com");
        memberFormDto.setName("부석순");
        memberFormDto.setPassword("1234");

        return Member.createMember(memberFormDto, passwordEncoder);
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void saveMemberTest() {
        Member member = createMember();
        Member savedMember = memberService.saveMember(member);

        //회원가입시킨 정보와 DB에 저장된 회원가입 정보가 일치하는지 확인
        Assertions.assertEquals(member.getEmail(),savedMember.getEmail());
        Assertions.assertEquals(member.getName(),savedMember.getName());
        Assertions.assertEquals(member.getPassword(),savedMember.getPassword());
    }

    @Test
    @DisplayName("중복 회원 가입 테스트")
    public void validateDuplicateMemberTest() {
        Member member1 = createMember();
        Member member2 = createMember();

        memberService.saveMember(member1);

        Throwable e = Assertions.assertThrows(IllegalStateException.class, () -> {
            memberService.saveMember(member2);
        });
        Assertions.assertEquals("이미 가입된 회원입니다.", e.getMessage());

    }
}
