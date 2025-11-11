package com.salon.service;

import com.salon.modal.Notification;
import com.salon.payload.dto.NotificationDTO;

import java.util.List;

public interface NotificationService {

    NotificationDTO createNotification(Notification notification);
    List<Notification> getAllNotificationsByUserId(Long userId);
    List<Notification> getAllNotificationsBySalonId(Long salonId);
    Notification markNotificationAsRead(Long notificationId) throws Exception;
}
