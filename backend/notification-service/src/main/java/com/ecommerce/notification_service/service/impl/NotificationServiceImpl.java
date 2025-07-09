package com.ecommerce.notification_service.service.impl;
import org.springframework.stereotype.Service;

import com.ecommerce.notification_service.dto.NotificationRequest;
import com.ecommerce.notification_service.service.NotificationService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    @Override
    public void sendNotification(NotificationRequest request) {
        if (request.getType().equalsIgnoreCase("EMAIL")) {
            log.info("📧 Sending email to {} with subject '{}' and body: {}", request.getRecipient(), request.getSubject(), request.getMessage());
        } else if (request.getType().equalsIgnoreCase("SMS")) {
            log.info("📱 Sending SMS to {} with message: {}", request.getRecipient(), request.getMessage());
        } else {
            log.warn("❗ Unknown notification type: {}", request.getType());
        }
    }
}
