package netprecision.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import lombok.RequiredArgsConstructor;
import netprecision.service.DBService;

@Configuration
@Profile("dev")
@RequiredArgsConstructor
public class DevConfig {

	private final DBService dbService;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Bean
	public boolean instanceDatabase() {
		if (strategy.equals("create")) {
			this.dbService.instanceDatabase();
		}

		return false;
	}
}
