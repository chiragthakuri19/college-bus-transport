package com.college.bus.service;

import com.college.bus.model.Notification;
import com.college.bus.model.User;
import com.college.bus.repository.NotificationRepository;
import com.college.bus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    public Notification sendNotification(User sender, User recipient, String title, String message) {
        Notification notification = new Notification();
        notification.setSender(sender);
        notification.setRecipient(recipient);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setTimestamp(LocalDateTime.now());
        notification.setRead(false);
        
        return notificationRepository.save(notification);
    }

    public void sendNotificationToAllStudents(User sender, String title, String message) {
        List<User> students = userRepository.findByRole(User.UserRole.STUDENT);
        students.forEach(student -> {
            sendNotification(sender, student, title, message);
        });
    }

    public List<Notification> getNotificationsForUser(User user) {
        return notificationRepository.findByRecipientOrderByTimestampDesc(user);
    }

    public List<Notification> getUnreadNotificationsForUser(User user) {
        return notificationRepository.findByRecipientAndIsReadFalseOrderByTimestampDesc(user);
    }

    public void markNotificationAsRead(Long notificationId) {
        notificationRepository.findById(notificationId).ifPresent(notification -> {
            notification.setRead(true);
            notificationRepository.save(notification);
        });
    }

    public long getUnreadNotificationCount(User user) {
        return notificationRepository.countByRecipientAndIsReadFalse(user);
    }
} 