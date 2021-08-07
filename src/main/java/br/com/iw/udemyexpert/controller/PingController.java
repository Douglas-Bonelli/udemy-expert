package br.com.iw.udemyexpert.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ping")
@CrossOrigin( origins = "*")
public class PingController {
	
	
	//@Autowired
	//@Qualifier("AplicationName")
	//private String application;
	
	//@Autowired
	//@Qualifier("AplicationVersion")
	//private String version;
	
	//@Autowired
	//@Qualifier("AplicationAuthor")
	//private String author;
	
	@Value("${application.status}")
	String status;
	
	
	@GetMapping
	public String get() {
		//return application+" "+version;
		return status;
		//return application+" "+version+" "+author;
		//return application+" "+version+" "+status;
	}

}
