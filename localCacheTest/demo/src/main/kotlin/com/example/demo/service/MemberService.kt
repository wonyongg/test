package com.example.demo.service

import com.example.demo.annotation.LocalCache
import com.example.demo.dto.GetAllMembersRequestDTO
import com.example.demo.dto.MemberGetRequestDTO
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
    
    // 모든 회원 조회 (캐싱 적용)
    @LocalCache(
        ttlMinutes = 1,
        cacheKeyFields = []
    )
    @Transactional(readOnly = true)
    fun getAllMembers(request: GetAllMembersRequestDTO): List<MemberResponseDTO> {
        println("📊 getAllMembers(isDbAccess=${request.isDbAccess}) - DB 조회")
        return memberRepository.findAll().map { it.toResponseDTO() }
    }
    
    // 회원 ID로 조회
    @Transactional(readOnly = true)
    fun getMemberById(request: MemberGetRequestDTO): MemberResponseDTO? {
        println("🔍 getMemberById(${request.id}) - DB 조회")
        return memberRepository.findById(request.id)
            .map { it.toResponseDTO() }
            .orElse(null)
    }
    
    // 회원 생성
    fun createMember(requestDTO: MemberRequestDTO): MemberResponseDTO {
        println("✅ createMember() - DB 저장")
        val member = Member(
            name = requestDTO.name,
            age = requestDTO.age
        )
        val savedMember = memberRepository.save(member)
        return savedMember.toResponseDTO()
    }
    
    // 회원 수정
    fun updateMember(id: Long, requestDTO: MemberRequestDTO): MemberResponseDTO? {
        println("📝 updateMember($id) - DB 업데이트")
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
        println("🗑️ deleteMember($id) - DB 삭제")
        return if (memberRepository.existsById(id)) {
            memberRepository.deleteById(id)
            true
        } else {
            false
        }
    }
    
    // 엔티티를 ResponseDTO로 변환
    private fun Member.toResponseDTO() = MemberResponseDTO(
        id = this.id ?: throw IllegalStateException("Member id cannot be null"),
        name = this.name,
        age = this.age
    )
}
