package com.example.demo.controller

import com.example.demo.dto.MemberGetRequestDTO
import com.example.demo.dto.MemberRequestDTO
import com.example.demo.dto.MemberResponseDTO
import com.example.demo.service.MemberService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/members")
class MemberController(
    private val memberService: MemberService
) {
    
    // GET: 모든 회원 조회
    @GetMapping
    fun getAllMembers(): ResponseEntity<List<MemberResponseDTO>> {
        val members = memberService.getAllMembers()
        return ResponseEntity.ok(members)
    }
    
    // GET: 특정 회원 조회 (캐싱 적용)
    @GetMapping("/{id}")
    fun getMemberById(
        @PathVariable id: Long,
        @RequestParam(defaultValue = "false") isDbAccess: Boolean
    ): ResponseEntity<MemberResponseDTO> {
        val request = MemberGetRequestDTO(id = id, isDbAccess = isDbAccess)
        val member = memberService.getMemberById(request)
        return if (member != null) {
            ResponseEntity.ok(member)
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    // POST: 회원 생성
    @PostMapping
    fun createMember(@RequestBody requestDTO: MemberRequestDTO): ResponseEntity<MemberResponseDTO> {
        val createdMember = memberService.createMember(requestDTO)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMember)
    }
    
    // PATCH: 회원 수정
    @PatchMapping("/{id}")
    fun updateMember(
        @PathVariable id: Long,
        @RequestBody requestDTO: MemberRequestDTO
    ): ResponseEntity<MemberResponseDTO> {
        val updatedMember = memberService.updateMember(id, requestDTO)
        return if (updatedMember != null) {
            ResponseEntity.ok(updatedMember)
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    // DELETE: 회원 삭제
    @DeleteMapping("/{id}")
    fun deleteMember(@PathVariable id: Long): ResponseEntity<Unit> {
        return if (memberService.deleteMember(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
