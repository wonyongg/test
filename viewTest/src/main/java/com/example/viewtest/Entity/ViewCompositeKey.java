package com.example.viewtest.Entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class ViewCompositeKey implements Serializable {
    private String playerName;
    private String teamName;
}
