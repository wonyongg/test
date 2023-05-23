package Test.MultiDb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MultiDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiDbApplication.class, args);
	}

}
