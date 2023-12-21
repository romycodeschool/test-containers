package ro.mycode.institutie2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ro.mycode.institutie2.Splital.repository.SpitalRepo;
import ro.mycode.institutie2.Splital.view.View;

@SpringBootApplication
@Slf4j
public class Institutie2Application {

	public static void main(String[] args) {
		SpringApplication.run(Institutie2Application.class, args);
	}


}
