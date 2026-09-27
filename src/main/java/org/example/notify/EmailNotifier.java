package org.example.notify;


import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Primary
public class EmailNotifier implements Notifier {
    @Override
    public void send(String message) {
        System.out.println("[EmailNotifier] " + message);
    }
}
