package io.younis.transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
public class TransactionalApplication {

    Logger log = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {
        SpringApplication.run(TransactionalApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            try {
                User user = userService.addSuccess("john@doe.com", "Jakarta");
                log.info("[+] saved user: {}", user.getId());
                try {
                    userService.addFail("john@doe.com", "Singapore");
                } catch (Exception e) {
                    log.error("[+] failed to save user with error: {}", e.getMessage());
                }
                userService.all().forEach(u -> log.info("[+] user: {}", u.getId()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

}
