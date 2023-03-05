package com.bakhromjonov.notification.repository;

import com.bakhromjonov.notification.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
