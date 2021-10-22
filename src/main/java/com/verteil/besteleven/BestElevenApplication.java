package com.verteil.besteleven;

import com.verteil.besteleven.model.User;
import com.verteil.besteleven.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(exclude = EmbeddedMongoAutoConfiguration.class)
public class BestElevenApplication {

    @Autowired
    private PasswordEncoder passwordEncoder;
    private static final String ADMIN_USER = "admin@verteil.com";

    public static void main(String[] args) {
        SpringApplication.run(BestElevenApplication.class, args);
    }

    @Bean
    public CommandLineRunner addAdminProfile(UserRepository repository) {
        return (args) -> {
            User adminUser = repository.findByName(ADMIN_USER);
            if (null == adminUser) {
                repository.save(createAdminUser());
            }
        };
    }

    private User createAdminUser() {
        User adminUser = new User();
        adminUser.setId(ADMIN_USER);
        adminUser.setName(ADMIN_USER);
        adminUser.setAdmin(true);
        adminUser.setPassword(passwordEncoder.encode("Admin@777"));
        return adminUser;
    }
}
