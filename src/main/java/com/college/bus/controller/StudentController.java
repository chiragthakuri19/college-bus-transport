package com.college.bus.controller;

import com.college.bus.model.BusRoute;
import com.college.bus.model.Notification;
import com.college.bus.model.User;
import com.college.bus.service.BusRouteService;
import com.college.bus.service.NotificationService;
import com.college.bus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('STUDENT')")
public class StudentController {

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private BusRouteService busRouteService;

    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(Authentication authentication) {
        return userService.getUserByUsername(authentication.getName())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/transport-fee")
    public ResponseEntity<Map<String, Boolean>> checkTransportFee(Authentication authentication) {
        return userService.getUserByUsername(authentication.getName())
                .map(user -> ResponseEntity.ok(Map.of("paid", user.isTransportFeePaid())))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/notifications")
    public ResponseEntity<List<Notification>> getNotifications(Authentication authentication) {
        return userService.getUserByUsername(authentication.getName())
                .map(user -> ResponseEntity.ok(notificationService.getNotificationsForUser(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/notifications/unread")
    public ResponseEntity<List<Notification>> getUnreadNotifications(Authentication authentication) {
        return userService.getUserByUsername(authentication.getName())
                .map(user -> ResponseEntity.ok(notificationService.getUnreadNotificationsForUser(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/notifications/{id}/read")
    public ResponseEntity<?> markNotificationAsRead(@PathVariable Long id) {
        notificationService.markNotificationAsRead(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/routes")
    public ResponseEntity<List<BusRoute>> getActiveBusRoutes() {
        return ResponseEntity.ok(busRouteService.getAllActiveBusRoutes());
    }
} 