package com.ecommerce.notification_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {
    private String type;       // "EMAIL" or "SMS"
    private String recipient;  // email or phone number
    private String subject;    // optional for SMS
    private String message;
}
