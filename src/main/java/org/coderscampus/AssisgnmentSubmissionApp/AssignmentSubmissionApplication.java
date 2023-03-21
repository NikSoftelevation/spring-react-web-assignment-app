package org.coderscampus.AssisgnmentSubmissionApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
