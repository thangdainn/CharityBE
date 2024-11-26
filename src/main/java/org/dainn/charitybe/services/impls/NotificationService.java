package org.dainn.charitybe.services.impls;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.charitybe.dtos.notification.Notification;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final SimpMessagingTemplate messagingTemplate;

    public void sendNotification(String userId, Notification notification) {
        log.info("Sending notification to {} with payload {}", userId, notification);
        messagingTemplate.convertAndSendToUser(
                userId,
                "/notifications",
                notification
        );
    }
}