package com.example.viewtest.Entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    @Column
    private String name;

    @Column
    private String city;

    @OneToMany(mappedBy = "team")
    List<Member> memberList = new ArrayList<>();

    private Team(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public static Team createTeamOf(String name, String city) {
        return new Team(name, city);
    }

    public void addMember(List<Member> memberList) {
        this.memberList = memberList;
    }
}
