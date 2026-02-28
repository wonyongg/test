package com.example.demo.dto

data class MemberRequestDTO(
    val name: String,
    val age: Int
)

data class MemberResponseDTO(
    val id: Long,
    val name: String,
    val age: Int
)
