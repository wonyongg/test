package Test.MultiDb.db2Entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class DB2 {
    @Id
    @GeneratedValue
    private Long id;

    private String db2Col1;
    private String db2Col2;
    private String db2Col3;
    private String db2Col4;
}