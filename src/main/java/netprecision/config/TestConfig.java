package netprecision.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import lombok.RequiredArgsConstructor;
import netprecision.service.DBService;

@Configuration
@Profile("test")
@RequiredArgsConstructor
public class TestConfig {

	private final DBService dbService;

	@Bean
	public void instanceDatabase() {
		this.dbService.instanceDatabase();
	}
}
