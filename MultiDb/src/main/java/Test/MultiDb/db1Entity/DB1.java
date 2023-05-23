package Test.MultiDb.db1Entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class DB1 {
    @Id
    @GeneratedValue
    private Long id;

    private String db1Col1;
    private String db1Col2;
    private String db1Col3;
    private String db1Col4;
}
