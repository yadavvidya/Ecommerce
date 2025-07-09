package com.ecommerce.notification_service.service;

import com.ecommerce.notification_service.dto.NotificationRequest;

public interface NotificationService {
    void sendNotification(NotificationRequest request);
}
