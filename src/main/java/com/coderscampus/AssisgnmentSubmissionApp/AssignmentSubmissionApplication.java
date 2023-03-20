package com.coderscampus.AssisgnmentSubmissionApp;

import com.coderscampus.AssisgnmentSubmissionApp.model.Authority;
import com.coderscampus.AssisgnmentSubmissionApp.model.User;
import com.coderscampus.AssisgnmentSubmissionApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

@SpringBootApplication
public class AssignmentSubmissionApplication{ /*implements CommandLineRunner {
   @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;*/

        public static void main(String[] args) {
            SpringApplication.run(AssignmentSubmissionApplication.class, args);
        }
/*
    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setUsername("NikhilAdmin");
        user.setPassword(bCryptPasswordEncoder.encode("Nikhil@07@18"));
        user.setCohortStartDate(LocalDate.of(2023, 3, 15));

        userRepository.save(user);*/
}
