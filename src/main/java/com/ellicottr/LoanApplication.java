package com.ellicottr;

import com.ellicottr.controllers.LoanController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@ComponentScan(basePackageClasses = LoanController.class)
@EntityScan({"com.ellicottr.beans","com.ellicott.service"})
@EnableJpaRepositories({"com.ellicottr.repository"})
public class LoanApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoanApplication.class, args);
    }
}
