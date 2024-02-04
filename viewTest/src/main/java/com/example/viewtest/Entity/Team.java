package com.example.viewtest.Entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {

    @Id
    private Long teamId;

    @Column
    private String name;

    @Column
    private String city;

    @OneToMany(mappedBy = "team")
    List<Member> memberList = new ArrayList<>();

    private Team(Long teamId, String name, String city) {
        this.teamId = teamId;
        this.name = name;
        this.city = city;
    }

    public static Team createTeamOf(Long teamId, String name, String city) {
        return new Team(teamId, name, city);
    }

    public void addMember(List<Member> memberList) {
        this.memberList = memberList;
    }
}
