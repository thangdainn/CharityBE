package org.dainn.charitybe.dtos.notification;

import lombok.*;
import org.dainn.charitybe.enums.NotificationStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Notification {
    private NotificationStatus status;
    private String message;
    private String title;
}
