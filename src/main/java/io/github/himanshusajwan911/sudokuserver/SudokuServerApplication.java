package io.github.himanshusajwan911.sudokuserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class SudokuServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SudokuServerApplication.class, args);
	}

}
