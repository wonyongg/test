package com.example.viewtest.repository;

import com.example.viewtest.Entity.Member;
import com.example.viewtest.Entity.MemberCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, MemberCompositeKey> {

    Optional<Member> findByMemberCompositeKey_MemberId(Long id);
}
