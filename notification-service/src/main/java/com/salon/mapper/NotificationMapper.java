package com.salon.mapper;

import com.salon.modal.Notification;
import com.salon.payload.dto.BookingDTO;
import com.salon.payload.dto.NotificationDTO;
import org.springframework.stereotype.Service;

@Service
public class NotificationMapper {


    public  NotificationDTO toDTO(Notification notification,
                                 BookingDTO bookingDTO) {
        if (notification == null) {
            return null;
        }

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setId(notification.getId());
        notificationDTO.setType(notification.getType());
        notificationDTO.setIsRead(notification.getIsRead());
        notificationDTO.setDescription(notification.getDescription());
        notificationDTO.setBookingId(bookingDTO.getId());
        notificationDTO.setUserId(notification.getUserId());
        notificationDTO.setSalonId(notification.getSalonId());
        notificationDTO.setCreatedAt(notification.getCreatedAt());

        notificationDTO.setBooking(bookingDTO);

        return notificationDTO;
    }
}
