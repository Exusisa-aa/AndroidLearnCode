package com.film.backend.config;

import com.film.backend.service.IMovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private IMovieService movieService;

    @Override
    public void run(String... args) throws Exception {
        log.info("Checking database data...");
        long count = movieService.count();
        log.info("Current movie count: {}", count);
        
        if (count == 0) {
            log.info("Database seems empty. Please run 'schema.sql' to initialize data.");
        } else {
            log.info("Database data check passed.");
        }
    }
}
