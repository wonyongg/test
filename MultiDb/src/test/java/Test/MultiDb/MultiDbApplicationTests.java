package Test.MultiDb;


import Test.MultiDb.db1Entity.DB1;
import Test.MultiDb.db1Repository.Db1Repository;
import Test.MultiDb.db2Entity.DB2;
import Test.MultiDb.db2Repository.Db2Repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MultiDbApplicationTests {
	@Autowired
	private Db1Repository db1Repository;
	@Autowired
	private Db2Repository db2Repository;

	@Test
	void init() {
		testDB1();
		System.out.println("----------------------------------");
		testDB2();
	}

	void testDB1() {
		List<DB1> all = db1Repository.findAll();
		for (DB1 db1 : all) {
			System.out.println(db1.getId());
			System.out.println(db1.getDb1Col1());
		}
	}

	void testDB2() {
		List<DB2> all = db2Repository.findAll();
		for (DB2 db2 : all) {
			System.out.println(db2.getId());
			System.out.println(db2.getDb2Col1());
		}
	}
}
