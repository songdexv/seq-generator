package com.songdexv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.songdexv.seqgenerator"})
public class SeqGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeqGeneratorApplication.class, args);
	}
}
