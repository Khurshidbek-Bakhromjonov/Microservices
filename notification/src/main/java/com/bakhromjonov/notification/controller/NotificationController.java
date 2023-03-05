package com.bakhromjonov.notification.controller;

import com.bakhromjonov.clients.notification.NotificationRequest;
import com.bakhromjonov.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/notification")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public void setNotification(@RequestBody NotificationRequest notificationRequest) {
        log.info("New notification... {}",notificationRequest);
        notificationService.send(notificationRequest);
    }
}
