package com.college.bus.controller;

import com.college.bus.model.BusRoute;
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
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private BusRouteService busRouteService;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/routes")
    public ResponseEntity<BusRoute> createBusRoute(@RequestBody BusRoute busRoute) {
        return ResponseEntity.ok(busRouteService.createBusRoute(busRoute));
    }

    @PutMapping("/routes/{id}")
    public ResponseEntity<BusRoute> updateBusRoute(@PathVariable Long id, @RequestBody BusRoute busRoute) {
        busRoute.setId(id);
        return ResponseEntity.ok(busRouteService.updateBusRoute(busRoute));
    }

    @GetMapping("/routes")
    public ResponseEntity<List<BusRoute>> getAllRoutes() {
        return ResponseEntity.ok(busRouteService.getAllBusRoutes());
    }

    @DeleteMapping("/routes/{id}")
    public ResponseEntity<?> deactivateRoute(@PathVariable Long id) {
        busRouteService.deactivateBusRoute(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/students")
    public ResponseEntity<List<User>> getAllStudents() {
        return ResponseEntity.ok(userService.getAllStudents());
    }

    @PutMapping("/students/{id}/transport-fee")
    public ResponseEntity<?> updateTransportFeePaid(@PathVariable Long id, @RequestBody Map<String, Boolean> request) {
        userService.updateTransportFeePaid(id, request.get("paid"));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/notifications")
    public ResponseEntity<?> sendNotification(
            Authentication authentication,
            @RequestBody Map<String, String> request) {
        User admin = userService.getUserByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        
        notificationService.sendNotificationToAllStudents(
            admin,
            request.get("title"),
            request.get("message")
        );
        
        return ResponseEntity.ok().build();
    }
} 