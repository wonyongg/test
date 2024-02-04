package com.example.viewtest.Entity;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class MemberCompositeKey implements Serializable {
    private Long memberId;
    private String name;
}
