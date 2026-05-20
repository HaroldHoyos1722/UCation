package co.com.polijic.ucation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@ComponentScan(basePackages = UcationApplication.MAIN_POLIJIC)
@EnableJpaRepositories(basePackages = UcationApplication.MAIN_POLIJIC + ".postgresql.repository")
@EntityScan(basePackages = UcationApplication.MAIN_POLIJIC + ".postgresql.entities")
@EnableTransactionManagement
@SpringBootApplication
public class UcationApplication {

    public static final String MAIN_POLIJIC = "co.com.polijic.ucation";

    public static void main(String[] args) {
		loadEnvIfPresent();
		SpringApplication.run(UcationApplication.class, args);
	}

	private static void loadEnvIfPresent() {
		List<String> candidates = List.of(
				"../../.env"
		);

		for (String candidate : candidates) {
			Path p = Paths.get(candidate).toAbsolutePath().normalize();
			if (Files.exists(p)) {
				try {
					Files.lines(p)
							.map(String::trim)
							.filter(line -> !line.isEmpty() && !line.startsWith("#") && line.contains("="))
							.forEach(line -> {
								int idx = line.indexOf('=');
								String key = line.substring(0, idx).trim();
								String value = line.substring(idx + 1).trim();
								if (System.getenv(key) == null && System.getProperty(key) == null) {
									System.setProperty(key, value);
								}
							});
					return;
				} catch (IOException ignored) {
				}
			}
		}
	}

}
