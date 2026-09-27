package org.example.notify;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificationManager {

    private final List<Notifier> notifiers;

    @Value("${company.name}")
    private String companyName;

    @Value("${notification.retry-count}")
    private int retryCount;

    public NotificationManager(List<Notifier> notifiers) {
        this.notifiers = notifiers;
    }

    @PostConstruct
    public void init() {
        System.out.println("[NotificationManager] @PostConstruct - "
                + notifiers.size() + " notifiers wired. company=" + companyName
                + ", retryCount=" + retryCount);
    }

    @PreDestroy
    public void destroy() {
        System.out.println("[NotificationManager] @PreDestroy - shutting down");
    }

    public void notifyAll(String message) {
        String tagged = "[" + companyName + "] " + message;
        for (int attempt = 1; attempt <= retryCount; attempt++) {
            for (Notifier notifier : notifiers) {
                notifier.send(tagged + " (attempt " + attempt + "/" + retryCount + ")");
            }
        }
    }
}