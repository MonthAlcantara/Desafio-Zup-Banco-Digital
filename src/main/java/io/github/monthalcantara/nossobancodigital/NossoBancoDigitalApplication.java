package io.github.monthalcantara.nossobancodigital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class NossoBancoDigitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(NossoBancoDigitalApplication.class, args);
	}

}
