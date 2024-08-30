package com.fipejava;

import com.fipejava.main.Main;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FipejavaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FipejavaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main();
		main.showMenu();
	}
}
