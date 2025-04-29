package br.com.wefit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "br.com.wefit")
@EntityScan(basePackages = "br.com.wefit.infrastructure.persistence.jpa")
public class WefitApplication {

	public static void main(String[] args) {
		SpringApplication.run(WefitApplication.class, args);
	}

}
