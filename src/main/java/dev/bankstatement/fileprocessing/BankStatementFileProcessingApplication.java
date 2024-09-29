package dev.bankstatement.fileprocessing;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankStatementFileProcessingApplication {

	public static void main(String[] args) {

		// Load .env file
		Dotenv dotenv = Dotenv.load();

		// Set system properties from the .env file
		System.setProperty("DB_URL", dotenv.get("DB_URL"));
		System.setProperty("DB_TEST_URL", dotenv.get("DB_TEST_URL"));
		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		SpringApplication.run(BankStatementFileProcessingApplication.class, args);

	}

}
