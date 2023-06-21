package com.example.uhavrend;

import com.example.uhavrend.entity.Role;
import com.example.uhavrend.entity.User;
import com.example.uhavrend.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.util.ArrayList;

@SpringBootApplication
public class PharmacySystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(PharmacySystemApplication.class, args);
    }
}
