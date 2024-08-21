package gr.uoa.di.ted.userdemo;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@Slf4j
class EncryptPassword {

    @Bean
    CommandLineRunner initDatabase(BCryptPasswordEncoder bCryptPasswordEncoder) {
        return args -> {
            log.info("Encrypted bilbo " + bCryptPasswordEncoder.encode("bilbo"));
            log.info("Encrypted ihamod " + bCryptPasswordEncoder.encode("ihamod"));
        };
    }
}
