package it.uniroma3.siw.hz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SiwMovieVendettaHzApplication {

    public static void main(String[] args) {
        SpringApplication.run(SiwMovieVendettaHzApplication.class, args);
    }

}
