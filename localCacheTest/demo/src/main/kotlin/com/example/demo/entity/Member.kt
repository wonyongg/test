package com.example.demo.entity

import jakarta.persistence.*

@Entity
@Table(name = "member")
data class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    
    @Column(nullable = false)
    val name: String,
    
    @Column(nullable = false)
    val age: Int
)
