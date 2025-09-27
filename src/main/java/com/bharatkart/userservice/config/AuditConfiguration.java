package com.bharatkart.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
public class AuditConfiguration {

    @Bean
    public AuditorAware auditProvider(){
        return ()->Optional.of("system");
    }
}
