package com.example.viewtest.Entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
//@EqualsAndHashCode(of = {"playerName", "teamName"})
@Embeddable
public class ViewCompositeKey implements Serializable {
    private String playerName;
    private String teamName;
}
