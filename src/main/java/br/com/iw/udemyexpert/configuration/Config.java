package br.com.iw.udemyexpert.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Config {

	@Bean(name="AplicationName")
	public String ApplicationName() {
		return "Aplicação Udemy Spring Expert";
	}
	
	
	@Bean(name="AplicationVersion")
	public String ApplicationVersion() {
		return "Version 1.0";
	}
	
	
	
	
}
