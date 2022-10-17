package com.AgendaBackEnd.app;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class AgendaBackEndApplication {

	  @PostConstruct
	  public void init(){
	    // Setting Spring Boot SetTimeZone
	    TimeZone.setDefault(TimeZone.getTimeZone("America/Argentina/Buenos_Aires"));
	  }
	public static void main(String[] args) {
		SpringApplication.run(AgendaBackEndApplication.class, args);
		

	}

}
