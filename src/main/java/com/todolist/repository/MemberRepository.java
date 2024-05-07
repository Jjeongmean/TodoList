package com.todolist.repository;

import com.todolist.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email); //회원가입시 중복된 회원이 있는지 이메일로 확인
}
