package org.example.notify;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificationManager {

    private final List<Notifier> notifiers;

    public NotificationManager(List<Notifier> notifiers) {
        this.notifiers = notifiers;
    }

    @PostConstruct
    public void init() {
        System.out.println("[NotificationManager] @PostConstruct - "
                + notifiers.size() + " notifiers wired: " + notifiers);
    }

    @PreDestroy
    public void destroy() {
        System.out.println("[NotificationManager] @PreDestroy - shutting down");
    }

    public void notifyAll(String message) {
        for (Notifier notifier : notifiers) {
            notifier.send(message);
        }
    }
}