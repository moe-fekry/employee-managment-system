package org.example.notify;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class PushNotifier implements Notifier {
    @Override
    public void send(String message) {
        System.out.println("[PushNotifier] " + message);
    }
}
