package co.com.polijic.ucation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan(basePackages = UcationApplication.MAIN_POLIJIC)
@EnableJpaRepositories(basePackages = UcationApplication.MAIN_POLIJIC + ".postgresql.repository")
@EntityScan(basePackages = UcationApplication.MAIN_POLIJIC + ".postgresql.entities")
@EnableTransactionManagement
@SpringBootApplication
public class UcationApplication {

    public static final String MAIN_POLIJIC = "co.com.polijic.ucation";

    public static void main(String[] args) {
		SpringApplication.run(UcationApplication.class, args);
	}

}
