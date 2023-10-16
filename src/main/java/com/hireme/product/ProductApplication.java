package com.hireme.product;

import com.hireme.product.configuration.SpringdocConfiguration;
import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//for swagger
@Import(SpringdocConfiguration.class)
//
@ComponentScan(basePackages = "com.hireme.product")
@EnableJpaRepositories
@EnableScheduling
public class ProductApplication {
	@Value("${stripe.secret-key}")
	String secretKey;

	@PostConstruct
	public void setup(){
		Stripe.apiKey = secretKey;
	}

	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}

}



