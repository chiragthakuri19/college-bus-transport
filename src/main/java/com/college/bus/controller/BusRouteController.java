package com.college.bus.controller;

import com.college.bus.model.BusRoute;
import com.college.bus.repository.BusRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
@CrossOrigin(origins = "*")
public class BusRouteController {

    @Autowired
    private BusRouteRepository busRouteRepository;

    @GetMapping
    public List<BusRoute> getAllRoutes() {
        return busRouteRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusRoute> getRouteById(@PathVariable Long id) {
        return busRouteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public BusRoute createRoute(@RequestBody BusRoute route) {
        return busRouteRepository.save(route);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BusRoute> updateRoute(@PathVariable Long id, @RequestBody BusRoute routeDetails) {
        return busRouteRepository.findById(id)
                .map(route -> {
                    route.setRouteName(routeDetails.getRouteName());
                    route.setStartLocation(routeDetails.getStartLocation());
                    route.setEndLocation(routeDetails.getEndLocation());
                    route.setDepartureTime(routeDetails.getDepartureTime());
                    route.setEstimatedArrivalTime(routeDetails.getEstimatedArrivalTime());
                    route.setBusNumber(routeDetails.getBusNumber());
                    route.setCapacity(routeDetails.getCapacity());
                    route.setDescription(routeDetails.getDescription());
                    route.setActive(routeDetails.isActive());
                    return ResponseEntity.ok(busRouteRepository.save(route));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteRoute(@PathVariable Long id) {
        return busRouteRepository.findById(id)
                .map(route -> {
                    busRouteRepository.delete(route);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/active")
    public List<BusRoute> getActiveRoutes() {
        return busRouteRepository.findByIsActiveTrue();
    }
} 