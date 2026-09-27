package org.example.audit;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Component
@Scope("prototype")
public class AuditLogger {

    private final String instanceId =
            UUID.randomUUID().toString().substring(0, 8);

    public AuditLogger() {
        System.out.println("[AuditLogger " + instanceId + "] constructor called");
    }

    @PostConstruct
    public void init() {
        System.out.println("[AuditLogger " + instanceId
                + "] @PostConstruct at " + LocalDateTime.now());
    }

    @PreDestroy
    public void destroy() {
        // NOTE: Spring does NOT call this for prototype beans automatically.
        System.out.println("[AuditLogger " + instanceId
                + "] @PreDestroy at " + LocalDateTime.now());
    }

    public void log(String message) {
        System.out.println("[AuditLogger " + instanceId + "] "
                + LocalDateTime.now() + " - " + message);
    }

}