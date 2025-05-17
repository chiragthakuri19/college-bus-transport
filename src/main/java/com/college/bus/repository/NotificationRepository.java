package com.college.bus.repository;

import com.college.bus.model.Notification;
import com.college.bus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByRecipientOrderByTimestampDesc(User recipient);
    List<Notification> findByRecipientAndIsReadFalseOrderByTimestampDesc(User recipient);
    long countByRecipientAndIsReadFalse(User recipient);
} 