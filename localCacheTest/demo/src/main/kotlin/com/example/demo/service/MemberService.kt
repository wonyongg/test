package com.example.demo.service

import com.example.demo.dto.MemberRequestDTO
import com.example.demo.dto.MemberResponseDTO
import com.example.demo.entity.Member
import com.example.demo.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberService(
    private val memberRepository: MemberRepository
) {
    
    // 모든 회원 조회
    @Transactional(readOnly = true)
    fun getAllMembers(): List<MemberResponseDTO> {
        return memberRepository.findAll().map { it.toResponseDTO() }
    }
    
    // 회원 ID로 조회
    @Transactional(readOnly = true)
    fun getMemberById(id: Long): MemberResponseDTO? {
        return memberRepository.findById(id).map { it.toResponseDTO() }.orElse(null)
    }
    
    // 회원 생성
    fun createMember(requestDTO: MemberRequestDTO): MemberResponseDTO {
        val member = Member(
            name = requestDTO.name,
            age = requestDTO.age
        )
        val savedMember = memberRepository.save(member)
        return savedMember.toResponseDTO()
    }
    
    // 회원 수정
    fun updateMember(id: Long, requestDTO: MemberRequestDTO): MemberResponseDTO? {
        val member = memberRepository.findById(id).orElse(null) ?: return null
        
        val updatedMember = member.copy(
            name = requestDTO.name,
            age = requestDTO.age
        )
        val savedMember = memberRepository.save(updatedMember)
        return savedMember.toResponseDTO()
    }
    
    // 회원 삭제
    fun deleteMember(id: Long): Boolean {
        return if (memberRepository.existsById(id)) {
            memberRepository.deleteById(id)
            true
        } else {
            false
        }
    }
    
    // 엔티티를 ResponseDTO로 변환
    private fun Member.toResponseDTO() = MemberResponseDTO(
        id = this.id,
        name = this.name,
        age = this.age
    )
}
