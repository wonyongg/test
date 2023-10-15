package apiserver;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Player {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String name;

    private int age;

    private String team;

    private int overall;

    private Player(String name, int age, String team, int overall) {
        this.name = name;
        this.age = age;
        this.team = team;
        this.overall = overall;
    }

    public static Player addPlayer(String name, int age, String team, int overall) {

        return new Player(name, age, team, overall);
    }
}
